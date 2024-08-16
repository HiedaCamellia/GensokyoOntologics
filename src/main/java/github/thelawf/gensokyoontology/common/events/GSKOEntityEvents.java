package github.thelawf.gensokyoontology.common.events;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.block.nature.HotSpringBlock;
import github.thelawf.gensokyoontology.common.capability.entity.BeliefCapability;
import github.thelawf.gensokyoontology.common.capability.entity.GSKOPowerCapability;
import github.thelawf.gensokyoontology.common.capability.entity.SecularLifeCapability;
import github.thelawf.gensokyoontology.common.capability.world.BloodyMistCapability;
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.capability.world.ImperishableNightCapability;
import github.thelawf.gensokyoontology.common.entity.monster.FairyEntity;
import github.thelawf.gensokyoontology.common.network.GSKONetworking;
import github.thelawf.gensokyoontology.common.network.packet.CPowerChangedPacket;
import github.thelawf.gensokyoontology.common.util.GSKODamageSource;
import github.thelawf.gensokyoontology.common.potion.HypnosisEffect;
import github.thelawf.gensokyoontology.common.potion.LovePotionEffect;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.common.util.world.GSKOLevelUtil;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomes;
import github.thelawf.gensokyoontology.core.GSKOSoundEvents;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import github.thelawf.gensokyoontology.core.init.EffectRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.entity.monster.IMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.LevelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@EventBusSubscriber(modid = "gensokyoontology")
public class GSKOEntityEvents {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            player.getCapability(GSKOCapabilities.POWER).ifPresent(gskoCap -> {
                GSKONetworking.sendToClientPlayer(new CPowerChangedPacket(gskoCap.getCount()), player);
                GSKOPowerCapability.INSTANCE = gskoCap;
            });
            player.getCapability(GSKOCapabilities.SECULAR_LIFE).ifPresent(SecularLifeCapability::markDirty);
            player.getCapability(GSKOCapabilities.BELIEF).ifPresent(belief -> BeliefCapability.INSTANCE = belief);
        }
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {

    }



    @SubscribeEvent
    public static void onHotSpringIn(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() != null && event.getEntityLiving().isInWater()) {
            BlockState blockState = event.getEntityLiving().getBlockState();
            if (blockState.getBlockState().getBlock() instanceof HotSpringBlock &&
                    !(event.getEntityLiving() instanceof IMob)) {
                event.getEntityLiving().heal(1.2F);
            }
        }
    }

    @SubscribeEvent
    public static void onWineIn(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() != null && event.getEntityLiving().isInWater()) {
            BlockState blockState = event.getEntityLiving().getBlockState();
            if (blockState.getBlockState().getBlock().equals(BlockRegistry.SAKE_WINE_BLOCK.get()) &&
                    event.getEntityLiving() instanceof Player) {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.NAUSEA, 2 * 100));
            }
        }
    }

    @SubscribeEvent
    public static void onAdvancementDone(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            PlayerAdvancements advancement = serverPlayer.getAdvancements();
            if (serverPlayer.world.getServer() == null) return;

            AdvancementManager manager = serverPlayer.world.getServer().getAdvancementManager();
            ResourceLocation location = ResourceLocation.parse(GensokyoOntology.MODID, "gensokyo_traveller");

            if (manager.getAdvancement(location) == null) return;

            AdvancementProgress progress = advancement.getProgress(manager.getAdvancement(location));
        }
    }

    @SubscribeEvent
    public static void onLivingEnterBiome(TickEvent.PlayerTickEvent event) {
        if (event.player.level() instanceof ServerLevel && event.player instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.player;
            ServerLevel serverLevel = (ServerLevel) event.player.world;
            ResourceLocation location = serverLevel.getBiome(player.getPosition()).getRegistryName();

            if (serverLevel.getBiome(player.getPosition()).getRegistryName() == GSKOBiomes.NAMELESS_HILL_KEY.getRegistryName()) {
                player.addPotionEffect(new EffectInstance(Effects.POISON, 2 * 50));
            }

            boolean precondition = player.ticksExisted % 20 == 0 && location != null && !player.isPotionActive(EffectRegistry.HAKUREI_BLESS_EFFECT.get());

            LazyOptional<BloodyMistCapability> cap = serverLevel.getCapability(GSKOCapabilities.BLOODY_MIST);
            cap.ifPresent((capability -> {
                List<String> biomes = capability.getBiomeRegistryNames();
                biomes.forEach((biomeRegistryName -> {
                    if (precondition && Objects.equals(location.toString(), biomeRegistryName) && capability.isTriggered()) {
                        player.sendStatusMessage(GensokyoOntology.withTranslation(
                                "msg.", ".enter_danger_biome.scarlet_mansion_precincts"), true);
                        player.attackEntityFrom(DamageSource.IN_WALL, 1f);
                    }
                }));
            }));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() == null) return;
        if (!(event.getEntityLiving().level() instanceof ServerLevel)) return;

        fairyDropDanmaku(event);
        dropLunarDanmaku(event.getEntityLiving(), (ServerLevel) event.getEntityLiving().world);

        if (event.getEntityLiving() instanceof Player) {
            GSKOPowerCapability.INSTANCE.add(-1);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.level();
        Inventory inventory = player.inventory;

        if (player.getShouldBeDead()) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                if (inventory.getStackInSlot(i).getItem() == ItemRegistry.EXTEND_ITEM.get()) {
                    player.setHealth(20F);
                    world.setEntityState(player, (byte) 35);
                    inventory.getStackInSlot(i).shrink(1);
                }
            }

        }
        world.getCapability(GSKOCapabilities.IMPERISHABLE_NIGHT).ifPresent(cap -> cap.setTriggered(false));
    }

    @SubscribeEvent
    public static void playBGMToPlayer(TickEvent.PlayerTickEvent event) {
        if (event.player.getServer() == null) return;

        ServerLevel serverLevel = event.player.getServer().level(GSKODimensions.GENSOKYO);
        if (event.player.level().dimension().equals(GSKODimensions.GENSOKYO) &&
                event.player.ticksExisted % GSKOMathUtil.randomRange(200, 1000) == 0) {
            event.player.playSound(GSKOSoundEvents.CICADA_AMBIENT.get(), 0.3f, 1f);
        }
    }

    public static void onHakureiBless(LivingEvent.LivingUpdateEvent event) {
        //if (event.getEntityLiving().getActivePotionEffect(EffectRegistry.HAKUREI_BLESS_EFFECT.get()) == null) return;
        EffectInstance effect = event.getEntityLiving().getActivePotionEffect(EffectRegistry.HAKUREI_BLESS_EFFECT.get());
        if (effect == null) return;
        Level world = event.getEntity().world;
        LivingEntity living = event.getEntityLiving();
        if (effect.getDuration() > 0 && world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) world;
            LazyOptional<BloodyMistCapability> bloodyMist = serverLevel.getCapability(GSKOCapabilities.BLOODY_MIST);
            bloodyMist.ifPresent(capability -> capability.setTriggered(false));
        } else if (effect.getDuration() <= 0 && world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) world;
            LazyOptional<BloodyMistCapability> bloodyMist = serverLevel.getCapability(GSKOCapabilities.BLOODY_MIST);
            bloodyMist.ifPresent(capability -> capability.setTriggered(true));
        }
    }

    private static void fairyDropDanmaku(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof FairyEntity) {
            FairyEntity fairy = (FairyEntity) event.getEntityLiving();
            Random random = new Random();
            if (event.getSource() == GSKODamageSource.DANMAKU) {
                if (random.nextInt(100) < 8) {
                    // fairy.entityDropItem(new ItemStack(random.nextInt() % 2 == 0 ? ItemRegistry.LIFE_FRAGMENT.get() : ItemRegistry.BOMB_FRAGMENT.get()));
                    fairy.entityDropItem(new ItemStack(ItemRegistry.LIFE_FRAGMENT.get()));
                }
                for (int i = 0; i < random.nextInt(3); i++) {
                    List<Item> danmakuItems = DanmakuUtil.getAllDanmakuItem();
                    fairy.entityDropItem(danmakuItems.get(random.nextInt(danmakuItems.size())));
                }
            }
        }
    }

    private static void dropLunarDanmaku(LivingEntity living, ServerLevel serverLevel) {
        if (GSKOLevelUtil.isEntityInDimension(living, GSKODimensions.GENSOKYO)) {
            LazyOptional<ImperishableNightCapability> capability = serverLevel.getCapability(GSKOCapabilities.IMPERISHABLE_NIGHT);
            capability.ifPresent(cap -> {
                Random random = new Random();
                if (random.nextInt(100) < 23) {
                    living.entityDropItem(new ItemStack(ItemRegistry.FAKE_LUNAR_ITEM.get()));
                }
            });
        }
    }

    private static void performHypnosis(LivingEvent.LivingUpdateEvent event, HypnosisEffect effect) {

        if (event.getEntityLiving() == null) return;

        if (event.getEntityLiving() instanceof ServerPlayer) {
            Player player = (Player) event.getEntityLiving();
            if (player.isPotionActive(effect)) {
                ServerLevel serverLevel = (ServerLevel) player.level();
                serverLevel.setDayTime(13000);
                player.setPose(Pose.SLEEPING);
                player.startSleeping(player.getPosition());
            }
        } else if (event.getEntityLiving() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity) event.getEntityLiving();
            if (villager.isPotionActive(effect)) {
                ServerLevel serverLevel = (ServerLevel) villager.level();
                serverLevel.setDayTime(13000);
                villager.setPose(Pose.SLEEPING);
                villager.startSleeping(villager.getPosition());
            }
        }
    }

    private static void performLovePotion(LivingEvent.LivingUpdateEvent event, LovePotionEffect effect) {

    }

}

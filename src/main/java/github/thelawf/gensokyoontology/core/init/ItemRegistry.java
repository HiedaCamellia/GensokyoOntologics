package github.thelawf.gensokyoontology.core.init;

import com.mojang.serialization.Dynamic;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.block.ore.JadeOreBlock;
import github.thelawf.gensokyoontology.common.container.script.*;
import github.thelawf.gensokyoontology.common.item.*;
import github.thelawf.gensokyoontology.common.item.armor.KoishiHatArmorItem;
import github.thelawf.gensokyoontology.common.item.danmaku.*;
import github.thelawf.gensokyoontology.common.item.food.*;
import github.thelawf.gensokyoontology.common.item.ore.*;
import github.thelawf.gensokyoontology.common.item.script.ConstBuilderItem;
import github.thelawf.gensokyoontology.common.item.script.DynamicScriptItem;
import github.thelawf.gensokyoontology.common.item.script.ScriptBuilderItem;
import github.thelawf.gensokyoontology.common.item.script.ScriptReadOnlyItem;
import github.thelawf.gensokyoontology.common.item.spellcard.*;
import github.thelawf.gensokyoontology.common.item.tool.*;
import github.thelawf.gensokyoontology.common.item.touhou.*;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import github.thelawf.gensokyoontology.common.nbt.script.BinaryOperation;
import github.thelawf.gensokyoontology.common.nbt.script.GSKOScriptUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOCombatTab;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static net.minecraft.world.item.Items.BUCKET;

public final class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            BuiltInRegistries.ITEM, GensokyoOntology.MODID);

    // ======================= GSKO杂项：装饰类方块 ==========================//
    // --------------------------- 泥土石头类方块：----------------------------//
    public static final DeferredHolder<Item, BlockItem> DEFOLIATION_DIRT_ITEM = ITEMS.register(
            "defoliation_dirt", () -> new BlockItem(BlockRegistry.DEFOLIATION_DIRT.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> KAOLIN_BLOCK_ITEM = ITEMS.register(
            "kaolin", () -> new BlockItem(BlockRegistry.KAOLIN.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> KAOLINITE_BLOCK_ITEM = ITEMS.register(
            "kaolinite", () -> new BlockItem(BlockRegistry.KAOLINITE.get(),
                    new Item.Properties()));

    // ---------------------------- 树木类方块：------------------------------//
    //////////////////////////////// 樱花木 ////////////////////////////////
    public static final DeferredHolder<Item, BlockItem> SAKURA_SAPLING_ITEM = ITEMS.register(
            "sakura_sapling", () -> new BlockItem(BlockRegistry.SAKURA_SAPLING.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_LEAVES_ITEM = ITEMS.register(
            "sakura_leaves", () -> new BlockItem(BlockRegistry.SAKURA_LEAVES.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_LOG_ITEM = ITEMS.register(
            "sakura_log", () -> new BlockItem(BlockRegistry.SAKURA_LOG.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_PLANKS_ITEM = ITEMS.register(
            "sakura_planks", () -> new BlockItem(BlockRegistry.SAKURA_PLANKS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_BUTTON_ITEM = ITEMS.register(
            "sakura_button", () -> new BlockItem(BlockRegistry.SAKURA_BUTTON.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_SLAB_ITEM = ITEMS.register(
            "sakura_slab", () -> new BlockItem(BlockRegistry.SAKURA_SLAB.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_STAIRS_ITEM = ITEMS.register(
            "sakura_stairs", () -> new BlockItem(BlockRegistry.SAKURA_STAIRS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_DOOR_ITEM = ITEMS.register(
            "sakura_door", () -> new BlockItem(BlockRegistry.SAKURA_DOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_FENCE_ITEM = ITEMS.register(
            "sakura_fence", () -> new BlockItem(BlockRegistry.SAKURA_FENCE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_FENCE_GATE_ITEM = ITEMS.register(
            "sakura_fence_gate", () -> new BlockItem(BlockRegistry.SAKURA_FENCE_GATE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_TRAPDOOR_ITEM = ITEMS.register(
            "sakura_trapdoor", () -> new BlockItem(BlockRegistry.SAKURA_TRAPDOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_PRESSRUE_PLATE_ITEM = ITEMS.register(
            "sakura_pressure_plate", () -> new BlockItem(BlockRegistry.SAKURA_PRESSRUE_PLATE.get(),
                    new Item.Properties()));

    //////////////////////////////// 榉树木 ////////////////////////////////
    // public static final DeferredHolder<Item, BlockItem> ZELKOVA_SAPLING_ITEM = ITEMS.register(
    //         "zelkova_sapling.json", () -> new BlockItem(BlockRegistry.ZELKOVA_SAPLING.get(),
    //                 new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_LEAVES_ITEM = ITEMS.register(
            "zelkova_leaves", () -> new BlockItem(BlockRegistry.ZELKOVA_LEAVES.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_LOG_ITEM = ITEMS.register(
            "zelkova_log", () -> new BlockItem(BlockRegistry.ZELKOVA_LOG.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_PLANKS_ITEM = ITEMS.register(
            "zelkova_planks", () -> new BlockItem(BlockRegistry.ZELKOVA_PLANKS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_BUTTON_ITEM = ITEMS.register(
            "zelkova_button", () -> new BlockItem(BlockRegistry.ZELKOVA_BUTTON.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_SLAB_ITEM = ITEMS.register(
            "zelkova_slab", () -> new BlockItem(BlockRegistry.ZELKOVA_SLAB.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_STAIRS_ITEM = ITEMS.register(
            "zelkova_stairs", () -> new BlockItem(BlockRegistry.ZELKOVA_STAIRS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_DOOR_ITEM = ITEMS.register(
            "zelkova_door", () -> new BlockItem(BlockRegistry.ZELKOVA_DOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_FENCE_ITEM = ITEMS.register(
            "zelkova_fence", () -> new BlockItem(BlockRegistry.ZELKOVA_FENCE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_FENCE_GATE_ITEM = ITEMS.register(
            "zelkova_fence_gate", () -> new BlockItem(BlockRegistry.ZELKOVA_FENCE_GATE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_TRAPDOOR_ITEM = ITEMS.register(
            "zelkova_trapdoor", () -> new BlockItem(BlockRegistry.ZELKOVA_TRAPDOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_PRESSRUE_PLATE_ITEM = ITEMS.register(
            "zelkova_pressure_plate", () -> new BlockItem(BlockRegistry.ZELKOVA_PRESSRUE_PLATE.get(),
                    new Item.Properties()));

    //////////////////////////////// 枫木 ////////////////////////////////
    public static final DeferredHolder<Item, BlockItem> MAPLE_SAPLING_ITEM = ITEMS.register(
            "maple_sapling", () -> new BlockItem(BlockRegistry.MAPLE_SAPLING.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_LEAVES_ITEM = ITEMS.register(
            "maple_leaves", () -> new BlockItem(BlockRegistry.MAPLE_LEAVES.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_LOG_ITEM = ITEMS.register(
            "maple_log", () -> new BlockItem(BlockRegistry.MAPLE_LOG.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_PLANKS_ITEM = ITEMS.register(
            "maple_planks", () -> new BlockItem(BlockRegistry.MAPLE_PLANKS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_BUTTON_ITEM = ITEMS.register(
            "maple_button", () -> new BlockItem(BlockRegistry.MAPLE_BUTTON.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_SLAB_ITEM = ITEMS.register(
            "maple_slab", () -> new BlockItem(BlockRegistry.MAPLE_SLAB.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_STAIRS_ITEM = ITEMS.register(
            "maple_stairs", () -> new BlockItem(BlockRegistry.MAPLE_STAIRS.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_DOOR_ITEM = ITEMS.register(
            "maple_door", () -> new BlockItem(BlockRegistry.MAPLE_DOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_FENCE_ITEM = ITEMS.register(
            "maple_fence", () -> new BlockItem(BlockRegistry.MAPLE_FENCE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_FENCE_GATE_ITEM = ITEMS.register(
            "maple_fence_gate", () -> new BlockItem(BlockRegistry.MAPLE_FENCE_GATE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_TRAPDOOR_ITEM = ITEMS.register(
            "maple_trapdoor", () -> new BlockItem(BlockRegistry.MAPLE_TRAPDOOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_PRESSURE_PLATE_ITEM = ITEMS.register(
            "maple_pressure_plate", () -> new BlockItem(BlockRegistry.MAPLE_PRESSURE_PLATE.get(),
                    new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> MAGIC_SAPLING_ITEM = ITEMS.register(
            "magic_sapling", () -> new BlockItem(BlockRegistry.MAGIC_SAPLING.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAGIC_LEAVES_ITEM = ITEMS.register(
            "magic_leaves", () -> new BlockItem(BlockRegistry.MAGIC_LEAVES.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAGIC_LOG_ITEM = ITEMS.register(
            "magic_log", () -> new BlockItem(BlockRegistry.MAGIC_LOG.get(),
                    new Item.Properties()));

    //////////////////////////////// 银杏木 ////////////////////////////////
    public static final DeferredHolder<Item, BlockItem> GINKGO_LEAVES_ITEM = ITEMS.register(
            "ginkgo_leaves", () -> new BlockItem(BlockRegistry.GINKGO_LEAVES.get(),
                    new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> GINKGO_LOG_ITEM = ITEMS.register(
            "ginkgo_log", () -> new BlockItem(BlockRegistry.GINKGO_LOG.get(),
                    new Item.Properties()));

    //////////////////////////////// 红杉木 ////////////////////////////////
    public static final DeferredHolder<Item, BlockItem> REDWOOD_LEAVES_ITEM = ITEMS.register(
            "redwood_leaves", () -> new BlockItem(BlockRegistry.REDWOOD_LEAVES.get(),
                    new Item.Properties()));

    //////////////////////////////// 落叶堆 ////////////////////////////////
    public static final DeferredHolder<Item, BlockItem> GINKGO_LEAVES_PILE_ITEM = ITEMS.register(
            "ginkgo_leaves_pile", () -> new BlockItem(BlockRegistry.GINKGO_LEAVES_PILE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> MAPLE_LEAVES_PILE_ITEM = ITEMS.register(
            "maple_leaves_pile", () -> new BlockItem(BlockRegistry.MAPLE_LEAVES_PILE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAKURA_LEAVES_PILE_ITEM = ITEMS.register(
            "sakura_leaves_pile", () -> new BlockItem(BlockRegistry.SAKURA_LEAVES_PILE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ZELKOVA_LEAVES_PILE_ITEM = ITEMS.register(
            "zelkova_leaves_pile", () -> new BlockItem(BlockRegistry.ZELKOVA_LEAVES_PILE.get(),
                    new Item.Properties()));

    // --------------------------- 草本植物类方块：----------------------------//
    public static final DeferredHolder<Item, BlockItem> BLUE_ROSE_ITEM = ITEMS.register("blue_rose_bush",
            () -> new BlockItem(BlockRegistry.BLUE_ROSE_BUSH.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> LYCORIS_RADIATA = ITEMS.register("lycoris_radiata",
            () -> new BlockItem(BlockRegistry.LYCORIS_RADIATA.get(),
                    new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> WASABI = ITEMS.register(
            "wasabi", () -> new BlockItem(BlockRegistry.WASABI_BLOCK.get(),
                    new Item.Properties()));

    // ------------------------------ 蘑菇方块 --------------------------------//
    public static final DeferredHolder<Item, BlockItem> BLUE_MUSHROOM_ITEM = ITEMS.register(
            "blue_mushroom_block", () -> new BlockItem(BlockRegistry.BLUE_MUSHROOM_BLOCK.get(),
                    new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> PURPLE_MUSHROOM_ITEM = ITEMS.register(
            "purple_mushroom_block", () -> new BlockItem(BlockRegistry.PURPLE_MUSHROOM_BLOCK.get(),
                    new Item.Properties()));

    ///////////////////////////////    工艺装饰类方块    //////////////////////////////////
    // public static final DeferredHolder<Item, BlockItem> CHIREIDEN_COLORED_GLASS = ITEMS.register(
    //         "chireiden_colored_glass", () -> new BlockItem(BlockRegistry.CHIREIDEN_COLORED_GLASS.get(),
    //                 new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> CLAY_ADOBE_ITEM = ITEMS.register(
            "clay_adobe", () -> new BlockItem(BlockRegistry.CLAY_ADOBE_BLOCK.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> HANIWA_ITEM = ITEMS.register(
            "haniwa", () -> new BlockItem(BlockRegistry.HANIWA_BLOCK.get(),
                    new Item.Properties()));

    // ======================= GSKO杂项：功能性方块 =========================//
    //----------------------------- 合成台 --------------------------//
    public static final DeferredHolder<Item, BlockItem> DANMAKU_TABLE_ITEM = ITEMS.register(
            "danmaku_table", () -> new BlockItem(BlockRegistry.DANMAKU_TABLE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SORCERY_EXTRACTOR_ITEM = ITEMS.register(
            "sorcery_extractor", () -> new BlockItem(BlockRegistry.SORCERY_EXTRACTOR.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SAISEN_BOX_ITEM = ITEMS.register(
            "saisen_box", () -> new BlockItem(BlockRegistry.SAISEN_BOX.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> SPELL_CONSOLE_ITEM = ITEMS.register(
            "spell_card_console", () -> new BlockItem(BlockRegistry.SPELL_CARD_CONSOLE.get(),
                    new Item.Properties()));

    // -------------------------------- 矿石 ---------------------------------//
    public static final DeferredHolder<Item, BlockItem> IZANO_OBJECT_ORE_ITEM = ITEMS.register(
            "izano_object_ore", () -> new BlockItem(BlockRegistry.IZANO_OBJECT_ORE.get(),
                    new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> DRAGON_SPHERE_ORE_ITEM = ITEMS.register(
            "dragon_sphere_ore", () -> new BlockItem(BlockRegistry.DRAGON_SPHERE_ORE.get(),
                    new Item.Properties()));

    /**
     * 在玉石方块的物品类中重写其与方块的交互逻辑，实现用玉石原矿赌石的功能
     */
    public static final DeferredHolder<Item, BlockItem> JADE_ORE_ITEM = ITEMS.register(
            "jade_ore", () -> new BlockItem(BlockRegistry.JADE_ORE.get(),
                    new Item.Properties()) {
                @Override
                @NotNull
                public InteractionResult useOn(UseOnContext context) {
                    Level world = context.getLevel();
                    BlockPos pos = context.getClickedPos();
                    Block block = world.getBlockState(pos).getBlock();
                    Random random = new Random();


                    if (context.getPlayer() != null && block.matchesBlock(Blocks.STONECUTTER)) {
                        context.getPlayer().playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 1.0f, 1.0f);
                    }

                    if (world.isClientSide && Screen.hasShiftDown() && block==Blocks.STONECUTTER &&
                            random.nextInt(6) == 1 &&
                            !JadeOreBlock.getItemToDrop(world, 150, 440, 2000, 6000).equals(ItemStack.EMPTY)) {

                        ServerLevel serverLevel = (ServerLevel) world;
                        if (context.getItemInHand().getCount() >= 10) {
                            context.getItemInHand().shrink(10);
                            for (int i = 0; i < 10; i++) {
                                ItemEntity entity = new ItemEntity(world,
                                        // Center of pos.
                                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                        JadeOreBlock.getItemToDrop(world,
                                                150, 440, 2000, 6000));
                                world.addFreshEntity(entity);
                            }
                            return InteractionResult.FAIL;
                        }
                        context.getItemInHand().shrink(1);
                        ItemEntity entity = new ItemEntity(world,
                                // Center of pos.
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                JadeOreBlock.getItemToDrop(world, context.getPlayer(),
                                        serverLevel.dimension()));
                        world.addFreshEntity(entity);

                    }
                    return super.useOn(context);
                }
            });
    public static final DeferredHolder<Item, BlockItem> IMMEMORIAL_ALLOY_BLOCK_ITEM = ITEMS.register(
            "immemorial_alloy_block", () -> new BlockItem(BlockRegistry.IMMEMORIAL_ALLOY_BLOCK.get(),
                    new Item.Properties()));

    // ------------------------------- 技术性道具 ----------------------------------//
    public static final DeferredHolder<Item, BlockItem> DISPOSABLE_SPAWNER_ITEM = ITEMS.register(
            "disposable_spawner", () -> new BlockItem(BlockRegistry.DISPOSABLE_SPAWNER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> DESTRUCTIVE_EYE = ITEMS.register("destructive_eye",
            () -> new Item(new Item.Properties()));

    // ======================= GSKO杂项：道具类物品 =========================//
    // ----------------------- 东方project特殊功能道具 ----------------------//
    public static final DeferredHolder<Item, HakureiGohei> HAKUREI_GOHEI = ITEMS.register(
            "hakurei_gohei", () -> new HakureiGohei(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredHolder<Item, MarisaHakkeiro> MARISA_HAKKEIRO = ITEMS.register(
            "marisa_hakkeiro", () -> new MarisaHakkeiro(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredHolder<Item, SakuyaStopWatch> SAKUYA_WATCH = ITEMS.register(
            "sakuya_stop_watch", () -> new SakuyaStopWatch(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredHolder<Item, CrookedClockNeedle> CROOKED_CLOCK_NEEDLE = ITEMS.register(
            "crooked_clock_needle", () -> new CrookedClockNeedle(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredHolder<Item, BlockItem> GAP_BLOCK = ITEMS.register(
            "gap_block", () -> new BlockItem(BlockRegistry.GAP_BLOCK.get(), new Item.Properties()
                    .stacksTo(1)));
    // public static final DeferredRegister<EirinYagokoroArrow> EIRIN_YAGOKORO_ARROW = ITEMS.register(
    //         "eirin_yagokoro_arrow", () -> new EirinYagokoroArrow(new Item.Properties()
    //                 ));
    public static final DeferredHolder<Item, AyaFans> AYA_FANS = ITEMS.register(
            "aya_fans", () -> new AyaFans(new Item.Properties()
                    .stacksTo(1)));
    public static final DeferredHolder<Item, KoishiEyeOpen> KOISHI_EYE_OPEN = ITEMS.register(
            "koishi_eye_open", () -> new KoishiEyeOpen(new Item.Properties()
                    ));
    public static final DeferredHolder<Item, KoishiEyeClosed> KOISHI_EYE_CLOSED = ITEMS.register(
            "koishi_eye_closed", () -> new KoishiEyeClosed(new Item.Properties()
                    ));
    public static final DeferredRegister<ArmorItem> KOISHI_HAT = ITEMS.register(
            "koishi_hat", () -> new KoishiHatArmorItem(GSKOArmorMaterial.JADE,
                    EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final DeferredHolder<Item, SatoriEye> SATORI_EYE = ITEMS.register(
            "satori_eye", () -> new SatoriEye(new Item.Properties()
                    ));
    public static final DeferredHolder<Item, SeigaHairpin> SEIGA_HAIRPIN = ITEMS.register(
            "seiga_hairpin", () -> new SeigaHairpin(new Item.Properties()
                    ));
    public static final DeferredHolder<Item, SpiritTube> SPIRIT_TUBE = ITEMS.register(
            "spirit_tube", () -> new SpiritTube(new Item.Properties()
                    ));
    public static final DeferredHolder<Item, KudaGitsuneTube> KUDA_GITSUNE_TUBE = ITEMS.register(
            "kuda_gitsune_tube", () -> new KudaGitsuneTube(new Item.Properties()
                    ));
    public static final DeferredRegister<Item> GITSUNE_TUBE_FULL = ITEMS.register(
            "gitsune_tube_full", () -> new GitsuneTubeFull(new Item.Properties()
                    .containerItem(ItemRegistry.SPIRIT_TUBE.get())));
    public static final DeferredHolder<Item, OccultBall> OCCULT_BALL = ITEMS.register(
            "occult_ball", () -> new OccultBall(new Item.Properties()));
    public static final DeferredHolder<Item, ChimataMarketLicense> CHIMATA_MARKET_LICENSE = ITEMS.register(
            "chimata_market_license", () -> new ChimataMarketLicense(new Item.Properties()));
    // 魔法道具
    public static final DeferredHolder<Item, SorceryScarletMist> SORCERY_SCARLET_MIST = ITEMS.register(
            "sorcery_scarlet_mmist", () -> new SorceryScarletMist(new Item.Properties()));

    // ----------------------------------- 杂项物品 --------------------------------------//
    public static final DeferredHolder<Item, CoinItem> SILVER_COIN = ITEMS.register("silver_coin", () -> new CoinItem(1F));
    public static final DeferredHolder<Item, SakeWormItem> SAKE_WORM = ITEMS.register("sake_worm", () -> new SakeWormItem(
            new Item.Properties().stacksTo(1)));
    public static final DeferredRegister<Item> HOTSPRING_BUCKET = ITEMS.register("hotspring_bucket",
            () -> new BucketItem(FluidRegistry.HOT_SPRING_SOURCE, new Item.Properties()

                    .stacksTo(1).containerItem(BUCKET)));
    public static final DeferredRegister<Item> SAKE_BUCKET = ITEMS.register("sake_bucket",
            () -> new BucketItem(FluidRegistry.SAKE_WINE_SOURCE, new Item.Properties()

                    .stacksTo(1).containerItem(BUCKET)));

    public static final DeferredRegister<Item> PAPER_PULP_BUCKET = ITEMS.register("paper_pulp_bucket",
            () -> new BucketItem(FluidRegistry.PAPER_PULP_SOURCE, new Item.Properties()

                    .stacksTo(1).containerItem(BUCKET)));

    // ========================== GSKO杂项：合成消耗品 =========================//

    public static final DeferredHolder<Item,Item> ISHI_ZAKURA_FRAGMENT = ITEMS.register("ishi_zakura_fragment",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> ISHI_ZAKURA = ITEMS.register("ishi_zakura",
            () -> new BlockItem(BlockRegistry.ISHI_ZAKURA.get(), new Item.Properties()
                    ));

    public static final DeferredHolder<Item, CherryBlossom> CHERRY_BLOSSOM = ITEMS.register("cherry_blossom",
            () -> new CherryBlossom(new Item.Properties()));

    public static final DeferredHolder<Item, WanderingSoul> WANDERING_SOUL = ITEMS.register("wandering_soul",
            () -> new WanderingSoul(new Item.Properties()));

    public static final DeferredHolder<Item, WashiPaper> WASHI_PAPER = ITEMS.register("washi_paper",
            () -> new WashiPaper(new Item.Properties()));

    // public static final DeferredRegister<Item> PAPER_SHIDE = ITEMS.register("paper_shide",
    //         () -> new PaperShide(new Item.Properties()));

    public static final DeferredHolder<Item, IzanoObject> IZANO_OBJECT = ITEMS.register("izano_object",
            () -> new IzanoObject(new Item.Properties()));
    public static final DeferredHolder<Item, DragonSphere> DRAGON_SPHERE_FRAGMENT = ITEMS.register("dragon_sphere_fragment",
            () -> new DragonSphere(new Item.Properties()));
    public static final DeferredHolder<Item, DragonSphere> DRAGON_SPHERE = ITEMS.register("dragon_sphere",
            () -> new DragonSphere(new Item.Properties()));

    public static final DeferredHolder<Item, CrimsonAlloyIngot> CRIMSON_ALLOY_INGOT = ITEMS.register("crimson_alloy_ingot",
            () -> new CrimsonAlloyIngot(new Item.Properties()));

    public static final DeferredHolder<Item, CrimsonMetalFragment> CRIMSON_ALLOY_FRAGMENT = ITEMS.register("crimson_alloy_fragment",
            () -> new CrimsonMetalFragment(new Item.Properties()));

    ////////////////////////////////////  各个等级的玉石  ///////////////////////////////////////
    public static final DeferredHolder<Item, JadeItem> JADE_LEVEL_B = ITEMS.register("jade_level_b",
            () -> new JadeItem(new Item.Properties()));
    public static final DeferredHolder<Item, JadeItem> JADE_LEVEL_A = ITEMS.register("jade_level_a",
            () -> new JadeItem(new Item.Properties()));
    public static final DeferredHolder<Item, JadeItem> JADE_LEVEL_S = ITEMS.register("jade_level_s",
            () -> new JadeItem(new Item.Properties()));
    public static final DeferredHolder<Item, JadeItem> JADE_LEVEL_SS = ITEMS.register("jade_level_ss",
            () -> new JadeItem(new Item.Properties()));
    public static final DeferredHolder<Item, JadeItem> JADE_LEVEL_SSS = ITEMS.register("jade_level_sss",
            () -> new JadeItem(new Item.Properties()));
    public static final DeferredHolder<Item, OrbJade> ORB_JADE = ITEMS.register("orb_jade",
            () -> new OrbJade(new Item.Properties()));
    public static final DeferredHolder<Item, DarkSpirit> DARK_SPIRIT = ITEMS.register("dark_spirit",
            () -> new DarkSpirit(new Item.Properties()));
    public static final DeferredHolder<Item, LightSpirit> LIGHT_SPIRIT = ITEMS.register("light_spirit",
            () -> new LightSpirit(new Item.Properties()));

    // ---------------------------- 食物原材料 -----------------------------//
    public static final DeferredHolder<Item, KitchenKnife> KITCHEN_KNIFE = ITEMS.register("kitchen_knife", KitchenKnife::new);
    public static final DeferredHolder<Item, Butter> BUTTER = ITEMS.register("butter",
            () -> new Butter(new Item.Properties()));
    public static final DeferredHolder<Item, MilkBottle> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);

    public static final DeferredHolder<Item, SquidTentacle> SQUID_TENTACLE = ITEMS.register("squid_tentacle", SquidTentacle::new);
    public static final DeferredRegister<Item> ONION = ITEMS.register("onion", () ->
            new BlockItem(BlockRegistry.ONION_CROP_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, YattsumeUna> YATTSUME_UNA =
            ITEMS.register("yattsume_una", () -> new YattsumeUna(
                    new Item.Properties()));

    // ------------------------------- 食物 -------------------------------//
    public static final DeferredHolder<Item, YattsumeUnaYaki> YATTSUME_UNA_YAKI = ITEMS.register("yattsume_una_yaki", YattsumeUnaYaki::new);
    public static final DeferredHolder<Item, KoishiHatMousse> KOISHI_HAT_MOUSSE = ITEMS.register("koishi_hat_mousse", KoishiHatMousse::new);
    public static final DeferredHolder<Item, CakeScarletDemon> CAKE_SCARLET_DEMON = ITEMS.register("cake_scarlet_demon", CakeScarletDemon::new);
    public static final DeferredHolder<Item, Lingoame> LINGOAME = ITEMS.register("lingoame", Lingoame::new);
    public static final DeferredHolder<Item, TakoYaki> TAKO_YAKI = ITEMS.register("tako_yaki", TakoYaki::new);
    public static final DeferredHolder<Item, WhiteSnow> WHITE_SNOW = ITEMS.register("white_snow", WhiteSnow::new);
    public static final DeferredHolder<Item, BurgerMeatRaw> BURGER_MEAT_RAW = ITEMS.register("burger_meat_raw", BurgerMeatRaw::new);
    public static final DeferredHolder<Item, BurgerMeat> BURGER_MEAT = ITEMS.register("burger_meat", BurgerMeat::new);


    //////////////////////////////////// 被遗忘的传说 /////////////////////////////////
    public static final DeferredHolder<Item, ObliviousTales> TALES_SCARLET_MIST = ITEMS.register(
            "oblivious_tales_scarlet_mist", () -> new ObliviousTales(new CompoundTag()));
    public static final DeferredHolder<Item, ObliviousTales> TALES_SPRING_SNOWS = ITEMS.register(
            "oblivious_tales_spring_snows", () -> new ObliviousTales(new CompoundTag()));
    public static final DeferredHolder<Item, ObliviousTales> TALES_IMPERISHABLE_NIGHT = ITEMS.register(
            "oblivious_tales_imperishable_night", () -> new ObliviousTales(new CompoundTag()));
    public static final DeferredHolder<Item, ObliviousTales> TALES_OCCULT_BALL = ITEMS.register(
            "oblivious_tales_occult_ball", () -> new ObliviousTales(new CompoundTag()));

    // Technical Items that will break the game balance: //
    // public static final DeferredRegister<Item> REALISM_SWORD = ITEMS.register(
    //         "realism_sword", RealismSword::new);
    // public static final DeferredRegister<Item> METAPHYSICS_SWORD = ITEMS.register(
    //         "metaphysics_sword", MetaphysicsSword::new);
    // public static final DeferredRegister<Item> IDEALISM_SWORD = ITEMS.register(
    //         "idealism_sword", IdealismSword::new);
    // public static final DeferredRegister<Item> PRAXIS_SWORD = ITEMS.register(
    //         "praxis_sword", PraxisSword::new);


    // ============================ GSKO生物刷怪蛋 ================================//
    public static final DeferredRegister<SpawnEggItem> HAKURE_REIMU_SPAWN_EGG = ITEMS.register(
            "hakurei_reimu_spawn_egg", () -> new SpawnEggItem(EntityRegistry.HAKUREI_REIMU,
                    0xFF200A, 0xFFFFFC, new Item.Properties()));
    public static final DeferredRegister<SpawnEggItem> FAIRY_SPAWN_EGG = ITEMS.register(
            "fairy_spawn_egg", () -> new SpawnEggItem(EntityRegistry.FAIRY_ENTITY,
                    0x0E51D5, 0xFAEB1C, new Item.Properties()));
    public static final DeferredRegister<SpawnEggItem> LILY_WHITE_SPAWN_EGG = ITEMS.register(
            "lily_white_spawn_egg", () -> new SpawnEggItem(EntityRegistry.LILY_WHITE_ENTITY,
                    0xFFF8E8, 0xF52C2C, new Item.Properties()));
    public static final DeferredRegister<SpawnEggItem> REMILIA_SCARLET_SAWN_EGG = ITEMS.register(
            "remilia_scarlet_spawn_egg", () -> new SpawnEggItem(EntityRegistry.REMILIA_SCARLET,
                    0x99CAFF, 0xFF0F3F, new Item.Properties()));
    public static final DeferredRegister<SpawnEggItem> FLANDRE_SCARLET_SPAWN_EGG = ITEMS.register(
            "flandre_scarlet_spawn_egg", () -> new SpawnEggItem(EntityRegistry.FLANDRE_SCARLET,
                    0xDC143C, 0xFDFD78, new Item.Properties()));
    public static final DeferredRegister<SpawnEggItem> KOMEIJI_KOISHI_SAPWN_EGG = ITEMS.register(
            "komeiji_koishi_spawn_egg", () -> new SpawnEggItem(EntityRegistry.KOMEIJI_KOISHI,
                    0x42B983, 0xFCFA58, new Item.Properties()));

    // ======================== GSKO战斗类物品 ============================//
    // ----------------------------- 符卡 --------------------------------//
    public static final DeferredHolder<Item, SpellCardBlank> SPELL_CARD_BLANK = ITEMS.register(
            "spell_card_blank", SpellCardBlank::new);
    public static final DeferredHolder<Item, SC_HyperboloidLaser> SC_HYPERBOLOID_LASER = ITEMS.register(
            "sc_hyperboloid_laser", SC_HyperboloidLaser::new);
    public static final DeferredHolder<Item, SC_WaveAndParticle> SC_WAVE_AND_PARTICLE = ITEMS.register(
            "sc_wave_and_particle", SC_WaveAndParticle::new);
    public static final DeferredHolder<Item, SC_IdoNoKaiho> SC_IDO_NO_KAIHO = ITEMS.register(
            "sc_ido_no_kaiho", SC_IdoNoKaiho::new);
    public static final DeferredHolder<Item, SC_SuperEgo> SC_SUPER_EGO = ITEMS.register(
            "sc_super_ego", SC_SuperEgo::new);
    public static final DeferredHolder<Item, SC_HellEclipse> SC_HELL_ECLIPSE = ITEMS.register(
            "sc_hell_eclipse", SC_HellEclipse::new);
    // public static final DeferredRegister<SC_MountainOfFaith> SC_MOUNTAIN_OF_FAITH = ITEMS.register(
    //         "sc_mountain_of_faith", SC_MountainOfFaith::new);
    public static final DeferredHolder<Item, SC_MobiusRingLevel> SC_MOBIUS_RING_WORLD = ITEMS.register(
            "sc_mobius_ring_world", SC_MobiusRingLevel::new);
    public static final DeferredHolder<Item, SC_FullCherryBlossom> SC_FULL_CHERRY_BLOSSOM = ITEMS.register(
            "sc_full_cherry_blossom", SC_FullCherryBlossom::new);
    public static final DeferredHolder<Item, SC_RorshachDanmaku> SC_RORSHACH_DANMAKU = ITEMS.register(
            "sc_rorshach_danmaku", SC_RorshachDanmaku::new);
    public static final DeferredHolder<Item, ScriptedSpellCard> SCRIPTED_SPELL_CARD = ITEMS.register(
            "scripted_spell_card", ScriptedSpellCard::new);

    // public static final DeferredRegister<SC_HanaShigure> SC_HANA_SHIGURE = ITEMS.register(
    //         "sc_hana_shigure", SC_HanaShigure::new);
    // public static final DeferredRegister<SC_ManiaDepress> SC_MANIA_DEPRESS = ITEMS.register(
    //         "sc_mania_depress", SC_ManiaDepress::new);
    // public static final DeferredRegister<SC_GalacticSpiralArms> SC_GALACTIC_SPIRAL_ARMS = ITEMS.register(
    //         "sc_galactic_spiral_arms", SC_GalacticSpiralArms::new);

    // --------------------- 投掷物：弹幕 阴阳玉 灵符 -----------------------//
    public static final DeferredHolder<Item, DanmakuShotItem> DANMAKU_SHOT = ITEMS.register("danmaku_shot",
            () -> new DanmakuShotItem(DanmakuType.DANMAKU_SHOT));

    /////////////////////////// 所有的灰色弹幕 ////////////////////////////////
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT = ITEMS.register(
            "large_shot", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT = ITEMS.register(
            "small_shot", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, RiceShot> RICE_SHOT = ITEMS.register("rice_shot",
            () -> new RiceShot(new Item.Properties()));
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT = ITEMS.register("scale_shot",
            () -> new ScaleShot(new Item.Properties()));

    public static final DeferredRegister<TalismanShot> TALISMAN_SHOT = ITEMS.register(
            "talisman_shot", () -> new TalismanShot(new Item.Properties()));

    /////////////////////////// 所有颜色的大弹 ////////////////////////////////
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_RED = ITEMS.register(
            "large_shot_red", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_ORANGE = ITEMS.register(
            "large_shot_orange", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_YELLOW = ITEMS.register(
            "large_shot_yellow", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_GREEN = ITEMS.register(
            "large_shot_green", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_AQUA = ITEMS.register(
            "large_shot_aqua", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_BLUE = ITEMS.register(
            "large_shot_blue", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_PURPLE = ITEMS.register(
            "large_shot_purple", () -> new LargeShot(DanmakuType.LARGE_SHOT));
    public static final DeferredHolder<Item, LargeShot> LARGE_SHOT_MAGENTA = ITEMS.register(
            "large_shot_magenta", () -> new LargeShot(DanmakuType.LARGE_SHOT));

    /////////////////////////// 所有颜色的小弹 ////////////////////////////////
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_RED = ITEMS.register(
            "small_shot_red", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_ORANGE = ITEMS.register(
            "small_shot_orange", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_YELLOW = ITEMS.register(
            "small_shot_yellow", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_GREEN = ITEMS.register(
            "small_shot_green", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_AQUA = ITEMS.register(
            "small_shot_aqua", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_BLUE = ITEMS.register(
            "small_shot_blue", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_PURPLE = ITEMS.register(
            "small_shot_purple", () -> new SmallShot(DanmakuType.SMALL_SHOT));
    public static final DeferredHolder<Item, SmallShot> SMALL_SHOT_MAGENTA = ITEMS.register(
            "small_shot_magenta", () -> new SmallShot(DanmakuType.SMALL_SHOT));

    ////////////////////////////// 所有颜色的环玉  /////////////////////////////////
    // public static final DeferredRegister<Item> CIRCLE_SHOT = ITEMS.register("rice_shot",
    //         () -> new RiceShot(new Item.Properties()));
    public static final DeferredHolder<Item, CircleShot> CIRCLE_SHOT_GREEN = ITEMS.register("circle_shot_green",
            () -> new CircleShot(new Item.Properties()));
    public static final DeferredHolder<Item, CircleShot> CIRCLE_SHOT_BLUE = ITEMS.register("circle_shot_blue",
            () -> new CircleShot(new Item.Properties()));
    public static final DeferredHolder<Item, CircleShot> CIRCLE_SHOT_MAGENTA = ITEMS.register("circle_shot_magenta",
            () -> new CircleShot(new Item.Properties()));

    ////////////////////////////// 所有颜色的米弹  /////////////////////////////////
    public static final DeferredHolder<Item, RiceShot> RICE_SHOT_RED = ITEMS.register("rice_shot_red",
            () -> new RiceShot(new Item.Properties()));
    public static final DeferredHolder<Item, RiceShot> RICE_SHOT_BLUE = ITEMS.register("rice_shot_blue",
            () -> new RiceShot(new Item.Properties()));
    public static final DeferredHolder<Item, RiceShot> RICE_SHOT_PURPLE = ITEMS.register("rice_shot_purple",
            () -> new RiceShot(new Item.Properties()));

    ////////////////////////////// 所有颜色的鳞弹  /////////////////////////////////
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT_RED = ITEMS.register("scale_shot_red",
            () -> new ScaleShot(new Item.Properties()));
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT_YELLOW = ITEMS.register("scale_shot_yellow",
            () -> new ScaleShot(new Item.Properties()));
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT_GREEN = ITEMS.register("scale_shot_green",
            () -> new ScaleShot(new Item.Properties()));
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT_BLUE = ITEMS.register("scale_shot_blue",
            () -> new ScaleShot(new Item.Properties()));
    public static final DeferredHolder<Item, ScaleShot> SCALE_SHOT_PURPLE = ITEMS.register("scale_shot_purple",
            () -> new ScaleShot(new Item.Properties()));

    ////////////////////////////// 所有颜色的心弹  /////////////////////////////////
    public static final DeferredHolder<Item, HeartShot> HEART_SHOT = ITEMS.register(
            "heart_shot", () -> new HeartShot(DanmakuType.HEART_SHOT));
    public static final DeferredHolder<Item, HeartShot> HEART_SHOT_PINK = ITEMS.register(
            "heart_shot_pink", () -> new HeartShot(DanmakuType.HEART_SHOT));
    public static final DeferredHolder<Item, HeartShot> HEART_SHOT_RED = ITEMS.register(
            "heart_shot_red", () -> new HeartShot(DanmakuType.HEART_SHOT));
    public static final DeferredHolder<Item, HeartShot> HEART_SHOT_AQUA = ITEMS.register(
            "heart_shot_aqua", () -> new HeartShot(DanmakuType.HEART_SHOT));
    public static final DeferredHolder<Item, HeartShot> HEART_SHOT_BLUE = ITEMS.register(
            "heart_shot_blue", () -> new HeartShot(DanmakuType.HEART_SHOT));

    ////////////////////////////// 所有颜色的小型星弹  /////////////////////////////////
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT = ITEMS.register(
            "small_star_shot", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_RED = ITEMS.register(
            "small_star_shot_red", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_YELLOW = ITEMS.register(
            "small_star_shot_yellow", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_GREEN = ITEMS.register(
            "small_star_shot_green", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_AQUA = ITEMS.register(
            "small_star_shot_aqua", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_BLUE = ITEMS.register(
            "small_star_shot_blue", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));
    public static final DeferredHolder<Item, StarShot> SMALL_STAR_SHOT_PURPLE = ITEMS.register(
            "small_star_shot_purple", () -> new StarShot(DanmakuType.STAR_SHOT_SMALL));

    ////////////////////////////// 所有颜色的大型星弹  /////////////////////////////////
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT = ITEMS.register(
            "large_star_shot", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_RED = ITEMS.register(
            "large_star_shot_red", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_YELLOW = ITEMS.register(
            "large_star_shot_yellow", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_GREEN = ITEMS.register(
            "large_star_shot_green", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_AQUA = ITEMS.register(
            "large_star_shot_aqua", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_BLUE = ITEMS.register(
            "large_star_shot_blue", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));
    public static final DeferredHolder<Item, StarShot> LARGE_STAR_SHOT_PURPLE = ITEMS.register(
            "large_star_shot_purple", () -> new StarShot(DanmakuType.STAR_SHOT_LARGE));

    ////////////////////////////// 所有颜色的札弹  /////////////////////////////////
    public static final DeferredHolder<Item, TalismanShot> TALISMAN_SHOT_RED = ITEMS.register("talisman_shot_red",
            () -> new TalismanShot(new Item.Properties()));
    public static final DeferredHolder<Item, TalismanShot> TALISMAN_SHOT_GREEN = ITEMS.register("talisman_shot_green",
            () -> new TalismanShot(new Item.Properties()));
    public static final DeferredHolder<Item, TalismanShot> TALISMAN_SHOT_AQUA = ITEMS.register("talisman_shot_aqua",
            () -> new TalismanShot(new Item.Properties()));
    public static final DeferredHolder<Item, TalismanShot> TALISMAN_SHOT_BLUE = ITEMS.register("talisman_shot_blue",
            () -> new TalismanShot(new Item.Properties()));
    public static final DeferredHolder<Item, TalismanShot> TALISMAN_SHOT_PURPLE = ITEMS.register("talisman_shot_purple",
            () -> new TalismanShot(new Item.Properties()));

    ////////////////////////////// 所有颜色的阴阳玉 ///////////////////////////////////
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_BLACK = ITEMS.register(
            "inyo_jade_black", () -> new InyoJade(DyeColor.BLACK,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_RED = ITEMS.register(
            "inyo_jade_red", () -> new InyoJade(DyeColor.RED,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_YELLOW = ITEMS.register(
            "inyo_jade_yellow", () -> new InyoJade(DyeColor.YELLOW,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_GREEN = ITEMS.register(
            "inyo_jade_green", () -> new InyoJade(DyeColor.GREEN,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_AQUA = ITEMS.register(
            "inyo_jade_aqua", () -> new InyoJade(DyeColor.LIGHT_BLUE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_BLUE = ITEMS.register(
            "inyo_jade_blue", () -> new InyoJade(DyeColor.BLUE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, InyoJade> INYO_JADE_PURPLE = ITEMS.register(
            "inyo_jade_purple", () -> new InyoJade(DyeColor.PURPLE,
                    new Item.Properties()));

    //////////////////////////// 道具：B点、残机 ////////////////////////////////
    public static final DeferredHolder<Item, FakeLunarItem> FAKE_LUNAR_ITEM = ITEMS.register("fake_lunar",
            () -> new FakeLunarItem(DanmakuType.FAKE_LUNAR));

    public static final DeferredHolder<Item, PowerItem> POWER_ITEM = ITEMS.register("power_item",
            () -> new PowerItem(new Item.Properties()));
    public static final DeferredHolder<Item, BombFragment> BOMB_FRAGMENT = ITEMS.register("bomb_fragment",
            () -> new BombFragment(new Item.Properties()));

    public static final DeferredHolder<Item, LifeFragment> LIFE_FRAGMENT = ITEMS.register("life_fragment",
            () -> new LifeFragment(new Item.Properties()));

    public static final DeferredHolder<Item, BombItem> BOMB_ITEM = ITEMS.register("bomb_item",
            () -> new BombItem(new Item.Properties()));

    public static final DeferredHolder<Item, ExtendItem> EXTEND_ITEM = ITEMS.register("extend_item",
            () -> new ExtendItem(new Item.Properties()));

    // ------------------------------- 装备 -------------------------------//
    public static final DeferredHolder<Item, JadeAxe> JADE_AXE = ITEMS.register("jade_axe", () -> new JadeAxe(
            new Item.Properties()));
    public static final DeferredHolder<Item, JadeHoe> JADE_HOE = ITEMS.register("jade_hoe", () -> new JadeHoe(
            new Item.Properties()));
    public static final DeferredHolder<Item, JadePickaxe> JADE_PICKAXE = ITEMS.register("jade_pickaxe", () -> new JadePickaxe(
            new Item.Properties()));
    public static final DeferredHolder<Item, JadeShovel> JADE_SHOVEL = ITEMS.register("jade_shovel", () -> new JadeShovel(
            new Item.Properties()));
    public static final DeferredHolder<Item, JadeSword> JADE_SWORD = ITEMS.register("jade_sword", () -> new JadeSword(
            new Item.Properties()));

    public static final DeferredRegister<ArmorItem> JADE_HELMET = ITEMS.register("jade_helmet", () -> new ArmorItem(
            GSKOArmorMaterial.JADE, EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final DeferredRegister<ArmorItem> JADE_CHESTPLATE = ITEMS.register("jade_chestplate", () -> new ArmorItem(
            GSKOArmorMaterial.JADE, EquipmentSlotType.CHEST, (new Item.Properties())));
    public static final DeferredRegister<ArmorItem> JADE_LEGGINGS = ITEMS.register("jade_leggings", () -> new ArmorItem(
            GSKOArmorMaterial.JADE, EquipmentSlotType.LEGS, (new Item.Properties())));
    public static final DeferredRegister<ArmorItem> JADE_BOOTS = ITEMS.register("jade_boots", () -> new ArmorItem(
            GSKOArmorMaterial.JADE, EquipmentSlotType.FEET, (new Item.Properties())));


    // ====================================== 技术性物品 ====================================== //
    public static final DeferredHolder<Item,Item> DREAM_SEAL_ITEM = ITEMS.register("dream_seal", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item,Item> SPHERE_EFFECT_ITEM = ITEMS.register("sphere", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, ConstBuilderItem> CONST_BUILDER = ITEMS.register("const_builder", ConstBuilderItem::new);

    public static final DeferredHolder<Item,ScriptBuilderItem> V3D_BUILDER = ITEMS.register("vector3d_builder", () -> new ScriptBuilderItem() {
        @Override
        public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
           if (!world.isRemote) player.openContainer(V3DBContainer.create("vector3d_builder"));
        }
    });

    public static final DeferredHolder<Item,DynamicScriptItem> V3D_INVOKER = ITEMS.register("v3d_invoker", () -> new DynamicScriptItem() {
        @Override
        public void addDynamicData(Level world, Player player, ItemStack stack, Dynamic<INBT> dynamic) {

        }
        @Override
        public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
            if (!world.isRemote) player.openContainer(V3dInvokerContainer.create());
        }
    });

    public static final DeferredHolder<Item,DynamicScriptItem> STATIC_INVOKER = ITEMS.register("static_invoker", () -> new DynamicScriptItem() {
        @Override
        public void addDynamicData(Level world, Player player, ItemStack stack, Dynamic<INBT> dynamic) {

        }
        @Override
        public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
            if (!world.isRemote) player.openContainer(StaticInvokerContainer.create());
        }
    });

    public static final DeferredHolder<Item,DynamicScriptItem> DANMAKU_BUILDER = ITEMS.register("danmaku_builder", () -> new ScriptBuilderItem() {
        @Override
        public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
            if (!world.isRemote) player.openContainer(DanmakuBuilderContainer.create("danmaku_builder"));
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
            Component entityText = GensokyoOntology.withTranslation("tooltip.",".danmaku_entity");
            Component colorText = GensokyoOntology.withTranslation("tooltip.",".danmaku_color");
            Component typeText = GensokyoOntology.withTranslation("tooltip.",".danmaku_type");
            if (stack.getTag() != null) {
                CompoundTag nbt = stack.getTag();
                tooltip.add(entityText);
                tooltip.add(Component.translatable(nbt.getString("type")));
                if (nbt.contains("danmakuType")) {
                    tooltip.add(typeText);
                    DanmakuType type = DanmakuType.valueOf(nbt.getString("danmakuType").toUpperCase());
                    tooltip.add(type.toTextComponent());
                }
                if (nbt.contains("danmakuColor")) {
                    tooltip.add(colorText);
                    DanmakuColor color = DanmakuColor.valueOf(nbt.getString("danmakuColor").toUpperCase());
                    tooltip.add(color.toTextComponent());
                }
                else if (GSKONBTUtil.containsAllowedType(nbt)) {
                    GSKONBTUtil.getMemberValues(nbt).forEach(s -> tooltip.add(Component.literal(s)));
                }
            }
        }
    });

    public static final DeferredRegister<Item> BINARY_OPERATION_BUILDER = ITEMS.register("binary_operation_builder", () -> new DynamicScriptItem() {
        @Override
        public void addDynamicData(Level world, Player player, ItemStack stack, Dynamic<INBT> dynamic) {

        }
        @Override
        public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
            // minecraft.displayGuiScreen(new DanmakuBuilderScreen(title, stack, world, player));
            if (!world.isRemote) player.openContainer(BinaryOperationContainer.create());
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
            if (stack.getTag() == null) return;
            CompoundTag nbt = stack.getTag();
            tooltip.add(OPERATION_TYPE_TIP);
            tooltip.add(BinaryOperation.valueOf(GSKOScriptUtil.getScriptValue(nbt).getString("operation")
                    .toUpperCase()).toTextComponent());
            tooltip.add(LEFT_TYPE_TIP);
            tooltip.add(Component.literal(TYPE_HIGHLIGHT + GSKOScriptUtil.getOptLeft(nbt).getString("type")));
            tooltip.add(RIGHT_TYPE_TIP);
            tooltip.add(Component.literal(TYPE_HIGHLIGHT + GSKOScriptUtil.getOptRight(nbt).getString("type")));
        }
    });

    public static final DeferredRegister<Item> TIME_STAMP = ITEMS.register("time_stamp", () -> new ScriptReadOnlyItem() {
        @Override
        public void addReadOnlyData(Level world, Player player, ItemStack stack) {
            CompoundTag nbt = new CompoundTag();
            nbt.putString("type", "time_stamp");
            nbt.putString("name", "ticksExisted");
            nbt.putString("value", "increasedByTick");
            stack.setTag(nbt);
        }
    });

    // public static final DeferredRegister<Item> FUNC_INVOCATION = ITEMS.register("func_invocation", () -> new ScriptReadOnlyItem() {
    //     @Override
    //     public void addReadOnlyData(Level world, Player player, ItemStack stack) {
    //     }
    // });
}

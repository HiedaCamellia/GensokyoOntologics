package github.thelawf.gensokyoontology.common.events;

import com.github.tartaricacid.touhoulittlemaid.capability.PowerCapabilityProvider;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.block.GapBlock;
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.compat.touhoulittlemaid.TouhouLittleMaidCompat;
import github.thelawf.gensokyoontology.common.entity.spawn.LilyWhiteSpawner;
import github.thelawf.gensokyoontology.common.item.touhou.GapItem;
import github.thelawf.gensokyoontology.common.item.touhou.SakeWormItem;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.common.world.GSKOEntityGenerator;
import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomeMaker;
import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomes;
import github.thelawf.gensokyoontology.common.world.feature.GSKOFeatureGenerator;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import github.thelawf.gensokyoontology.core.init.StructureRegistry;
import github.thelawf.gensokyoontology.data.world.GSKOLevelSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@EventBusSubscriber(modid = GensokyoOntology.MODID)
public class GSKOLevelEvents {

    @SubscribeEvent
    public static void onLivingSpawn(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.THEEND) || event.getCategory().equals(Biome.Category.NETHER)) {
            return;
        }
        List<MobSpawnInfo.Spawners> spawners = event.getSpawns().getSpawner(EntityRegistry.FAIRY_ENTITY.get().getClassification());
        spawners.add(new MobSpawnInfo.Spawners(EntityRegistry.FAIRY_ENTITY.get(), 38, 2, 4));
        GSKOEntityGenerator.addEntityToBiomes(event, EntityRegistry.SPECTRE_ENTITY.get(), 20, 3, 5,
                GSKOBiomes.HIGAN_KEY,
                GSKOBiomes.BAMBOO_FOREST_LOST_KEY,
                GSKOBiomes.SCARLET_MANSION_PRECINCTS_KEY);
    }
    
    @SubscribeEvent
    public static void onItemEntityTick(EntityEvent event) {
        if (event.getEntity() == null) return;
        if (event.getEntity().getType() == EntityType.ITEM) {
            ItemEntity itemEntity = (ItemEntity) event.getEntity();
            Level world = event.getEntity().level();
            if (itemEntity.getItem().getItem() == ItemRegistry.SAKE_WORM.get() && itemEntity.ticksExisted > 100 &&
                    world.getBlockState(itemEntity.getPosition()).getBlock() == Blocks.WATER) {
                world.setBlockState(itemEntity.getPosition(), BlockRegistry.SAKE_WINE_BLOCK.get().getDefaultState());
            }
            onGapEntityTick(itemEntity, ItemRegistry.GAP_BLOCK.get());
        }
    }

    @SubscribeEvent
    public static void trySpawnBoss(TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) event.player.world;
            Player player = event.player;
            LilyWhiteSpawner.spawn(serverLevel, player, player.getPosition(), player.ticksExisted, 0.01f);
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        IChunk chunk = event.getChunk();
        if (chunk.levelForge() instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) chunk.levelForge();
            if (serverLevel.dimension().equals(GSKODimensions.GENSOKYO)) {
                Biome biome = serverLevel.getBiome(chunk.getPos().asBlockPos());
                final String modid = GensokyoOntology.MODID;
            }
        }
    }

    @SubscribeEvent
    public static void addDimensionSpacing(final LevelEvent.Load event) {
        if (event.level() instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) event.level();
            Method GET_CODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
            try {
                ResourceLocation location = Registry.CHUNK_GENERATOR_CODEC.getKey(
                        (Codec<? extends ChunkGenerator>) GET_CODEC_METHOD.invoke(
                                serverLevel.getChunkProvider().getChunkGenerator()));
                if (location != null && location.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                LogManager.getLogger().error("Was unable to check if " + serverLevel.dimension()
                        + " is using Terraforged's ChunkGenerator.");
            }
            // 放止建筑在超平坦世界生成
            if (serverLevel.getChunkProvider().generator instanceof FlatChunkGenerator &&
                    serverLevel.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            // 将我们的建筑添加到建筑生成地图中，反混淆映射如下：
            //        srg名：               反混淆名：                                类型/返回值：                                             作用：
            //   func_235957_b()     getDimensionSettings()         return -> DimensionStructuresSettings                              获取世界维度生成设置
            //   field_236193_d_          structures                Map<Structure<?>, StructureSeparationSettings>             存放建筑结构和建筑生成设置的映射Map
            //   field_236191_b_       structureSettings       ImmutableMap<Structure<?>, StructureSeparationSettings>      存放建筑结构和建筑生成设置的不可变映射Map
            //   field_236268_b_            feature                           F extends Structure<FC>                            泛型：一切继承于建筑结构的类
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
                    serverLevel.getChunkProvider().generator.func_235957_b_().func_236195_a_());

            tempMap.putIfAbsent(StructureRegistry.ALICE_HOUSE.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.ALICE_HOUSE.get()));

            tempMap.putIfAbsent(StructureRegistry.SCARLET_DEVIL_MANSION.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.SCARLET_DEVIL_MANSION.get()));

            tempMap.putIfAbsent(StructureRegistry.MYSTIA_IZAKAYA.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.MYSTIA_IZAKAYA.get()));

            tempMap.putIfAbsent(StructureRegistry.HAKUREI_SHRINE.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.HAKUREI_SHRINE.get()));

            tempMap.putIfAbsent(StructureRegistry.CIRNO_ICE_HOUSE.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.CIRNO_ICE_HOUSE.get()));

            tempMap.putIfAbsent(StructureRegistry.CHIREIDEN.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.CHIREIDEN.get()));

            tempMap.putIfAbsent(StructureRegistry.BEAST_PATHWAY.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.BEAST_PATHWAY.get()));

            tempMap.putIfAbsent(StructureRegistry.HUMAN_VILLAGE.get(),
                    DimensionStructuresSettings.field_236191_b_.get(StructureRegistry.HUMAN_VILLAGE.get()));

            serverLevel.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(final BiomeLoadingEvent event) {
        GSKOFeatureGenerator.generateOverworldOre(event);
        GSKOFeatureGenerator.generateGensokyoOre(event);
    }

    private static void onGapEntityTick(ItemEntity entity, BlockItem gapItem) {
        if (entity.getItem().getItem() == gapItem) {
            Level world = entity.level();
            Predicate<BlockPos> predicate = (pos) -> {
                BlockPos.Mutable mutable = pos.toMutable();
                return world.getBlockState(mutable).getBlock() == Blocks.WATER && world.getBlockState(mutable.north()).getBlock() == Blocks.WATER &&
                        world.getBlockState(mutable.south()).getBlock() == Blocks.WATER && world.getBlockState(mutable.east()).getBlock() == Blocks.WATER &&
                        world.getBlockState(mutable.west()).getBlock() == Blocks.WATER && world.getBlockState(mutable.add(1,0,1)).getBlock() == Blocks.WATER &&
                        world.getBlockState(mutable.add(1,0,-1)).getBlock() == Blocks.WATER && world.getBlockState(mutable.add(-1,0,1)).getBlock() == Blocks.WATER &&
                        world.getBlockState(mutable.add(-1,0,-1)).getBlock() == Blocks.WATER;
            };
        }
    }


    private static void spawnEntityIn(ServerLevel serverLevel, EntityClassification classification,
                                      LevelEvent.PotentialSpawns event) {

        List<ResourceLocation> biomeIds = Arrays.asList(
                ResourceLocation.parse("minecraft:plains"),
                ResourceLocation.parse("minecraft:desert"),
                ResourceLocation.parse("minecraft:forest"),
                ResourceLocation.parse("miencraft:taiga"),
                ResourceLocation.parse("minecraft:mountains"),
                ResourceLocation.parse("minecraft:snowy_tundra"),
                ResourceLocation.parse("minecraft:snowy_mountains"),
                ResourceLocation.parse("minecraft:jungle"),
                ResourceLocation.parse("minecraft:birch_forest"),
                ResourceLocation.parse("minecraft:savanna"),
                ResourceLocation.parse("minecraft:savanna_plateau"),
                ResourceLocation.parse("minecraft:dark_forest"),
                ResourceLocation.parse("minecraft:bamboo_jungle"),
                ResourceLocation.parse("minecraft:giant_spruce_taiga")
        );

        serverLevel.getChunkProvider().getChunkGenerator().getBiomeProvider()
                .getBiomes().forEach(biome -> spawnEntityIn(biome, biomeIds, classification));
    }

    private static void spawnEntityIn(Biome biome, List<ResourceLocation> biomeIds,
                                      EntityClassification classification) {
        List<EntityType<?>> entityTypes = ImmutableList.of(
                EntityRegistry.FAIRY_ENTITY.get()
        );

        biomeIds.forEach(resourceLocation -> {
            if (biome.getRegistryName() != null && biome.getRegistryName().equals(resourceLocation)) {
                int weight = 8;
                int minCount = 3;
                int maxCount = 8;

                // entityTypes.forEach(entityType -> biome.getMobSpawnInfo().getSpawners(classification)
                //         .add(new MobSpawnInfo.Spawners(EntityRegistry.FAIRY_ENTITY.get(),
                //                 weight, minCount, maxCount)));
            }
        });
    }
}

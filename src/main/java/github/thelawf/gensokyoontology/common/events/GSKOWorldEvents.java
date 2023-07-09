package github.thelawf.gensokyoontology.common.events;

import com.google.common.collect.ImmutableList;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.common.world.feature.GSKOFeatureGeneration;
import github.thelawf.gensokyoontology.common.world.feature.GSKOFeatures;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = GensokyoOntology.MODID)
public class GSKOWorldEvents {

    @SubscribeEvent
    public static void onLivingSpawn(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.THEEND) ||
        event.getCategory().equals(Biome.Category.NETHER)) {
            return;
        }
        List<MobSpawnInfo.Spawners> spawners = event.getSpawns().getSpawner(
                EntityRegistry.FAIRY_ENTITY.get().getClassification());

        spawners.add(new MobSpawnInfo.Spawners(EntityRegistry.FAIRY_ENTITY.get(),
                38,2,4));
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        IChunk chunk = event.getChunk();
        if (chunk.getWorldForge() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) chunk.getWorldForge();
            if (serverWorld.getDimensionKey().equals(GSKODimensions.GENSOKYO)) {
                Biome biome = serverWorld.getBiome(chunk.getPos().asBlockPos());
                final String modid = GensokyoOntology.MODID;
                // if (String.valueOf(biome.getRegistryName()).equals(modid + ":gensokyo_forest")) {
                //     GensokyoOntology.LOGGER.info(String.valueOf(biome.getRegistryName()));
                // }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldGenerateEvent event) {
        if (!event.getWorld().isRemote()) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            if (serverWorld.getDimensionKey().equals(GSKODimensions.GENSOKYO)) {
                Biome biome = serverWorld.getBiome(event.getChunk().getPos().asBlockPos());
            }
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(final BiomeLoadingEvent event) {
        GSKOFeatureGeneration.generateOverworldTrees(event);
        GSKOFeatureGeneration.generateGensokyoTrees(event);
        GSKOFeatureGeneration.generateFlowers(event);
    }

    private static void spawnEntityIn(ServerWorld serverWorld, EntityClassification classification,
                                      WorldEvent.PotentialSpawns event) {

        List<ResourceLocation> biomeIds = Arrays.asList(
                new ResourceLocation("minecraft:plains"),
                new ResourceLocation("minecraft:desert"),
                new ResourceLocation("minecraft:forest"),
                new ResourceLocation("miencraft:taiga"),
                new ResourceLocation("minecraft:mountains"),
                new ResourceLocation("minecraft:snowy_tundra"),
                new ResourceLocation("minecraft:snowy_mountains"),
                new ResourceLocation("minecraft:jungle"),
                new ResourceLocation("minecraft:birch_forest"),
                new ResourceLocation("minecraft:savanna"),
                new ResourceLocation("minecraft:savanna_plateau"),
                new ResourceLocation("minecraft:dark_forest"),
                new ResourceLocation("minecraft:bamboo_jungle"),
                new ResourceLocation("minecraft:giant_spruce_taiga")
        );

        serverWorld.getChunkProvider().getChunkGenerator().getBiomeProvider()
                .getBiomes().forEach(biome -> spawnEntityIn(biome, biomeIds, classification));
    }

    private static <FC extends IFeatureConfig, F extends Feature<FC>> void generateFeatureIn(Biome biome, ConfiguredFeature<FC, F> feature) {


        // List<Supplier<ConfiguredFeature<?,?>>> base = biome.getGenerationSettings().getFeatures()
        //         .get(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal());
//
        // base.add(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),
        //         () -> feature.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
        //         .withPlacement(Placement.COUNT_EXTRA.configure(
        //                 new AtSurfaceWithExtraConfig(1, 0.8f, 2))));
    }

    private static void spawnEntityIn (Biome biome, List<ResourceLocation> biomeIds,
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

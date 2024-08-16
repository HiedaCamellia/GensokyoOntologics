package github.thelawf.gensokyoontology.common.world.structure;

import com.mojang.serialization.Codec;
import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

// -4742294992493429317
public class AliceHouse extends Structure<NoFeatureConfig> {
    public AliceHouse(Codec<NoFeatureConfig> codec) {
        super(codec);
    }


    @Override
    @NotNull
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }


    @Override
    @NotNull
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return AliceHouse.Start::new;
    }
    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_, MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) {
            super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
        }

        @Override
        protected int getMaxRefCount() {
            return 5;
        }

        @Override
        public void func_230364_a_(@NotNull DynamicRegistries dynamicRegistry, @NotNull ChunkGenerator chunkGenerator, @NotNull TemplateManager templateManagerIn,
                                   int chunkX, int chunkZ, @NotNull Biome p_230364_6_, @NotNull NoFeatureConfig p_230364_7_) {
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            BlockPos pos = new BlockPos(x, 0, z);

            // addPieces() Method
            JigsawManager.func_242837_a(dynamicRegistry,
                    new VillageConfig(() -> dynamicRegistry.getRegistry(Registry.JIGSAW_POOL_KEY)
                            .getOrDefault(ResourceLocation.parse(GensokyoOntology.MODID, "alice_house/start_pool")),
                            10), AbstractVillagePiece::new, chunkGenerator, templateManagerIn,
                    pos, this.components, this.rand, false, true);

            System.out.println(this.components.size());
            this.recalculateStructureSize();
        }
    }
}

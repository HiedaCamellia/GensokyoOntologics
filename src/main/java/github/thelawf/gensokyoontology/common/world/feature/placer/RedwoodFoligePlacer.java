package github.thelawf.gensokyoontology.common.world.feature.placer;

import net.minecraft.core.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ILevelGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class RedwoodFoligePlacer extends FoliagePlacer {
    public RedwoodFoligePlacer(FeatureSpread p_i241999_1_, FeatureSpread p_i241999_2_) {
        super(p_i241999_1_, p_i241999_2_);
    }

    @Override
    protected FoliagePlacerType<?> getPlacerType() {
        return null;
    }

    /**
     * 生成树的核心代码
     */
    @Override
    protected void func_230372_a_(ILevelGenerationReader reader, Random random, BaseTreeFeatureConfig config, int height, Foliage foliage, int p_230372_6_, int p_230372_7_, Set<BlockPos> blockPos, int p_230372_9_, MutableBoundingBox p_230372_10_) {

    }

    /**
     * 获取生成树的高度
     */
    @Override
    public int func_230374_a_(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return 0;
    }

    /**
     * 重写树的生成条件
     */
    @Override
    protected boolean func_230373_a_(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }
}

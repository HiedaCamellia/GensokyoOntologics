package github.thelawf.gensokyoontology.common.world.feature.placer;

import net.minecraft.core.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ILevelGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class SaigyoAyakashiTrunkPlacer extends AbstractTrunkPlacer {
    public SaigyoAyakashiTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> getPlacerType() {
        return null;
    }

    @Override
    public List<FoliagePlacer.Foliage> getFoliages(ILevelGenerationReader reader, Random rand, int treeHeight, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
        return null;
    }
}

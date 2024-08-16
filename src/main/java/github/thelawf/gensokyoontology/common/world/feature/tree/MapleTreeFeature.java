package github.thelawf.gensokyoontology.common.world.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.ILevelReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ILevelGenerationBaseReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class MapleTreeFeature extends Feature<NoFeatureConfig> {
    public MapleTreeFeature(Codec codec) {
        super(codec);
    }

    @SuppressWarnings("deprecation")
    private boolean isAirOrLeaves(ILevelGenerationBaseReader reader, BlockPos pos) {
        if (!(reader instanceof ILevelReader)) {
            return reader.hasBlockState(pos, state -> state.isAir() || state.isIn(BlockTags.LEAVES));
        } else {
            return reader.hasBlockState(pos, state -> state.canBeReplacedByLeaves((ILevelReader) reader, pos));
        }
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        return false;
    }
}

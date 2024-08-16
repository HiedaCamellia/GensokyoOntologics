package github.thelawf.gensokyoontology.common.world.feature.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class TwistedTreeFeature extends Feature<NoFeatureConfig> {


    public TwistedTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        return false;
    }
}

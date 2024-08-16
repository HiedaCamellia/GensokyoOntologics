package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class MagicLeaves extends LeavesBlock {
    public MagicLeaves() {
        super(Properties.from(Blocks.ACACIA_LEAVES).tickRandomly().sound(SoundType.PLANT));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {

    }
}

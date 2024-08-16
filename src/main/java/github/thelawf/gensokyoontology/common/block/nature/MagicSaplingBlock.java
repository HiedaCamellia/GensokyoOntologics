package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class MagicSaplingBlock extends SaplingBlock {
    public MagicSaplingBlock(Properties properties) {
        super(null, properties);
    }

    @Override
    public void placeTree(ServerLevel serverLevelIn, BlockPos posIn, BlockState stateIn, Random randomIn) {
        super.placeTree(serverLevelIn, posIn, stateIn, randomIn);
    }
}

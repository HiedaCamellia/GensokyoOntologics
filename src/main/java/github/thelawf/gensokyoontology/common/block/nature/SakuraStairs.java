package github.thelawf.gensokyoontology.common.block.nature;

import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItemUseContext;

import java.util.function.Supplier;

public class SakuraStairs extends StairsBlock {
    public SakuraStairs() {
        super(Blocks.OAK_STAIRS::getDefaultState, Properties.from(Blocks.OAK_STAIRS));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context);
    }
}

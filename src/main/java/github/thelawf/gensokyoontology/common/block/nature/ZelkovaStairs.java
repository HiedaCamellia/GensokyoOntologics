package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairsBlock;
import net.minecraft.world.item.BlockItemUseContext;
import org.jetbrains.annotations.NotNull;

public class ZelkovaStairs extends StairsBlock {
    public ZelkovaStairs() {
        super(Blocks.OAK_STAIRS::getDefaultState, Properties.from(Blocks.OAK_STAIRS));
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        return super.getStateForPlacement(context);
    }
}

package github.thelawf.gensokyoontology.common.block.decoration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;

public class AdobeTileBlock extends Block {
    public static final VoxelShape TILE_SHAPE = makeCuboidShape(0,0,0,1,1,1);
    public AdobeTileBlock() {
        super(Properties.from(Blocks.TERRACOTTA));
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return TILE_SHAPE;
    }

}

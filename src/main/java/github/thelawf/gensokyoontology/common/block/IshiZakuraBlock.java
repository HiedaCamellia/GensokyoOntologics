package github.thelawf.gensokyoontology.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;

public class IshiZakuraBlock extends Block {

    private static final VoxelShape shape;

    static {
        VoxelShape voxelShape = Block.makeCuboidShape(4, 0, 4, 12, 6, 12);
        shape = VoxelShapes.or(voxelShape);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return shape;
    }

    public IshiZakuraBlock() {
        super(Properties.from(Blocks.STONE).sound(SoundType.GLASS));
    }
}

package github.thelawf.gensokyoontology.common.block.decoration;

import github.thelawf.gensokyoontology.common.tileentity.AdobeTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClayAdobeBlock extends Block {

    public ClayAdobeBlock() {
        super(Properties.from(Blocks.TERRACOTTA));
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AdobeTileEntity();
    }

    // @SuppressWarnings("deprecation")
    public List<Vector3i> getCarvedPos(ServerLevel serverLevel, BlockPos pos) {
        if (serverLevel.getTileEntity(pos) instanceof AdobeTileEntity) {
            // this.getShape();
            AdobeTileEntity adobe = getTileEntity(serverLevel, pos);
            return adobe.getCarvedPositions();
        }
        return new ArrayList<>();
    }

    public AdobeTileEntity getTileEntity(ServerLevel serverLevel, BlockPos pos) {
        return (AdobeTileEntity) serverLevel.getTileEntity(pos);
    }

    public static final int[] VOXEL_LEVEL = new int[]{
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,

            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,

            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,

            0,0,0,0,  1,1,1,1,  1,1,1,1,  0,0,0,0,
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
            0,0,0,0,  0,0,0,0,  0,0,0,0,  0,0,0,0,
    };
}

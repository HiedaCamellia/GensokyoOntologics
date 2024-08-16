package github.thelawf.gensokyoontology.common.util.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class GSKOBlockProperties {
    public static final EnumProperty<CauldronFluid> CAULDRON_FLUID = EnumProperty.create("fluid", CauldronFluid.class);

    public static <T extends Comparable<T>, V extends T> BlockState getStateWith(Level world, BlockPos pos, Property<T> property, V value) {
        return world.getBlockState(pos).with(property, value);
    }

    public static List<VoxelShape> makeClockVoxel() {
        List<VoxelShape> voxelShapes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            // voxelShapes.add(Block.makeCuboidShape());
        }
        return voxelShapes;
    }

    // public static <T extends Comparable<T>, V extends T> BlockState containsProperty(Level world, BlockPos pos, Property<T> property, V value) {
    //     return world.getBlockState(pos).with(property, value);
    // }
}

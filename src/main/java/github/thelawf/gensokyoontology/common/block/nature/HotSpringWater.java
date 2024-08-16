package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.fluid.*;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILevelReader;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;

public class HotSpringWater extends ForgeFlowingFluid implements IForgeFluid {
    public HotSpringWater(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(@Nullable FluidState state) {
        return false;
    }

    @Override
    public int level(@Nullable FluidState state) {
        return 1;
    }

    public static class Flowing extends HotSpringWater {
        public Flowing(Properties properties) {
            super(properties);
        }

        @Override
        public boolean isEntityInside(FluidState state, ILevelReader world, BlockPos pos, Entity entity, double yToTest, Tag<Fluid> tag, boolean testingHead) {
            return super.isEntityInside(state, world, pos, entity, yToTest, tag, testingHead);
        }

        public int getAmount(FluidState p_207192_1_) {
            return 1;
        }

        public boolean isSource(FluidState p_207193_1_) {
            return false;
        }


    }

    protected boolean canConvertToSource() {
        return true;
    }

    public int getDropOff(ILevelReader p_204528_1_) {
        return 1;
    }

    public int getTickDelay(ILevelReader p_205569_1_) {
        return 5;
    }

    public boolean canBeReplacedWith(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return p_215665_5_ == Direction.DOWN;
    }

    public static class Source extends HotSpringWater {

        public Source(Properties properties) {
            super(properties);
        }

        @Override
        public boolean isEntityInside(FluidState state, ILevelReader world, BlockPos pos, Entity entity, double yToTest, Tag<Fluid> tag, boolean testingHead) {
            return super.isEntityInside(state, world, pos, entity, yToTest, tag, testingHead);
        }

        public int getAmount(FluidState p_207192_1_) {
            return 8;
        }

        public boolean isSource(FluidState p_207193_1_) {
            return true;
        }

    }

    @Override
    public boolean isEntityInside(FluidState state, ILevelReader world, BlockPos pos, Entity entity, double yToTest, Tag<Fluid> tag, boolean testingHead) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            living.heal(1.2F);
        }
        return true;
    }
}

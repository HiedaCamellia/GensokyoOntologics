package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LilyOfValleyBlock extends FlowerBlock {
    public LilyOfValleyBlock() {
        super(Effects.POISON, 2000, Properties.from(Blocks.DANDELION));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
        if (worldIn.isClientSide && entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;
            living.addPotionEffect(new EffectInstance(Effects.POISON, 2 * 50));
        }
    }
}

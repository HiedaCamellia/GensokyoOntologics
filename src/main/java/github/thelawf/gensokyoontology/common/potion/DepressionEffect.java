package github.thelawf.gensokyoontology.common.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class DepressionEffect extends MobEffect {
    public DepressionEffect() {
        super(EffectType.HARMFUL, 937512);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
    }
}

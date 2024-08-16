package github.thelawf.gensokyoontology.common.potion;

import github.thelawf.gensokyoontology.api.IEffectHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ManiaEffect extends MobEffect implements IEffectHandler {
    public ManiaEffect() {
        super(EffectType.HARMFUL, 127294);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
    }
}

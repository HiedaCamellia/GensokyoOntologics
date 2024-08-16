package github.thelawf.gensokyoontology.common.potion;

import github.thelawf.gensokyoontology.api.IEffectHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class LovePotionEffect extends MobEffect implements IEffectHandler {

    /* 也许是整活儿（误）？被恋恋的心型弹幕打中或者被迷恋药水打在脸上（迫真）之后跟随主人移动。
    被弹幕打中跟随弹幕发出者（恋恋贴贴~），被迷恋药水打中跟随药水制作人（什么，你问我能不能原地tp？
    我想说，你自己喝下去之后并没有任何效果）。
     */

    public LovePotionEffect() {
        super(EffectType.NEUTRAL, 16458003);
    }

    @Override
    public String getEntityId(String entityId) {
        return IEffectHandler.super.getEntityId(entityId);
    }

    @Override
    public String getPlayerName(String playerName) {
        return IEffectHandler.super.getPlayerName(playerName);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        Player thrower = entityLivingBaseIn.world.getClosestPlayer(entityLivingBaseIn, 16.0D);
        if (entityLivingBaseIn.world.isRemote || entityLivingBaseIn instanceof Player) {
            return;
        }
        if (thrower == null) {
            return;
        }
        entityLivingBaseIn.moveToBlockPosAndAngles(thrower.getPosition(),
                thrower.rotationYaw, thrower.rotationPitch);

        super.performEffect(entityLivingBaseIn, amplifier);
    }
}

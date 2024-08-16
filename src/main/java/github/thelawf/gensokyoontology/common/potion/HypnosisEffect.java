package github.thelawf.gensokyoontology.common.potion;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class HypnosisEffect extends MobEffect {

    public static final ResourceLocation HYPNOSIS_TEXTURE = ResourceLocation.parse(
            GensokyoOntology.MODID, "effect/hypnosis_effect");

    public HypnosisEffect() {
        super(EffectType.NEUTRAL, 0);
    }

    @Override
    public void performEffect(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        if (entityLivingBaseIn instanceof Player) {
            Player player = (Player) entityLivingBaseIn;
            ServerLevel serverLevel = (ServerLevel) player.level();
            serverLevel.setDayTime(13000);
            player.setPose(Pose.SLEEPING);
            player.startSleeping(player.getPosition());
        }
    }

}

package github.thelawf.gensokyoontology.common.events;

import github.thelawf.gensokyoontology.common.nbt.GensokyoOntologyNBT;
import github.thelawf.gensokyoontology.core.init.EffectRegistry;
import net.minecraft.command.impl.SetIdleTimeoutCommand;
import net.minecraft.command.impl.TimeCommand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.UUID;

@Deprecated
@EventBusSubscriber(modid = "gensokyoontology", bus = EventBusSubscriber.Bus.FORGE)
public class GSKOEffectEvents {
    @SubscribeEvent
    public static void onLoveEffectActivate(PotionEvent event) {
        // 恋爱效果（迫真）的实现
        LivingEntity living = event.getEntityLiving();
        if (!(living instanceof Player) &&
                event.getEntityLiving().isPotionActive(EffectRegistry.LOVE_EFFECT.get())) {
            System.out.println("OK");
        }
        if (living instanceof Player) {
            UUID uuid = ((Player) living).getGameProfile().getId();
            String name = ((Player) living).getGameProfile().getName();
            if (Objects.equals(name, GensokyoOntologyNBT.getLovePotionMasterName().getString())) {
                return;
            }
        }
    }

    public static void onHypnosisEffectActivate(PotionEvent event) {
        // 更改玩家姿态的代码在 Player.java的updatePose()方法中实现。
        // 在这个事件中我设置玩家睡觉的姿态，并跳过10000个游戏刻的时间。

        // Player player = (Player) event.getEntityLiving();
        // if (event.getEntityLiving() instanceof ServerPlayer) {
        //     if (event.getEntityLiving().isPotionActive(EffectRegistry.HYPNOSIS_EFFECT.get())) {
        //         ServerLevel serverLevel = (ServerLevel) player.level();
        //         serverLevel.setDayTime(13000);
        //         event.getEntityLiving().setPose(Pose.SLEEPING);
        //         event.getEntityLiving().startSleeping(event.getEntityLiving().getPosition());
        //     }
        // }
    }

    @SubscribeEvent
    public static void onInsomniaEffectActivate(PotionEvent event) {

    }

    @SubscribeEvent
    public static void onManiaEffectActivate(PotionEvent event) {

    }
}

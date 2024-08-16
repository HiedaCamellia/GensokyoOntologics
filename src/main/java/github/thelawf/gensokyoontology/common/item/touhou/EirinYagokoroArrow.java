package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import net.minecraft.world.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class EirinYagokoroArrow extends ArrowItem {
    public EirinYagokoroArrow(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        if (worldIn.isRemote) return super.onItemRightClick(worldIn, playerIn, handIn);
        ServerLevel serverLevel = (ServerLevel) worldIn;
        serverLevel.setDayTime(16000);
        if (serverLevel.dimension() != GSKODimensions.GENSOKYO)
            return super.onItemRightClick(worldIn, playerIn, handIn);

        serverLevel.getCapability(GSKOCapabilities.IMPERISHABLE_NIGHT).ifPresent(cap -> {
            cap.setTriggered(true);
            cap.setDayTime(18000);
            serverLevel.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(cap.isTriggered(), serverLevel.getServer());
        });
        playerIn.sendSystemMessage((Component.literal("Game rule doDayLightCycle is: " +
                serverLevel.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).get()), Component.literal(String.valueOf(playerIn.getUUID())));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

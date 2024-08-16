package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import net.minecraft.world.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class BombItem extends Item {
    private boolean flag;
    public BombItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (stack.hasTag() && stack.getTag() != null && stack.getTag().contains("is_triggered")){
            playerIn.sendSystemMessage((Component.literal("has_tag"), Component.literal(String.valueOf(playerIn.getUUID())));
            setCapabilityTriggered(worldIn, playerIn, false);
            if (worldIn.isClientSide) {
                ServerLevel serverLevel = (ServerLevel) worldIn;
                serverLevel.getServer().levels().forEach(sw -> sw.setDayTime(15000));
            }
            stack.setTag(new CompoundTag());
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("isTriggered", this.flag);
        playerIn.sendSystemMessage((Component.literal("no_tag"), Component.literal(String.valueOf(playerIn.getUUID())));
        setCapabilityTriggered(worldIn, playerIn, true);
        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            serverLevel.getServer().levels().forEach(sw -> sw.setDayTime(15000));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void setCapabilityTriggered(Level worldIn, Player playerIn, boolean triggered) {
        //if (worldIn.isRemote) ImperishableNightPacket.sendToServer(worldIn, GSKONetworking.IMPERISHABLE_NIGHT, triggered);
        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            serverLevel.getCapability(GSKOCapabilities.IMPERISHABLE_NIGHT).ifPresent(cap -> {
                cap.setTriggered(triggered);
                // playerIn.sendSystemMessage((Component.literal(String.valueOf(cap.isTriggered())), Component.literal(String.valueOf(playerIn.getUUID())));
                serverLevel.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(cap.isTriggered(), serverLevel.getServer());
            });
            //ImperishableNightPacket.sendToPlayer(serverLevel, (ServerPlayer) playerIn, GSKONetworking.IMPERISHABLE_NIGHT, triggered);
        }

    }

}

package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.network.GSKONetworking;
import github.thelawf.gensokyoontology.common.network.packet.CPowerChangedPacket;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 *
 * Player.noClip 字段决定玩家能否与方块进行碰撞，所以这里需要将这个字段设为false以便让玩家使用霍青娥的能力。当能力启动时，将会以
 * 1 power/秒 的速度通过消耗玩家的power点数来让玩家实现穿墙效果。
 */
public class SeigaHairpin extends Item {
    private int maxTick = 0;
    private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(BlockPos.ZERO, BlockPos.ZERO);
    public SeigaHairpin(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!GSKONBTUtil.hasAndContainsTag(stack, "maxTick")) {
            CompoundTag nbt = new CompoundTag();

            playerIn.getCapability(GSKOCapabilities.POWER).ifPresent(gskoCap -> {
                int count = (int) (gskoCap.getCount() * 20);
                nbt.putInt("maxTick", playerIn.ticksExisted + count);
                nbt.putInt("yHeight", (int) playerIn.getPosY());
            });
            stack.setTag(nbt);
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        else {
            playerIn.noClip = false;
            playerIn.setNoGravity(false);
            stack.setTag(new CompoundTag());
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static void trySetNoClip(Player player, ItemStack stack) {
        if (!GSKONBTUtil.hasAndContainsTag(stack, "maxTick")) return;
        int tick = GSKONBTUtil.getNonNullTag(stack, "maxTick").getInt("maxTick");
        int yHeight = GSKONBTUtil.getNonNullTag(stack, "yHeight").getInt("yHeight");

        if (player.ticksExisted < tick) {
            player.noClip = true;
            player.setNoGravity(true);

            player.getCapability(GSKOCapabilities.POWER).ifPresent(gskoCap -> {
                GSKONetworking.sendToClientPlayer(new CPowerChangedPacket(gskoCap.getCount() - 0.01f), player);
            });

        }
        else {
            player.noClip = false;
            player.setNoGravity(false);
            stack.setTag(new CompoundTag());
        }

    }
}

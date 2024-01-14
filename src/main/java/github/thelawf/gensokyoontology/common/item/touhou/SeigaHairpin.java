package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 *
 * PlayerEntity.noClip 字段决定玩家能否与方块进行碰撞，所以这里需要将这个字段设为false以便让玩家使用霍青娥的能力。当能力启动时，将会以
 * 1 power/秒 的速度通过消耗玩家的power点数来让玩家实现穿墙效果。
 */
public class SeigaHairpin extends Item {
    private int maxTick = 0;
    public SeigaHairpin(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!stack.hasTag()) {
            CompoundNBT nbt = new CompoundNBT();
            playerIn.getCapability(GSKOCapabilities.POWER).ifPresent(cap -> {
                int count = (int) (cap.getCount() * 2000);
                nbt.putInt("max_tick", playerIn.ticksExisted + count);
            });
            stack.setTag(nbt);
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        else {
            stack.setTag(new CompoundNBT());
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

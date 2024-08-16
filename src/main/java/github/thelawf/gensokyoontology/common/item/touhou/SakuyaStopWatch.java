package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 咲夜的怀表物品
 */
public class SakuyaStopWatch extends Item implements IRayTraceReader{
    public static int pauseTicks = 100;
    public static int totalTicks = 0;

    public SakuyaStopWatch(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (stack.getTag() != null && stack.hasTag()) {
            stack.setTag(new CompoundTag());
            totalTicks = 0;

            if (playerIn.isCreative()) return super.onItemRightClick(worldIn, playerIn, handIn);
            playerIn.getCooldownTracker().setCooldown(this, 6000);
            return ActionResult.resultPass(stack);
        }

        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("pause_start", true);
        stack.setTag(nbt);
        totalTicks = pauseTicks + playerIn.ticksExisted;

        if (playerIn.isCreative())
            return super.onItemRightClick(worldIn, playerIn, handIn);
        if (playerIn.getHeldItemMainhand().getItem() == this &&
                playerIn.getHeldItemOffhand().getItem() == ItemRegistry.WASHI_PAPER.get()) {
            ItemStack offHand = playerIn.getHeldItemOffhand();
            offHand.shrink(1);
            ItemStack itemStack = new ItemStack(ItemRegistry.TIME_STAMP.get());
            playerIn.setHeldItem(Hand.OFF_HAND, itemStack);
        }

        playerIn.getCooldownTracker().setCooldown(this, 6000);
        return ActionResult.resultPass(stack);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".sakuya_stop_watch"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }

}

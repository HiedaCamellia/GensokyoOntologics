package github.thelawf.gensokyoontology.common.item.danmaku;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderPearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DanmakuItem extends Item {
    public DanmakuItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {

        if (playerIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof DanmakuItem) {
            ItemStack stack = playerIn.getHeldItem(Hand.MAIN_HAND);
            stack.shrink(1);
            return ActionResult.resultConsume(stack);
        } else if (playerIn.getHeldItem(Hand.OFF_HAND).getItem() instanceof DanmakuItem) {
            ItemStack stack = playerIn.getHeldItem(Hand.OFF_HAND);
            stack.shrink(1);
            return ActionResult.resultConsume(stack);
        } else {
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        }
    }
}

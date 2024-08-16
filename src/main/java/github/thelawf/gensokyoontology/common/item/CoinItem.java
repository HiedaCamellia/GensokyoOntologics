package github.thelawf.gensokyoontology.common.item;

import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.core.PlacerRegistry;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;

//TODO: 把名称改为 “赛钱”
public class CoinItem extends Item {
    public float value;
    public CoinItem(float value) {
        super(new Properties());
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, Hand handIn) {
        // GSKOUtil.showChatMsg(playerIn, PlacerRegistry.BRANCH_TRUNK_PLACER.toString(), 1);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

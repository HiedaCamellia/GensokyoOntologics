package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import github.thelawf.gensokyoontology.common.item.danmaku.DanmakuItem;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOCombatTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class SpellCardItem extends Item {

    public SpellCardItem() {
        super(new Item.Properties().group(GSKOCombatTab.GSKO_COMBAT_TAB).stacksTo(1));
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn,@NotNull Player playerIn, @NotNull Hand handIn) {
        if (playerIn.isCreative()) {
            applySpell(worldIn, playerIn);
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }

        if (playerIn.getCooldownTracker().hasCooldown(this)) return ActionResult.resultFail(playerIn.getHeldItem(handIn));
        applySpell(worldIn, playerIn);
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    protected abstract void applySpell(Level worldIn, Player playerIn);
}

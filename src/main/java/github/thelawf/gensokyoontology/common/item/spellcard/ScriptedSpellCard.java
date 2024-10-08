package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.ScriptedSpellCardEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ScriptedSpellCard extends SpellCardItem {

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        if (playerIn.getCooldownTracker().hasCooldown(this))
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getTag() != null && stack.getTag().contains("scripts")) {
            ScriptedSpellCardEntity scriptedSpellCard = new ScriptedSpellCardEntity(worldIn, playerIn, stack.getTag());
            worldIn.addEntity(scriptedSpellCard);

            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {

    }
}

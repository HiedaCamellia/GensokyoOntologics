package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.HellEclipseEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.ManiaDepressEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

/**
 * 恋恋的符卡：心符「躁狂的心理障碍者」
 */
public class SC_ManiaDepress extends SpellCardItem {

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, Hand handIn) {
        if (playerIn.getCooldownTracker().hasCooldown(this))
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        if (worldIn instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) worldIn;

            ManiaDepressEntity maniaDepress = new ManiaDepressEntity(worldIn, playerIn);
            worldIn.addEntity(maniaDepress);

            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {

    }
}

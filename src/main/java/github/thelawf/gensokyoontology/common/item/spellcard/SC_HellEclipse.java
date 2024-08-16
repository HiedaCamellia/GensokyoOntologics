package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.HellEclipseEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class SC_HellEclipse extends SpellCardItem {

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {
        if (worldIn instanceof ServerLevel) {

            HellEclipseEntity hellEclipse = new HellEclipseEntity(worldIn, playerIn);
            worldIn.addEntity(hellEclipse);

            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }
    }

}

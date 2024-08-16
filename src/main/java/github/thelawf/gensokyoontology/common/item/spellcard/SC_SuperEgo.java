package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.SuperEgoSpellEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class SC_SuperEgo extends SpellCardItem {

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {
        if (worldIn instanceof ServerLevel) {
            SuperEgoSpellEntity spiralWheel = new SuperEgoSpellEntity(worldIn, playerIn);
            worldIn.addEntity(spiralWheel);
            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }
    }

}

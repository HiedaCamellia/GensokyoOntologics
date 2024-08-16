package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.RorschachDanmakuEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SC_RorshachDanmaku extends SpellCardItem{

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {
        RorschachDanmakuEntity rorschach = new RorschachDanmakuEntity(worldIn, playerIn);
        worldIn.addEntity(rorschach);
    }
}

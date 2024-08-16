package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.WaveAndParticleEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class SC_WaveAndParticle extends SpellCardItem {

    @Override
    protected void applySpell(Level world, Player player) {
        WaveAndParticleEntity waveAndParticle = new WaveAndParticleEntity(world, player);
        world.addEntity(waveAndParticle);
    }

}

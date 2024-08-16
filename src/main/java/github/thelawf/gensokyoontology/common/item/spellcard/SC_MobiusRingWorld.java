package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.spellcard.MobiusRingEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.MountainOfFaithEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SC_MobiusRingLevel extends SpellCardItem {

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {
        if (worldIn instanceof ServerLevel) {
            MobiusRingEntity spellCard = new MobiusRingEntity(worldIn, playerIn);
            worldIn.addEntity(spellCard);
            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip." + GensokyoOntology.MODID +
                ".spellcard.mobius_ring_world.info_line_1"));
        tooltip.add(Component.translatable("tooltip." + GensokyoOntology.MODID +
                ".spellcard.mobius_ring_world.info_line_2"));
    }
}

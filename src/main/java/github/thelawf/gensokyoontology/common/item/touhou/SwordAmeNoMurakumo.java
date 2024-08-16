package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwordAmeNoMurakumo extends SwordItem {
    public SwordAmeNoMurakumo(Properties properties) {
        super(GSKOItemTier.CRIMSON_ALLOY, 2, 2.0F, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip." + GensokyoOntology.MODID +
                ".three_sacred_treasures.sword"));
    }
}

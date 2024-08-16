package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class YataMirrorBlock extends Block {
    public YataMirrorBlock() {
        super(Properties.from(Blocks.OBSIDIAN));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<Component> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip." + GensokyoOntology.MODID +
                ".three_sacred_treasures.mirror"));
    }
}

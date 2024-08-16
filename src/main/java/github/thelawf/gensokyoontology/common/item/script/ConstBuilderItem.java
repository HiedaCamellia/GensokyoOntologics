package github.thelawf.gensokyoontology.common.item.script;

import github.thelawf.gensokyoontology.common.container.script.CBContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class ConstBuilderItem extends ScriptBuilderItem{
    @Override
    public void openScriptEditGUI(Level world, Player player, ItemStack stack) {
        // minecraft.displayGuiScreen(new ConstBuilderScreen(title, stack));
        if (!world.isRemote) player.openContainer(CBContainer.create("const_builder"));
    }
}

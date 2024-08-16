package github.thelawf.gensokyoontology.client.gui.screen.script;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class VariableBuilderScreen extends Screen {
    public VariableBuilderScreen(Component titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        if (this.minecraft == null) return;
        if (this.minecraft.player == null) return;
    }
}

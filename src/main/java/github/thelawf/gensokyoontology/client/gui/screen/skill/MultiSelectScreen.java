package github.thelawf.gensokyoontology.client.gui.screen.skill;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MultiSelectScreen extends Screen {
    protected MultiSelectScreen(Component titleIn) {
        super(titleIn);
    }
}

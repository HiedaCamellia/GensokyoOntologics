package github.thelawf.gensokyoontology.api.client.layout;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class LineralBoxScreen extends Screen {

    public List<List<WidgetConfig>> configs;

    public LineralBoxScreen(Component title, List<List<WidgetConfig>> configs) {
        super(title);
        this.configs = configs;
    }
}

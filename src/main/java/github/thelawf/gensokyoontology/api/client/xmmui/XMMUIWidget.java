package github.thelawf.gensokyoontology.api.client.xmmui;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

public abstract class XMMUIWidget extends Widget {

    public Method onHoverMethod;
    public XMMUIWidget(int x, int y, int width, int height, Component title, String onHover) {
        super(x, y, width, height, title);
    }

}

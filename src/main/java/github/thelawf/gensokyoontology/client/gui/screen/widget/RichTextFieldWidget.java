package github.thelawf.gensokyoontology.client.gui.screen.widget;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class RichEditBox extends EditBox {
    private final ResourceLocation TEXT_FILED_TEXTURE;
    public RichEditBox(FontRenderer fontRenderer, int x, int y, int width, int height, Component title, ResourceLocation texture, int u, int v) {
        super(fontRenderer, x, y, width, height, title);
        this.TEXT_FILED_TEXTURE = texture;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}

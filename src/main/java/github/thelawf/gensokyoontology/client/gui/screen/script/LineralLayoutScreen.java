package github.thelawf.gensokyoontology.client.gui.screen.script;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.api.client.IInputParser;
import github.thelawf.gensokyoontology.api.client.ITextBuilder;
import github.thelawf.gensokyoontology.api.client.layout.WidgetConfig;
import github.thelawf.gensokyoontology.client.gui.screen.widget.BlankWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class LineralLayoutScreen extends Screen implements IInputParser, ITextBuilder {
    protected final BlankWidget blank = BlankWidget.INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    protected LineralLayoutScreen(Component titleIn) {
        super(titleIn);
    }

    public void initByConfig(List<WidgetConfig> configs, int x, int y) {
        for (WidgetConfig config : configs) {
            x += config.leftInterval;
            y += config.upInterval;

            // LOGGER.info("widget: {}, x: {}, y: {}", config.text.getString(), x, y);
            addWidget(x, y, config);
        }
    }

    public void setCenteredWidgets(List<WidgetConfig> configs) {
        int width = 0;
        int parentWidth = 255;
        for (WidgetConfig config : configs) width += config.width;
        int x = (parentWidth - width) / 2, y = 0;
        initByConfig(configs, x, y);
    }

    public void setAbsoluteXY(List<WidgetConfig> configs) {
        int x,y;
        for (WidgetConfig config : configs) {
            x = config.leftInterval;
            y = config.upInterval;

            // LOGGER.info("widget: {}, x: {}, y: {}", config.text.getString(), x, y);
            // addWidget(config);
            addWidget(x, y, config);
        }
    }

    private void addWidget(int x, int y, WidgetConfig config) {
        if (config.widget instanceof Button) {
            config.widget = new Button(x, y, config.width, config.height, config.text, config.action);
            this.addButton(config.widget);
        }
        if (config.widget instanceof EditBox) {
            config.widget.x = x;
            config.widget.y = y;
            config.widget.setWidth(config.width);
            config.widget.setHeight(config.height);
            config.widget.setMessage(config.text);
            this.children.add(config.widget);
        }
    }


    public void drawCenteredText(List<WidgetConfig> configs, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int width = 0;
        int parentWidth = 255;
        for (WidgetConfig config : configs) width += config.width;
        int x = (parentWidth - width) / 2, y = 0;
        for (WidgetConfig config : configs) {
            x += config.width;
            y += config.height;
            y += config.upInterval;

            if (config.isText) drawString(matrixStack, config.fontRenderer, config.text, x, y, 16777215);
        }
    }

    public void renderWidgets(List<WidgetConfig> configs, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int width = 0;
        int parentWidth = 255;
        for (WidgetConfig config : configs) width += config.width;
        int x = (parentWidth - width) / 2, y = 0;
        for (WidgetConfig config : configs) {
            x += config.width;
            y += config.height;
            y += config.upInterval;

            if (config.isText) drawString(matrixStack, this.font, config.text, x, y, 16777215);
            else config.widget.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    public void renderAbsoluteXY(List<WidgetConfig> configs, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int x, y;
        for (WidgetConfig config : configs) {
            x = config.leftInterval;
            y = config.upInterval;

            if (config.isText) drawString(matrixStack, config.fontRenderer, config.text, x, y, 16777215);
            else config.widget.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }


    public void renderIntervalRelative(List<WidgetConfig> configs, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int x = 0, y = 0;
        for (WidgetConfig config : configs) {
            x += config.leftInterval;
            y += config.upInterval;

            if (config.isText) drawString(matrixStack, config.fontRenderer, config.text, x, y, 16777215);
            else config.widget.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}

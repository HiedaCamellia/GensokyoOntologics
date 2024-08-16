package github.thelawf.gensokyoontology.client.gui.screen.script;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.client.layout.WidgetConfig;
import github.thelawf.gensokyoontology.client.gui.screen.widget.BlankWidget;
import github.thelawf.gensokyoontology.common.container.script.FunctionInvokerContainer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class InvokerContainerScreen<C extends FunctionInvokerContainer> extends ScriptContainerScreen<C>{
    protected Button functionNameBtn;
    public final WidgetConfig PARAMS_LABEL = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);
    public final WidgetConfig RETURN_LABEL = WidgetConfig.of(new BlankWidget(0,0,0,0, withText("null")),0,0).isText(true);
    public static final Component PARAMS_TEXT = GensokyoOntology.withTranslation("gui.", ".func_invoker.params");
    public static final Component RETURN_TEXT = GensokyoOntology.withTranslation("gui.", ".func_invoker.return");
    public InvokerContainerScreen(C screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

}

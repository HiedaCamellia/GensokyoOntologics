package github.thelawf.gensokyoontology.client.gui.screen.script;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.container.script.ScriptBuilderContainer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public abstract class ScriptContainerScreen<C extends ScriptBuilderContainer> extends LineralContainerScreen<C> {
    protected Button saveBtn;

    protected ItemStack stack;
    protected final Component fieldName = GensokyoOntology.withTranslation("gui.", ".script_builder.fieldName");
    protected Component saveText = GensokyoOntology.withTranslation("gui.", ".script.button.save");
    public ScriptContainerScreen(C screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public boolean isPauseScreen() {
        return super.isPauseScreen();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (hoveredSlot != null && hoveredSlot.getHasStack()) {
            ItemStack stack = hoveredSlot.getStack();
            renderTooltip(matrixStack, stack, mouseX, mouseY);
        }
    }
}

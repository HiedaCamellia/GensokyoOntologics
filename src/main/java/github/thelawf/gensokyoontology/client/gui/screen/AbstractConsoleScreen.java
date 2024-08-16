package github.thelawf.gensokyoontology.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.container.SpellCardConsoleContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

public class AbstractConsoleScreen extends ContainerScreen<SpellCardConsoleContainer> {
    /** Menu Hierarchy of Danmaku Command Table 弹幕控制台的菜单层级 */

    // Main Hierarchy 主层级
    private static final Component ADD_COMMAND = GensokyoOntology.withTranslation("",".add_cmd");
    private static final Component NEXT = GensokyoOntology.withTranslation("", "next");
    private static final Component NEW_INSTANCE = GensokyoOntology.withTranslation("", ".new_instance");
    private static final Component ASSIGN = GensokyoOntology.withTranslation("",".assign");
    private static final Component FOR_LOOP = GensokyoOntology.withTranslation("",".for_loop");
    private static final Component IF_BRANCH = GensokyoOntology.withTranslation("",".if_branch");
    private static final Component BINARY_OPT = GensokyoOntology.withTranslation("",".binary_opt");

    Button addCmd;
    Button done;
    Button next;
    Button saveAll;

    // Hierarchy of Vec3 Behavior Control 向量行为控制的层级

    // Hierarchy of SpellCard Behavior Control 符卡行为控制的层级
    public AbstractConsoleScreen(SpellCardConsoleContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }
}

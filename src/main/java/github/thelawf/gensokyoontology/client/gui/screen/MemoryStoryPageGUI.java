package github.thelawf.gensokyoontology.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;


public class MemoryStoryPageGUI extends Screen {

    Button nextPage;
    Button prevPage;

    @Override
    protected void init() {
        super.init();
        nextPage = new Button(this.width, this.height, 140, 40, Component.translatable(
                "client.gensokyoontology.story_page.next"), onPress -> {
        });
        prevPage = new Button(0, this.height, 140, 40, Component.translatable(
                "client.gensokyoontology.story_page.prev"), onPress -> {
        });
    }

    private final ItemStack storyPageItem;

    protected MemoryStoryPageGUI(Component titleIn, ItemStack storyPageItem) {
        super(titleIn);
        this.storyPageItem = storyPageItem;
    }

    public ItemStack getStoryPageItem() {
        return storyPageItem;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderBackground(matrixStack);
        if (storyPageItem.getTag() == null) return;

        int i = this.width - 3;
        int j = this.height - 3;

        drawString(matrixStack, this.font, Component.translatable(
                storyPageItem.getTag().getString("story")), 900, i, j);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
    }
}

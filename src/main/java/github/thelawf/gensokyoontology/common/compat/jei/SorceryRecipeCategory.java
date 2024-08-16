package github.thelawf.gensokyoontology.common.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.gui.screen.DanmakuCraftingScreen;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import github.thelawf.gensokyoontology.data.recipe.DanmakuRecipe;
import github.thelawf.gensokyoontology.data.recipe.SorceryExtractorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SorceryRecipeCategory implements IRecipeCategory<DanmakuRecipe> {

    public static final ResourceLocation UID = ResourceLocation.parse(GensokyoOntology.MODID, "sorcery_extract");
    public static final ResourceLocation TEXTURE = DanmakuCraftingScreen.DANMAKU_CRAFTING_TEXTURE;

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawable slotDrawable;

    public SorceryRecipeCategory(IGuiHelper helper) {
        this.slotDrawable = helper.getSlotDrawable();
        this.background = helper.createDrawable(TEXTURE, 0, 0, 217, 211);
        this.icon = helper.createDrawableIngredient(new ItemStack(BlockRegistry.DANMAKU_TABLE.get()));
    }

    @Override
    @NotNull
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Class<? extends DanmakuRecipe> getRecipeClass() {
        return DanmakuRecipe.class;
    }

    @Override
    @NotNull
    public String getTitle() {
        return BlockRegistry.SORCERY_EXTRACTOR.get().getTranslatedName().getString();
    }

    @Override
    @NotNull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @NotNull
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(DanmakuRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull DanmakuRecipe recipe, @NotNull IIngredients ingredients) {
        IGuiItemStackGroup guiStack = recipeLayout.getItemStacks();

        guiStack.init(0, true, 99, 12);
        guiStack.init(1, true, 54, 56);
        guiStack.init(2, true, 145, 56);
        guiStack.init(3, true, 99, 101);
        guiStack.init(4, false, 99, 56);

        guiStack.setBackground(0, this.slotDrawable);
        guiStack.setBackground(1, this.slotDrawable);
        guiStack.setBackground(2, this.slotDrawable);
        guiStack.setBackground(3, this.slotDrawable);
        guiStack.setBackground(4, this.slotDrawable);

        guiStack.set(ingredients);
    }

    @Override
    public void draw(@NotNull DanmakuRecipe recipe, @NotNull MatrixStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        // matrixStack.push();
        // matrixStack.scale(0.6f,0.6f,0.6f);
        // this.background.draw(matrixStack);
        // matrixStack.pop();
    }
}

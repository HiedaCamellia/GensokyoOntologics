package github.thelawf.gensokyoontology.data.recipe;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.IRecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public interface ISorceryExtractorRecipe extends IRecipe<IInventory> {
    ResourceLocation RECIPE_ID = ResourceLocation.parse(GensokyoOntology.MODID, "sorcery_extract");

    @Override
    @NotNull
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getOptional(RECIPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic() {
        return true;
    }
}

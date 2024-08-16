package github.thelawf.gensokyoontology.data;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.List;

public final class GSKOFluidTags extends FluidTagsProvider {
    private static final TagRegistry<Block> REGISTRY = TagRegistryManager.create(
            ResourceLocation.parse("fluid"), ITagCollectionSupplier::getBlockTags
    );

    public static final ITag.INamedTag<Block> HOT_SPRING = makeWrapperTag("hot_spring");

    public GSKOFluidTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    public static ITag.INamedTag<Block> makeWrapperTag(String id) {
        return REGISTRY.createTag(id);
    }

    public static List<? extends ITag.INamedTag<Block>> getAllTags() {
        return REGISTRY.getTags();
    }

}

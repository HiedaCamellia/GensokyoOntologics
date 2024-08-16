package github.thelawf.gensokyoontology.common.item;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;

public class ObliviousTales extends Item {
    private CompoundTag storyNBT;

    public ObliviousTales(final CompoundTag storyNBT) {
        super(new Properties().stacksTo(1));
        this.storyNBT = storyNBT;
    }

    public CompoundTag getStoryNBT() {
        return storyNBT;
    }

    public void setStoryNBT(CompoundTag storyNBT) {
        this.storyNBT = storyNBT;
    }
}

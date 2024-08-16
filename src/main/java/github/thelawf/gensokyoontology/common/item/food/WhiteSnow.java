package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.common.nbt.GensokyoOntologyNBT;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.world.item.Items;

public class WhiteSnow extends Item {
    private static final Food food = new Food.Builder()
            .saturation(10)
            .hunger(7)
            .setAlwaysEdible()
            .build();

    public WhiteSnow() {
        super(new Properties().containerItem(Items.BOWL).food(food));
        this.updateItemStackNBT(GensokyoOntologyNBT.nbtWhiteSnow);
    }
}

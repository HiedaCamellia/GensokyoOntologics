package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;

public class Lingoame extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(4)
            .hunger(6)
            .setAlwaysEdible()
            .build();

    public Lingoame() {
        super(new Properties().food(food));
    }
}

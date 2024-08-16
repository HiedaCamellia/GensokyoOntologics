package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;

public class TakoYaki extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(7)
            .hunger(5)
            .setAlwaysEdible()
            .build();

    public TakoYaki() {
        super(new Properties().food(food));
    }
}

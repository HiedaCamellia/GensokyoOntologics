package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;

public class BurgerMeat extends Item {
    private static final Food food = new Food.Builder()
            .saturation(6)
            .hunger(8)
            .meat()
            .setAlwaysEdible()
            .build();

    public BurgerMeat() {
        super(new Properties().food(food));
    }
}

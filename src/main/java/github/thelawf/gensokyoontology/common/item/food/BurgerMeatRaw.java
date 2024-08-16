package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;

public class BurgerMeatRaw extends Item {
    private static final Food food = new Food.Builder()
            .saturation(2)
            .hunger(-2)
            .meat()
            .build();

    public BurgerMeatRaw() {
        super(new Properties().food(food));
    }
}

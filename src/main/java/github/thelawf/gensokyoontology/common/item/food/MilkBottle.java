package github.thelawf.gensokyoontology.common.item.food;

import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.world.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public class MilkBottle extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(2)
            .hunger(2)
            .build();

    public MilkBottle() {
        super(new Properties().group(ItemGroup.FOOD).containerItem(Items.GLASS_BOTTLE).food(food));
    }

    @Override
    @Nonnull
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }
}

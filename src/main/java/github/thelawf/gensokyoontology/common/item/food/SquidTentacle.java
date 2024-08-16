package github.thelawf.gensokyoontology.common.item.food;

import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;

public class SquidTentacle extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(1)
            .hunger(1)
            .effect(() -> new EffectInstance(Effects.MINING_FATIGUE, 2 * 80, 2), 0.75F)
            .build();

    public SquidTentacle() {
        super(new Properties().group(ItemGroup.FOOD).food(food));
    }
}


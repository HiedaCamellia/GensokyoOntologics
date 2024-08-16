package github.thelawf.gensokyoontology.common.item.food;

import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.item.Food;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class KoishiHatMousse extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(12)
            .hunger(10)
            .effect(() -> new EffectInstance(Effects.INVISIBILITY, 5 * 150), 0.8f)
            .setAlwaysEdible()
            .build();

    public KoishiHatMousse() {
        super(new Properties().food(food));
    }
}

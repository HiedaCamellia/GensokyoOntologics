package github.thelawf.gensokyoontology.common.item.touhou;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class InyoJade extends Item {

    private final DyeColor dyeColor;

    public InyoJade(DyeColor dyeColor, Properties properties) {
        super(properties);
        this.dyeColor = dyeColor;

    }
}

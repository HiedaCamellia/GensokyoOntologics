package github.thelawf.gensokyoontology.common.item.tool;

import github.thelawf.gensokyoontology.common.item.touhou.GSKOItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

public class JadeShovel extends ShovelItem {
    public JadeShovel(Properties properties) {
        super(GSKOItemTier.JADE, 4.5f, 1.6f, properties);
    }
}

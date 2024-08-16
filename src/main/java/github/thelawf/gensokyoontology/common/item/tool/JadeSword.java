package github.thelawf.gensokyoontology.common.item.tool;

import github.thelawf.gensokyoontology.common.item.touhou.GSKOItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class JadeSword extends SwordItem {
    public JadeSword(Properties properties) {
        super(GSKOItemTier.JADE, 6, 2f, properties);
    }
}

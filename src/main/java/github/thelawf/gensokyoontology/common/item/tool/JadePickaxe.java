package github.thelawf.gensokyoontology.common.item.tool;

import github.thelawf.gensokyoontology.common.item.touhou.GSKOItemTier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class JadePickaxe extends PickaxeItem {
    public JadePickaxe(Properties properties) {
        super(GSKOItemTier.JADE, 5, 0.6f, properties);
    }
}

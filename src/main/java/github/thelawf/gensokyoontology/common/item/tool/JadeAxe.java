package github.thelawf.gensokyoontology.common.item.tool;

import github.thelawf.gensokyoontology.common.item.touhou.GSKOItemTier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class JadeAxe extends AxeItem {
    public JadeAxe(Properties properties) {
        super(GSKOItemTier.JADE, 8, 1.6f, properties);
    }
}

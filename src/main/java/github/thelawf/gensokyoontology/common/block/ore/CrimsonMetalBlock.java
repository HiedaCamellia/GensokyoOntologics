package github.thelawf.gensokyoontology.common.block.ore;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CrimsonMetalBlock extends Block {
    public CrimsonMetalBlock() {
        super(Properties.from(Blocks.ANCIENT_DEBRIS).harvestLevel(4));
    }
}

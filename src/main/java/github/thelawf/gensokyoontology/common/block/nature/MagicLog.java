package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.AbstractBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class MagicLog extends RotatedPillarBlock {
    public MagicLog() {
        super(Properties.from(Blocks.OAK_LOG));
    }
}

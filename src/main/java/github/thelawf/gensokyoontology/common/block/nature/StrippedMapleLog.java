package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;

public class StrippedMapleLog extends RotatedPillarBlock {
    public StrippedMapleLog() {
        super(Properties.from(Blocks.STRIPPED_ACACIA_LOG).sound(SoundType.WOOD));
    }
}

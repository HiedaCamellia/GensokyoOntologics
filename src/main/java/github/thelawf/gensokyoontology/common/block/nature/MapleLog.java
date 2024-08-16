package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;

public class MapleLog extends RotatedPillarBlock {
    public MapleLog() {
        super(Properties.from(Blocks.OAK_LOG).sound(SoundType.WOOD));
    }
}

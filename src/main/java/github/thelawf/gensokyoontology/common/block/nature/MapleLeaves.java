package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;

public class MapleLeaves extends LeavesBlock {
    public MapleLeaves() {
        super(Properties.from(Blocks.OAK_LEAVES).tickRandomly().sound(SoundType.PLANT));
    }
}

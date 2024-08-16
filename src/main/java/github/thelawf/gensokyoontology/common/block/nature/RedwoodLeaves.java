package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;

public class RedwoodLeaves extends LeavesBlock {
    public RedwoodLeaves() {
        super(Properties.from(Blocks.SPRUCE_LEAVES).tickRandomly().sound(SoundType.PLANT));
    }
}

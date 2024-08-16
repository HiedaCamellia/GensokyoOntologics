package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;

public class ShinbokuTrapdoor extends TrapDoorBlock {
    public ShinbokuTrapdoor() {
        super(Properties.from(Blocks.OAK_TRAPDOOR).sound(SoundType.WOOD));
    }
}

package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.*;

public class SakuraTrapDoor extends TrapDoorBlock {
    public SakuraTrapDoor() {
        super(Properties.from(Blocks.OAK_TRAPDOOR).sound(SoundType.WOOD));
    }
}

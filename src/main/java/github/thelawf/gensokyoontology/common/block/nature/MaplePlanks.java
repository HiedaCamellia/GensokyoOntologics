package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public class MaplePlanks extends Block {
    public MaplePlanks() {
        super(Properties.from(Blocks.OAK_PLANKS).sound(SoundType.WOOD));
    }
}

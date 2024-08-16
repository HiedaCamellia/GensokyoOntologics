package github.thelawf.gensokyoontology.common.block.decoration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SorceryCircleBlock extends Block {
    public SorceryCircleBlock() {
        super(Properties.from(Blocks.NETHERITE_BLOCK).setLightLevel((state) -> 12));
    }
}

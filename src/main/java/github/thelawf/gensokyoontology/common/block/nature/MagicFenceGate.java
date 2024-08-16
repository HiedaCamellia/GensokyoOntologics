package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;

public class MagicFenceGate extends FenceGateBlock {
    public MagicFenceGate() {
        super(Properties.from(Blocks.OAK_FENCE_GATE).sound(SoundType.WOOD));
    }
}

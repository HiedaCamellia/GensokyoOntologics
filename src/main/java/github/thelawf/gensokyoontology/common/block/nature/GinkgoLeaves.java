package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.material.Material;

public class GinkgoLeaves extends LeavesBlock {
    public GinkgoLeaves() {
        super(Properties.from(Blocks.DARK_OAK_LEAVES).tickRandomly().sound(SoundType.PLANT));
    }
}

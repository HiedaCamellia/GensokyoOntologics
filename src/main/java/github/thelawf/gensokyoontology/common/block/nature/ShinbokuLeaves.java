package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.material.Material;

public class ShinbokuLeaves extends LeavesBlock {
    public ShinbokuLeaves() {
        super(Properties.create(Material.LEAVES).tickRandomly().sound(SoundType.PLANT));
    }
}

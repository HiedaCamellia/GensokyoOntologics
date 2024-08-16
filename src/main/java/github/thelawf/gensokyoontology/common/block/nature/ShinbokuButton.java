package github.thelawf.gensokyoontology.common.block.nature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoodButtonBlock;

public class ShinbokuButton extends WoodButtonBlock {
    public ShinbokuButton() {
        super(Properties.from(Blocks.OAK_BUTTON).sound(SoundType.WOOD));
    }
}

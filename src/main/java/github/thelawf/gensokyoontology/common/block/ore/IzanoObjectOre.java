package github.thelawf.gensokyoontology.common.block.ore;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class IzanoObjectOre extends OreBlock {
    public IzanoObjectOre() {
        super(Properties.from(Blocks.GOLD_ORE).setRequiresTool()
                .hardnessAndResistance(5.0f, 5.0f).harvestLevel(3)
                .sound(SoundType.STONE));
    }

    @Override
    protected int getExperience(@NotNull Random rand) {
        return MathHelper.nextInt(rand, 3, 7);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.ILevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}

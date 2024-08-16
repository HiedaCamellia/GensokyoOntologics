package github.thelawf.gensokyoontology.common.block.nature;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.data.world.GSKOLevelSavedData;
import github.thelawf.gensokyoontology.data.world.GensokyoSeason;
import net.minecraft.world.level.block.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SakuraLeaves extends LeavesBlock {
    public static final BooleanProperty BLOOMED = BooleanProperty.create("bloomed");
    public SakuraLeaves() {
        super(Properties.from(Blocks.ACACIA_LEAVES).tickRandomly().sound(SoundType.PLANT));
        //this.setDefaultState(this.getDefaultState().with(BLOOMED, true));
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel worldIn, @NotNull BlockPos pos, @NotNull Random random) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
            spawnDrops(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
        GSKOLevelSavedData gskoLevelData = GSKOLevelSavedData.getInstance(worldIn);
        if (gskoLevelData.getSeason() == GensokyoSeason.SPRING) {
            this.setDefaultState(this.getDefaultState().with(BLOOMED, true));
        }
        else {
            this.setDefaultState(this.getDefaultState().with(BLOOMED, false));
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BLOOMED);
    }
}

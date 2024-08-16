package github.thelawf.gensokyoontology.common.world.surface;

import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class UntroddenValleySurface {
    public static final BlockState DEFOLIATION = BlockRegistry.DEFOLIATION_DIRT.get().getDefaultState();
    public static final SurfaceBuilderConfig DEFOLIATION_CONFIG = new SurfaceBuilderConfig(DEFOLIATION, Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());

}

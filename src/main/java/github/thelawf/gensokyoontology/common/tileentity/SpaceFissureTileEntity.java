package github.thelawf.gensokyoontology.common.tileentity;

import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SpaceFissureTileEntity extends BlockEntity implements ITickableTileEntity {

    public final int lifeSpan = 200;
    protected long elapsedTime = 0L;

    public SpaceFissureTileEntity() {
        super(TileEntityRegistry.SPACE_FISSURE_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (this.world != null) {
            this.elapsedTime++;
            if (this.elapsedTime >= lifeSpan) {
                this.remove();
            }
        }
    }
}

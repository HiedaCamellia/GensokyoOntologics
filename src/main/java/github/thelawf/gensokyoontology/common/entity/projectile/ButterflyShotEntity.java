package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ButterflyShotEntity extends AbstractDanmakuEntity {
    protected ButterflyShotEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public ItemStack getItem() {
        return null;
    }
}

package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.entity.monster.YoukaiEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.SpellData;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class InYoJadeDanmakuEntity extends AbstractDanmakuEntity {

    public InYoJadeDanmakuEntity(Level worldIn, Entity shooter) {
        super(EntityRegistry.INYO_JADE_DANMAKU.get(), worldIn);
        this.setShooter(shooter);
    }

    public InYoJadeDanmakuEntity(EntityType<? extends ThrowableEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void onEntityHit(@NotNull EntityRayTraceResult result) {
        if (result.getEntity() instanceof YoukaiEntity) {
            YoukaiEntity youkai = (YoukaiEntity) result.getEntity();
            youkai.attackEntityFrom(DamageSource.WITHER, 5F);
        }
        super.onEntityHit(result);
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.INYO_JADE_RED.get());
    }
}

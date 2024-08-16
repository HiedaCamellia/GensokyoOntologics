package github.thelawf.gensokyoontology.common.entity.misc;

import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.entity.monster.FairyEntity;
import github.thelawf.gensokyoontology.common.entity.monster.YoukaiEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.ScriptedDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.GSKODamageSource;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.EntityPredicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class DreamSealEntity extends ScriptedDanmakuEntity implements IRayTraceReader {
    private int color;
    public DreamSealEntity(Level worldIn, LivingEntity shooter, DanmakuColor color) {
        super(EntityRegistry.DREAM_SEAL_ENTITY.get(), worldIn);
        this.setLifespan(300);

        this.setColor(color);
        this.setShooter(shooter);
        this.setScript(new CompoundTag());
        this.setTarget(findTarget(world, shooter.getPositionVec(), createCubeBox(shooter.getPositionVec(), 20)));
    }
    public DreamSealEntity(EntityType<? extends AbstractDanmakuEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    // /tp -19 70.0 -164
    @Override
    public void tick() {
        super.tick();
        // if (ticksExisted <= 20) return;
        if (this.getShooter() != null && this.getTarget() != null) {
            Vec3 aimedVec = this.getAimedVec(this.getShooter(), this.getTarget());
            float offset = 0.3f / this.getTarget().getEyeHeight();
            this.shoot(aimedVec.x, aimedVec.y - offset, aimedVec.z, 1.6f, 0f);
            GSKOUtil.log(this.getClass(), this.getTarget().getPositionVec());
        }
        // this.getShooter().flatMap(this::getTarget).ifPresent(target -> {
        //     Vec3 direction = this.getAimedVec(target);
        //     this.setMotion(direction.normalize().scale(0.8f));
        // });
    }

    private boolean isPlayer() {
        AtomicBoolean flag = new AtomicBoolean();
        if (this.getShooter() == null) return false;
        return this.getShooter() instanceof Player;
    }

    public static LivingEntity findTarget(Level world, Vec3 center, AxisAlignedBB box) {
        EntityPredicate predicate = new EntityPredicate().setCustomPredicate(entity -> !(entity instanceof Player));
        return world.getClosestEntity(LivingEntity.class, predicate, null,
                center.x, center.y, center.z, box);
    }

    @Override
    protected void onEntityHit(@NotNull EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (result.getEntity() instanceof AbstractDanmakuEntity) {
            AbstractDanmakuEntity danmaku = (AbstractDanmakuEntity) result.getEntity();
            danmaku.remove();
        }
        if (result.getEntity() instanceof YoukaiEntity) {
            YoukaiEntity youkai = (YoukaiEntity) result.getEntity();
            float value = youkai.getMaxHealth() < 400 ? youkai.getMaxHealth() * 0.1f : youkai.getMaxHealth() * 0.075f;
            youkai.attackEntityFrom(GSKODamageSource.HAKUREI_POWER, value);
        }
        if (result.getEntity() instanceof FairyEntity) {
            FairyEntity fairy = (FairyEntity) result.getEntity();
            fairy.attackEntityFrom(GSKODamageSource.HAKUREI_POWER, fairy.getMaxHealth());
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

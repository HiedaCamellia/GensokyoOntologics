package github.thelawf.gensokyoontology.common.entity.projectile;
/*
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.capability.world.ImperishableNightCapability;
import github.thelawf.gensokyoontology.common.util.GSKODamageSource;
import github.thelawf.gensokyoontology.core.register.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class EirinYagokoroArrowEntity extends AbstractArrowEntity {

    private static final int TICKS_TO_CAUSE_INCIDENT = 500;
    public static final DataParameter<Integer> DATA_CAUSING_TICKS = EntityDataManager.createKey(
            EirinYagokoroArrowEntity.class, DataSerializers.VARINT);

    protected EirinYagokoroArrowEntity(EntityType<? extends AbstractArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    // 在这里注册将会引发永夜异变的时长
    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public void writeAdditional(@NotNull CompoundTag compound) {
        super.writeAdditional(compound);
    }

    @Override
    public void readAdditional(@NotNull CompoundTag compound) {
        super.readAdditional(compound);
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getShooter() == null)
            return;

        if (!this.world.isRemote && isTickSuccess(this.world.getDayTime())) {
            ServerLevel serverLevel = (ServerLevel) this.world;
            this.getShooter().sendMessage(Component.literal("你发动了永夜异变"), this.getShooter().getUniqueID());
            LazyOptional<ImperishableNightCapability> capability = serverLevel.getCapability(GSKOCapabilities.IMPERISHABLE_NIGHT);
            capability.ifPresent(cap -> {
                cap.setTriggered(true);
                cap.setDayTime(18000);
                serverLevel.setDayTime(cap.getDayTime());
                serverLevel.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(cap.isTriggered(), serverLevel.getServer());
            });
        }
    }

    private boolean isTickSuccess(long time) {
        double moonAngle = Math.PI / 12000 * (this.world.getDayTime() - 12000);
        Vec3 lookVec = this.getLookVec();
        return time > 12000 && this.ticksExisted >= TICKS_TO_CAUSE_INCIDENT &&
                isWithinRange(lookVec.y, moonAngle - Math.PI / 10, moonAngle + Math.PI / 10) &&
                isWithinRange(lookVec.z, -0.1, 0.1);
    }

    private boolean isWithinRange(double f, double rangeMin, double rangeMax) {
        return f > rangeMin && f < rangeMax;
    }

    @Override
    public void setDamage(double damageIn) {
        super.setDamage(8);
    }

    // 在这里添加新的伤害来源，使得在虚假之月的事件之下，被该箭矢杀死的生物将掉落月之碎片。
    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        LivingEntity entityHit = (LivingEntity) result.getEntity();
        if (!(entityHit instanceof Player)) {
            entityHit.attackEntityFrom(GSKODamageSource.IMPERISHABLE_NIGHT, (float) this.getDamage());
            this.remove();
        }
    }
}

 */

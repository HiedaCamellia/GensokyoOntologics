package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class ScriptedDanmakuEntity extends AbstractDanmakuEntity{
    protected CompoundTag scriptsNBT = new CompoundTag();
    protected Entity target;
    public static final DataParameter<CompoundTag> DATA_SCRIPT = EntityDataManager.createKey(
            ScriptedDanmakuEntity.class, DataSerializers.COMPOUND_NBT);
    protected ScriptedDanmakuEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public ScriptedDanmakuEntity(EntityType<? extends ThrowableEntity> type, LivingEntity throwerIn, Level worldIn, DanmakuType danmakuType, CompoundTag scriptIn) {
        super(type, throwerIn, worldIn, danmakuType, getColorFrom(scriptIn));
        this.setDanmakuColor(getColorFrom(this.scriptsNBT));
        this.setScript(scriptIn);
        this.setColor(DanmakuColor.values()[scriptIn.getInt("color")]);
    }

    public static DanmakuColor getColorFrom(CompoundTag scriptsNBT) {
        if (scriptsNBT != null && scriptsNBT.contains("color")) {
            return DanmakuColor.values()[scriptsNBT.getInt("color")];
        }
        return DanmakuColor.NONE;
    }

    protected ListNBT wrapAsList(INBT inbt) {
        if (inbt instanceof ListNBT) {
            return (ListNBT) inbt;
        }
        return new ListNBT();
    }

    protected CompoundTag wrapAsCompound(INBT inbt) {
        if (inbt instanceof CompoundTag) {
            return  (CompoundTag) inbt;
        }
        return new CompoundTag();
    }

    protected List<Double> wrapAsDoubleFromList(ListNBT listNBT) {
        List<Double> list = new ArrayList<>();
        listNBT.forEach(inbt -> {
            if (inbt instanceof DoubleNBT) list.add(((DoubleNBT) inbt).getDouble());
        });
        return list;
    }

    protected ListNBT getBehaviors(CompoundTag script) {
        if (script.getString("type").equals("keyTickBehavior")) {
            return wrapAsList(script.get("behaviors"));
        }
        return new ListNBT();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_SCRIPT, this.scriptsNBT);
    }

    @Override
    protected void readAdditional(@NotNull CompoundTag compound) {
        super.readAdditional(compound);
        this.scriptsNBT = compound.getCompound("script");
        this.dataManager.set(DATA_SCRIPT, this.scriptsNBT);
        this.setColor(DanmakuColor.values()[compound.getInt("color")]);

    }

    @Override
    protected void writeAdditional(@NotNull CompoundTag compound) {
        super.writeAdditional(compound);
        compound.put("script", this.scriptsNBT);
        compound.putInt("color", this.scriptsNBT.getInt("color"));
    }

    @Override
    public void tick() {
        super.tick();
        this.onScriptTick();
    }

    public void setScript(CompoundTag scriptsNBT) {
        this.scriptsNBT = scriptsNBT;
        this.dataManager.set(DATA_SCRIPT, scriptsNBT);
    }
    public CompoundTag getScript() {
        return this.dataManager.get(DATA_SCRIPT);
    }

    public Entity getTarget() {
        if (!this.world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) this.world;
            return serverLevel.getEntityByUuid(this.scriptsNBT.getUniqueId("target"));
        }
        return null;
    }

    public static Entity getTargetFrom(Level world, CompoundTag scriptsNBT) {
        if (!world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) world;
            return serverLevel.getEntityByUuid(scriptsNBT.getUniqueId("target"));
        }
        return null;
    }

    public void setTarget(@Nullable Entity target) {
        this.target = target;
        if (target != null) {
            this.getScript().putUniqueId("target", target.getUniqueID());
            this.setScript(this.getScript());
        }
    }

    public void onScriptTick() {
        ListNBT list2 = getBehaviors(this.scriptsNBT);
        for (INBT inbt2 : list2) {
            CompoundTag behavior = wrapAsCompound(inbt2);
            if (behavior.contains("shoot") && behavior.get("shoot") instanceof ListNBT) {
                List<Double> paramList = wrapAsDoubleFromList((ListNBT) behavior.get("shoot"));
                int keyTick = behavior.getInt("keyTick");
                if (paramList.size() != 4) return;
                if (this.ticksExisted == keyTick) this.shoot(paramList.get(0), paramList.get(1), paramList.get(2), paramList.get(3).floatValue(), 0f);
            }
            if (behavior.contains("setMotion") && behavior.get("setMotion") instanceof ListNBT) {
                List<Double> paramList = wrapAsDoubleFromList((ListNBT) behavior.get("setMotion"));
                int keyTick = behavior.getInt("keyTick");
                if (paramList.size() != 3) return;
                Vec3 motion = new Vec3(paramList.get(0), paramList.get(1), paramList.get(2));
                if (this.ticksExisted == keyTick) this.setMotion(motion.normalize());
            }
        }
    }
}

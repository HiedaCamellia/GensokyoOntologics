package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuPool;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

// TODO: 按照符卡的强度决定其登场和获取顺序
@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public abstract class SpellCardEntity extends Entity implements IRendersAsItem {

    protected int lifeSpan = 500;
    protected UUID owner;
    private int ownerId;

    /**
     * 初始化设置弹幕的射击方位为X轴正方向，即游戏中的东方
     */
    protected Vec3 shootAngle = new Vec3(Vector3f.XP);

    public static final DataParameter<Integer> DATA_LIFESPAN = EntityDataManager.createKey(
            SpellCardEntity.class, DataSerializers.VARINT);

    public static final DataParameter<Optional<UUID>> DATA_OWNER_UUID = EntityDataManager.createKey(
            SpellCardEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public SpellCardEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn, LivingEntity living) {
        this(entityTypeIn, worldIn);
        // this.setPosition(living.getPosX(), living.getPosY(), living.getPosZ());
        this.setLocationAndAngles(living.getPosX(), living.getPosY(), living.getPosZ(), living.rotationYaw, living.rotationPitch);
        this.setOwner(living);
    }

    public SpellCardEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn, Player player) {
        this(entityTypeIn, worldIn);
        // this.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
        this.setLocationAndAngles(player.getPosX(),player.getPosY(), player.getPosZ(), player.rotationYaw, player.rotationPitch);
        this.setOwner(worldIn.getPlayerByUuid(player.getUniqueID()));
    }

    public SpellCardEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(DATA_LIFESPAN, this.lifeSpan);
        this.dataManager.register(DATA_OWNER_UUID, Optional.empty());
    }

    protected void readAdditional(@NotNull CompoundTag compound) {
        if (compound.contains("Lifespan")) {
            this.lifeSpan = compound.getInt("Lifespan");
        }

        UUID uuid = null;
        if (compound.hasUniqueId("Owner")) {
            uuid = compound.getUniqueId("Owner");
        }

        if (uuid != null) {
            setOwnerId(uuid);
        }
    }

    protected void writeAdditional(CompoundTag compound) {
        compound.putInt("Lifespan", this.lifeSpan);
        if (this.owner != null) {
            compound.putUniqueId("Owner", this.owner);
        }
    }

    public boolean hasOwner() {
        return this.dataManager.get(DATA_OWNER_UUID).isPresent();
    }

    public void setOwner(@Nullable Entity entityIn) {
        if (entityIn != null) {
            this.owner = entityIn.getUniqueID();
            this.setOwnerId(this.owner);
        }
    }

    private void setOwnerId(UUID uuid) {
        this.dataManager.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Nullable
    public Entity getOwner() {
        if (this.world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) this.world;
            Optional<UUID> optionalUUID = this.getDataManager().get(DATA_OWNER_UUID);
            if (optionalUUID.isPresent()) {
                return serverLevel.getEntityByUuid(optionalUUID.get());
            }
        }
        return null;
    }

    @Nullable
    public UUID getOwnerId() {
        return this.dataManager.get(DATA_OWNER_UUID).orElse(null);
    }

    public int getLifeSpan() {
        return lifeSpan;
    }


    /**
     * 使用tick()方法让弹幕在每个游戏刻执行不同的操作，将实体类中的 tickExisted 参数作为变换函数 increment 增加值的迭代单位，
     * 因为这个参数的存在，实体类的tick方法比单纯继承了 ITickable 接口的类更加便捷好用。
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.dataManager.get(DATA_OWNER_UUID).isPresent()) {
            this.remove();
        }

        if (ticksExisted >= this.lifeSpan) {
            this.remove();
        }
        // onTick(world, getOwner(), ticksExisted);
    }

    /**
     * 根据符卡实体的生成位置和旋转设置弹幕的初始位置和旋转
     *
     * @param <D>     所有弹幕实体的类型
     * @param danmaku 弹幕形参
     */
    protected <D extends AbstractDanmakuEntity> void initDanmaku(D danmaku) {
        danmaku.setNoGravity(true);
        danmaku.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.rotationYaw, this.rotationPitch);
    }

    /**
     * 根据旋转设置弹幕的旋转
     *
     * @param <D>          所有弹幕实体的类型
     * @param danmaku      弹幕形参
     * @param initPosition 弹幕的初始化位置
     */
    protected <D extends AbstractDanmakuEntity> void setDanmakuInit(D danmaku, Vec3 initPosition) {
        danmaku.setNoGravity(true);
        danmaku.setLocationAndAngles(initPosition.x, initPosition.y, initPosition.z, this.rotationYaw, this.rotationPitch);
    }

    /**
     * 根据传入的初始化位置和旋转设置弹幕的位置和旋转。<br>
     * 注意：传入的旋转角度将决定着弹幕的法向渲染。
     *
     * @param <D>          所有弹幕实体的类型
     * @param danmaku      弹幕形参
     * @param initPosition 弹幕的初始化位置，应该为全局坐标系
     * @param initRotation 弹幕的旋转
     */
    protected <D extends AbstractDanmakuEntity> void setDanmakuInit(D danmaku, Vec3 initPosition, Vec2 initRotation) {
        danmaku.setNoGravity(true);
        danmaku.setLocationAndAngles(initPosition.x, initPosition.y, initPosition.z, initRotation.x, initRotation.y);
    }

    protected <D extends AbstractDanmakuEntity> void setDanmakuInit(D danmaku, Vec3 initPosition, double rotationYaw, double rotationPitch) {
        setDanmakuInit(danmaku, initPosition, new Vec2((float) rotationYaw, (float) rotationPitch));
    }

    protected <D extends AbstractDanmakuEntity> void setDanmakuInit(D danmaku, Vec3 initPosition, float rotationYaw, float rotationPitch, boolean noGravity) {
        danmaku.setNoGravity(noGravity);
        setDanmakuInit(danmaku, initPosition, new Vec2(rotationYaw, rotationPitch));
    }

    protected <D extends AbstractDanmakuEntity> void setDanmakuInit(D danmaku, Vec3 initPosition, Vec2 initRotation, boolean noGravity) {
        danmaku.setNoGravity(noGravity);
        setDanmakuInit(danmaku, initPosition,initRotation);
    }
    @Override
    public void baseTick() {
        super.baseTick();
        // onTick(world, this.getOwner(), ticksExisted);
    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void onTick(Level world, Entity entity, int ticksIn) {
    }

    public void onScriptTick(Level world, Entity owner, int ticksIn){

    }

    /**
     * 这是个怪方法，请不要理睬（）
     *
     * @param <D>          弹幕实体的具体类
     * @param danmaku      弹幕的提供器，在这里初始化弹幕
     * @param danmakuClass 需要初始化的弹幕的类
     * @param count        弹幕对象池的大小
     * @return 弹幕对象池
     * @throws IllegalAccessException 非法访问
     */
    protected <D extends AbstractDanmakuEntity> List<D> newDanmakuList(Supplier<D> danmaku, Class<D> danmakuClass, int count) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<D> danmakuPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            danmakuPool.add(newDanmaku(danmaku.get(), danmakuClass));
        }
        return danmakuPool;
    }

    protected <D extends AbstractDanmakuEntity> Map<Integer, D> newDanmakuMap (D danmaku, Class<D> danmakuClass, int count) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<Integer, D> danmakuMap = new HashMap<>();
        for (int i = 0; i < count; i++) {
            Constructor<D> constructor = danmakuClass.getDeclaredConstructor(LivingEntity.class, Level.class, DanmakuType.class, DanmakuColor.class);
            D instance = constructor.newInstance(danmaku.getShooter(), danmaku.world,
                    danmaku.getDanmakuType(), danmaku.getDanmakuColor());
            danmakuMap.put(instance.getEntityId(), instance);
        }
        return danmakuMap;
    }

    protected <D extends AbstractDanmakuEntity> D newDanmaku (D danmaku, Class<D> danmakuClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<D> constructor = danmakuClass.getDeclaredConstructor(LivingEntity.class, Level.class, DanmakuType.class, DanmakuColor.class);
        return constructor.newInstance(danmaku.getShooter(), danmaku.world,
                danmaku.getDanmakuType(), danmaku.getDanmakuColor());
    }

    protected <D extends AbstractDanmakuEntity> DanmakuPool<D> newDanmakuPool(D danmaku, Class<D> danmakuClass, int count) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DanmakuPool<D> danmakuPool = new DanmakuPool<>();
        for (int i = 0; i < count; i++) {
            Constructor<D> constructor = danmakuClass.getDeclaredConstructor(LivingEntity.class, Level.class, DanmakuType.class, DanmakuColor.class);
            danmakuPool.add(constructor.newInstance(danmaku.getShooter(), danmaku.world,
                    danmaku.getDanmakuType(), danmaku.getDanmakuColor()));
        }
        return danmakuPool;
    }

    public <D extends AbstractDanmakuEntity> D acquire(Deque<D> deque, Vec3 positionVec, Vec2 rotationVec) {
        D entity = deque.pollFirst();
        if (entity != null) {
            entity.setNoGravity(true);
            entity.setLocationAndAngles(positionVec.x, positionVec.y, positionVec.z, rotationVec.x, rotationVec.y);
            return entity;
        } else {
            return null;
        }
    }

    protected CompoundTag initColor(DanmakuColor colorIn) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("color", colorIn.ordinal());
        return nbt;
    }

    protected Vec2 lookVecToDegrees(Vec3 vector3d) {
        float f1 = (float)Math.acos(vector3d.y);
        float f2 = (float)Math.atan2(vector3d.z, vector3d.x);
        return new Vec2(f1, f2);
    }

    @Override
    @NotNull
    public abstract ItemStack getItem();
}


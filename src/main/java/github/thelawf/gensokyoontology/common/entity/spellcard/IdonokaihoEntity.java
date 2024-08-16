package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.HeartShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.nbt.BehaviorFunctions;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * 恋恋的符卡：本我的解放
 */
public class IdonokaihoEntity extends SpellCardEntity {

    public IdonokaihoEntity(Level worldIn, LivingEntity player){
        super(EntityRegistry.IDO_NO_KAIHO_ENTITY.get(), worldIn, player);
    }

    public IdonokaihoEntity(EntityType<IdonokaihoEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SC_IDO_NO_KAIHO.get());
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 start = new Vec3(1,0,0);

        // TODO: 现在可以通过继承脚本弹幕的方式设置其运动
        if (ticksExisted % 3 != 0) return;
        for (int i = 0; i < 6; i++) {
            CompoundTag nbtClockWise = new CompoundTag();
            CompoundTag nbtCounterClockWise = new CompoundTag();
            nbtClockWise.putInt("color", DanmakuColor.PINK.ordinal());
            nbtCounterClockWise.putInt("color", DanmakuColor.PINK.ordinal());

            Vec3 clockwise = start.rotateYaw((float) Math.PI * 2 / 6 * i);
            Vec3 counterClockwise = start.rotateYaw((float) -Math.PI * 2/ 6 * i);
            clockwise = clockwise.rotateYaw((float) Math.PI / 180 * ticksExisted);
            counterClockwise = counterClockwise.rotateYaw((float) -Math.PI / 180 * ticksExisted);

            applyFunc(new Vec3(-0.2, 0, 0), nbtClockWise);
            applyFunc(new Vec3(0.2, 0, 0), nbtCounterClockWise);

            HeartShotEntity heartClockwise = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbtClockWise);
            HeartShotEntity heartCounterClockwise = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbtCounterClockWise);
            setDanmakuInit(heartClockwise, this.getPositionVec(), new Vec2(this.rotationYaw, this.rotationPitch));
            setDanmakuInit(heartCounterClockwise, this.getPositionVec(), new Vec2(this.rotationYaw, this.rotationPitch));

            heartClockwise.shoot(clockwise.x, clockwise.y, clockwise.z, 0.4f, 0f);
            heartCounterClockwise.shoot(counterClockwise.x, counterClockwise.y, counterClockwise.z, 0.4f, 0f);
            world.addEntity(heartClockwise);
            world.addEntity(heartCounterClockwise);
        }
    }

    private void applyFunc(Vec3 motion, CompoundTag script) {
        ListNBT list = new ListNBT();
        CompoundTag behavior = new CompoundTag();
        motion = motion.rotateYaw((float) Math.PI / 2).scale(0.1);

        ListNBT params = newDoubleNBTList(motion.x, motion.y, motion.z);
        behavior.put(BehaviorFunctions.ADD_MOTION, params);
        list.add(behavior);

        script.putString("type", "keyTickBehavior");
        script.put("behaviors", list);
    }
}


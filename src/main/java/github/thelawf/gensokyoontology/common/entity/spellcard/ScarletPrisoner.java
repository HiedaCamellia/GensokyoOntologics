package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.misc.LaserSourceEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.LargeShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScarletPrisoner extends SpellCardEntity {
    public ScarletPrisoner(Level worldIn, LivingEntity living) {
        super(EntityRegistry.SCARLET_PRISONER_ENTITY.get(), worldIn, living);
    }

    public ScarletPrisoner(EntityType<ScarletPrisoner> type, Level world) {
        super(EntityRegistry.SCARLET_PRISONER_ENTITY.get(), world);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 center = new Vec3(Vector3f.XP);
        Vec3 nextPos = center.scale(3).rotateYaw((float) Math.PI * 2 / 20 * ticksExisted);
        nextPos = this.getPositionVec().add(nextPos);
        float yaw = -360 + (float) 360 / 20 * ticksExisted;
        Vec2 emitVec = new Vec2(GSKOMathUtil.clampPeriod(yaw, -360, 360), -75);

        if (ticksExisted <= 20 && !world.isRemote()) {
            ServerLevel serverLevel = (ServerLevel) world;
            LaserSourceEntity laser = new LaserSourceEntity(world, this.getOwner());
            laser.init(450, 30, 40);
            laser.setLocationAndAngles(nextPos.x, nextPos.y, nextPos.z, emitVec.x, emitVec.y);
            boolean flag = serverLevel.getEntities()
                    .filter(entity -> entity.getType() == EntityRegistry.LASER_SOURCE_ENTITY.get())
                    .count() <= 21;
            if (flag) world.addEntity(laser);
        }

        if (ticksExisted % 20 == 0) {

            // List<Vec3> pos1 = DanmakuUtil.ellipticPos(new Vec2(0,0), 1, 20);
            List<Vec3> coordinates = DanmakuUtil.spheroidPos(1, 20);

            // for (int i = 0; i < pos1.size(); i++) {
            //     for (int j = 0; j < pos1.size(); j++) {
            //         Vec3 vector3d = pos1.get(j).rotatePitch((float) Math.PI * 2 / pos1.size() * j);
            //         pos1.set(j, vector3d);
            //     }
            //     coordinates.addAll(pos1);
            // }

            coordinates.forEach(vector3d -> {
                LargeShotEntity largeShot = new LargeShotEntity((LivingEntity) this.getOwner(), world, DanmakuType.LARGE_SHOT, DanmakuColor.RED);
                setDanmakuInit(largeShot, this.getPositionVec().add(vector3d.x, 15, vector3d.y));
                largeShot.shoot(vector3d.x, vector3d.y, vector3d.z, 0.6f, 0f);
                world.addEntity(largeShot);
            });
        }

    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

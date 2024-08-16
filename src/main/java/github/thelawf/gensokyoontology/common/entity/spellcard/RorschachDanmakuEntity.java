package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.CircleShotEntity;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// 7799399517919454945
public class RorschachDanmakuEntity extends SpellCardEntity{

    public RorschachDanmakuEntity(Level worldIn, LivingEntity living) {
        super(EntityRegistry.RORSCHACH_DANMAKU_ENTITY.get(), worldIn, living);
        this.lifeSpan = 1000;
        this.dataManager.set(DATA_LIFESPAN, 1000);
    }

    public RorschachDanmakuEntity(EntityType<RorschachDanmakuEntity> type, Level world) {
        super(type, world);
        this.lifeSpan = 1000;
        this.dataManager.set(DATA_LIFESPAN, 1000);
    }

    @Override
    public void tick() {
        super.tick();
        Vec2 vector2f = Vec2.ZERO;
        Vec3 initPos = this.getPositionVec();
        for (int i = 0; i < 4; i++) {
            List<Vec3> bluePos1 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
            List<Vec3> bluePos2 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
            List<Vec3> greenPos1 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
            List<Vec3> greenPos2 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
            List<Vec3> magentaPos1 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
            List<Vec3> magentaPos2 = DanmakuUtil.ellipticPos(vector2f, 3 * i, 60);
//
            List<Vec3> blueShootPos1 = deepCopy(bluePos1);
            List<Vec3> blueShootPos2 = deepCopy(bluePos2);
            List<Vec3> greenShootPos1 = deepCopy(greenPos1);
            List<Vec3> greenShootPos2 = deepCopy(greenPos2);
            List<Vec3> magentaShootPos1 = deepCopy(magentaPos1);
            List<Vec3> magentaShootPos2 = deepCopy(magentaPos2);
//
            bluePos1.replaceAll(vector3d -> initPos.add(vector3d.rotatePitch((float) Math.PI / 4)));
            bluePos2.replaceAll(vector3d -> initPos.add(vector3d.rotatePitch((float) -Math.PI / 4)));
            greenPos1.replaceAll(vector3d -> initPos.add(vector3d.rotatePitch((float) Math.PI / 10)));
            greenPos2.replaceAll(vector3d -> initPos.add(vector3d.rotatePitch((float) -Math.PI / 10)));
            magentaPos2.replaceAll(vector3d -> initPos.add(vector3d.rotatePitch((float) Math.PI / 2)));
//
            float blueAngle1 = (float) (i % 2 == 0 ? Math.PI / 4 : -Math.PI / 4);
            float blueAngle2 = (float) (i % 2 == 0 ? -Math.PI / 4 : Math.PI / 4 );
            float greenAngle1 = (float) (i % 2 == 0 ? Math.PI / 10 : -Math.PI / 10);
            float greenAngle2 = (float) (i % 2 == 0 ? -Math.PI / 10 : Math.PI / 10);
            float magentaAngle1 = (float) (i % 2 == 0 ? Math.PI : -Math.PI / 2);
            float magentaAngle2 = (float) (i % 2 == 0 ? Math.PI / 2  : Math.PI);
//
            blueShootPos1.replaceAll(vector3d -> vector3d.rotatePitch(blueAngle1));
            blueShootPos2.replaceAll(vector3d -> vector3d.rotatePitch(blueAngle2));
            greenShootPos1.replaceAll(vector3d -> vector3d.rotatePitch(greenAngle1));
            greenShootPos2.replaceAll(vector3d -> vector3d.rotatePitch(greenAngle2));
            magentaShootPos1.replaceAll(vector3d -> vector3d.rotatePitch(magentaAngle1));
            magentaShootPos2.replaceAll(vector3d -> vector3d.rotatePitch(magentaAngle2));
//
            CompoundTag scriptBlue = new CompoundTag();
            scriptBlue.putInt("color", DanmakuColor.BLUE.ordinal());
            CompoundTag scriptGreen = new CompoundTag();
            scriptGreen.putInt("color", DanmakuColor.GREEN.ordinal());
            CompoundTag scriptMagenta = new CompoundTag();
            scriptMagenta.putInt("color", DanmakuColor.MAGENTA.ordinal());
//
            CompoundTag scriptBlueCounter = new CompoundTag();
            scriptBlue.putInt("color", DanmakuColor.BLUE.ordinal());
            CompoundTag scriptGreenCounter = new CompoundTag();
            scriptGreen.putInt("color", DanmakuColor.GREEN.ordinal());
            CompoundTag scriptMagentaCounter = new CompoundTag();
            scriptGreen.putInt("color", DanmakuColor.MAGENTA.ordinal());
//
            // GSKOUtil.log(this.getClass(), ticksExisted % (bluePos1.size()));
            // GSKOUtil.log(this.getClass(), bluePos1.get(ticksExisted % (bluePos1.size())));
            int interval = 2;
//
            if (ticksExisted % interval == 0) {
                int index = (ticksExisted / interval) % bluePos1.size();
//
                applyScript(bluePos1.get(index), blueShootPos1.get(index), scriptBlue);
                applyScript(bluePos2.get(index), blueShootPos2.get(index), scriptBlueCounter);
                applyScript(greenPos1.get(index), greenShootPos1.get(index), scriptGreen);
                applyScript(greenPos2.get(index), greenShootPos2.get(index), scriptGreenCounter);
                applyScript(magentaPos1.get(index), magentaShootPos1.get(index), scriptMagenta);
                applyScript(magentaPos2.get(index), magentaShootPos2.get(index), scriptMagentaCounter);
            }
        }
    }

    private List<Vec3> deepCopy(List<Vec3> prevVec) {
        List<Vec3> list = new ArrayList<>();
        prevVec.forEach(vec -> list.add(new Vec3(vec.x, vec.y, vec.z)));
        return list;
    }

    private void applyScript(CompoundTag script, Vec3 initPos, String funcName, List<Object> params) {
        ListNBT list = new ListNBT();
//
        CompoundTag behavior = new CompoundTag();
        behavior.putInt("keyTick", 100);
        behavior.put(funcName, (INBT) params);
//
        list.add(behavior);
        script.put("behaviors", list);
        script.putString("type", "keyTickBehavior");
        CircleShotEntity danmaku = new CircleShotEntity((LivingEntity) this.getOwner(), world, script);
        setDanmakuInit(danmaku, initPos);
        world.addEntity(danmaku);
    }

    private void applyScript(Vec3 initPos, Vec3 vector3d, CompoundTag script) {
        ListNBT list = new ListNBT();
        ListNBT motion = new ListNBT();

        motion.add(DoubleNBT.valueOf(vector3d.x));
        motion.add(DoubleNBT.valueOf(vector3d.y));
        motion.add(DoubleNBT.valueOf(vector3d.z));

        CompoundTag behavior = new CompoundTag();
        behavior.putInt("keyTick", 100);
        behavior.put("setMotion", motion);

        list.add(behavior);
        script.put("behaviors", list);
        script.putString("type", "keyTickBehavior");
        CircleShotEntity danmaku = new CircleShotEntity((LivingEntity) this.getOwner(), world, script);
        setDanmakuInit(danmaku, initPos);
        world.addEntity(danmaku);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

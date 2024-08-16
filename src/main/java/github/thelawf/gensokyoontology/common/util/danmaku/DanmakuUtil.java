package github.thelawf.gensokyoontology.common.util.danmaku;

import com.google.common.collect.Lists;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.SpellCardRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class DanmakuUtil {


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
    public static <D extends AbstractDanmakuEntity> List<D> newDanmakuPool(Supplier<D> danmaku, Class<D> danmakuClass, int count) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<D> danmakuPool = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Constructor<D> constructor = danmakuClass.getDeclaredConstructor(EntityType.class, Level.class, Entity.class, DanmakuType.class, DanmakuColor.class);
            danmakuPool.add(constructor.newInstance(danmaku.get().getType(), danmaku.get().world,
                    danmaku.get().getShooter(), danmaku.get().getDanmakuType(), danmaku.get().getDanmakuColor()));
        }
        return danmakuPool;
    }

    public static <D extends AbstractDanmakuEntity> AbstractDanmakuEntity newDanmaku(D danmaku, Class<D> danmakuClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<D> constructor = danmakuClass.getDeclaredConstructor(EntityType.class, Level.class, Entity.class, DanmakuType.class, DanmakuColor.class);

        return constructor.newInstance(danmaku.getType(), danmaku.world, danmaku.getShooter(),
                danmaku.getDanmakuType(), danmaku.getDanmakuColor());
    }

    public static <D extends AbstractDanmakuEntity> void shootDanmaku(@NotNull Level worldIn, Player playerIn,
                                                                      D danmakuEntityType, float velocity, float inaccuracy) {
        Vec3 lookVec = playerIn.getLookVec();

        danmakuEntityType.setNoGravity(true);
        danmakuEntityType.setLocationAndAngles(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight(), playerIn.getPosZ(),
                (float) lookVec.y, (float) lookVec.z);
        danmakuEntityType.shoot(lookVec.x, lookVec.y, lookVec.z, velocity, inaccuracy);
        worldIn.addEntity(danmakuEntityType);

    }

    public static <D extends AbstractDanmakuEntity> void shootDanmaku(@NotNull Level worldIn, Entity entityIn,
                                                                      D danmakuEntityType, Vec3 shootVec,
                                                                      float velocity, float inaccuracy) {
        Vec3 lookVec = entityIn.getLookVec();

        danmakuEntityType.setNoGravity(true);
        danmakuEntityType.setLocationAndAngles(entityIn.getPosX(), entityIn.getPosY() + entityIn.getEyeHeight(), entityIn.getPosZ(),
                (float) lookVec.y, (float) lookVec.z);
        danmakuEntityType.shoot(shootVec.x, shootVec.y, shootVec.z, velocity, inaccuracy);
        worldIn.addEntity(danmakuEntityType);

    }

    public static <D extends AbstractDanmakuEntity> void initDanmaku(D danmaku, Vec3 globalPos, Vec2 rotation, boolean noGravity) {
        danmaku.setNoGravity(noGravity);
        danmaku.setLocationAndAngles(globalPos.getX(), globalPos.getY(), globalPos.getZ(),
                rotation.x, rotation.y);
    }
    public static <D extends AbstractDanmakuEntity> void initDanmaku(D danmaku, Vec3 globalPos, boolean noGravity) {
        danmaku.setNoGravity(noGravity);
        danmaku.setLocationAndAngles(globalPos.getX(), globalPos.getY(), globalPos.getZ(),
                Vec2.ZERO.x, Vec2.ZERO.y);
    }



    public static void applyOperation(ArrayList<VectorOperations> operations, TransformFunction function, Vec3 prevVec) {
        // operations.forEach(operation -> getTransform(operation, function, prevVec));
    }

    public static Vec3 getTransform(VectorOperations operation, TransformFunction function, Vec3 prevVec) {
        if (function.scaling > 0F) {
            if (operation == VectorOperations.ROTATE_YAW) {
                return prevVec.rotateYaw(function.scaling);
            } else if (operation == VectorOperations.ROTATE_ROLL) {
                return prevVec.rotateRoll(function.scaling);
            } else if (operation == VectorOperations.ROTATE_PITCH) {
                return prevVec.rotatePitch(function.scaling);
            } else if (operation == VectorOperations.VECTOR_SCALE) {
                return prevVec.scale(function.scaling);
            }
        } else if (function.acceleration != null) {
            if (operation == VectorOperations.VECTOR_ADD) {
                return prevVec.add(function.acceleration);
            } else if (operation == VectorOperations.VECTOR_SUBTRACT) {
                return prevVec.subtract(function.acceleration);
            }
        } else if (operation == VectorOperations.ARCHIMEDE_SPIRAL) {
            return getArchimedeSpiral(prevVec, 1.0f, Math.PI);
        }

        return prevVec;
    }

    public static Vec3 getArchimedeSpiral(Vec3 prevVec, double radius, double angle) {
        return new Vec3(prevVec.x * radius * Math.cos(angle),
                prevVec.y, prevVec.z * radius * Math.signum(angle));
    }

    public static List<Item> getAllDanmakuItem() {
        List<Item> danmakuItems = new ArrayList<>();
        danmakuItems.add(ItemRegistry.LARGE_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_PURPLE.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_BLUE.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_GREEN.get());
        danmakuItems.add(ItemRegistry.LARGE_SHOT_YELLOW.get());

        danmakuItems.add(ItemRegistry.SMALL_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_BLUE.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_GREEN.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_PURPLE.get());
        danmakuItems.add(ItemRegistry.SMALL_SHOT_YELLOW.get());

        danmakuItems.add(ItemRegistry.HEART_SHOT_RED.get());
        danmakuItems.add(ItemRegistry.HEART_SHOT_PINK.get());
        danmakuItems.add(ItemRegistry.HEART_SHOT_AQUA.get());

        return danmakuItems;
    }

    public static Vec3 rotateRandomAngle(Vec3 preVec, float yawBounds, float pitchBounds) {
        Vec3 nextVec = preVec.rotateYaw(GSKOMathUtil.randomRange(0f, yawBounds));
        nextVec = nextVec.rotatePitch(GSKOMathUtil.randomRange(0f, pitchBounds));
        return nextVec;
    }

    public static Vec3 getRandomPos(Vec3 center, Vector3f radius) {
        double x = GSKOMathUtil.randomRange(-radius.getX(), radius.getX());
        double y = GSKOMathUtil.randomRange(-radius.getY(), radius.getY());
        double z = GSKOMathUtil.randomRange(-radius.getZ(), radius.getZ());

        return new Vec3(center.x + x, center.y + y, center.z + z);
    }

    public static Vec3 getRandomPosWithin(float radius, Plane planeIn) {
        return getRandomPosWithin(new Vector3f(radius, radius, radius), planeIn);
    }

    public static Vec3 getRandomPosWithin(Vector3f radius, Plane planeIn) {
        double x = GSKOMathUtil.randomRange(-radius.getX(), radius.getX());
        double y = GSKOMathUtil.randomRange(-radius.getY(), radius.getY());
        double z = GSKOMathUtil.randomRange(-radius.getZ(), radius.getZ());

        Vec3 vector3d = Vec3.ZERO;

        switch (planeIn) {
            case XY:
                vector3d = new Vec3(x, y, 0);
                break;
            case XZ:
                vector3d = new Vec3(x, 0, z);
                break;
            case YZ:
                vector3d = new Vec3(0, y, z);
                break;
            case XYZ:
                vector3d = new Vec3(x, y, z);
                break;
        }
        return vector3d;
    }

    public static Vec3 getAimingShootVec(LivingEntity thrower, LivingEntity target) {
        float offset = (float) (0.3f / target.getYOffset());
        return new Vec3(target.getPosX() - thrower.getPosX(), target.getPosY() - thrower.getPosY() - offset, target.getPosZ() - thrower.getPosZ());
    }

    public static <D extends AbstractDanmakuEntity> void shootWithRoseLine(D danmaku, Plane planeIn, Vec3 offsetRotation,
                                                                           double radius, double count, double size, int density) {
        // List<Vec3> roseLinePos = getRoseLinePos(radius, count, size, density);
        // List<Vec2> shootVectors = new ArrayList<>();
        // roseLinePos.forEach(vector3d -> shootVectors.add(GSKOMathUtil.getEulerAngle(new Vec3(Vector3f.ZP), vector3d)));
    }

    /**
     * 按照玫瑰线来初始化弹幕的位置和旋转
     *
     * @param count 玫瑰线花瓣/叶片的数量
     * @param size  玫瑰线花瓣的大小
     * @param delta 决定着玫瑰线上的弹幕之间的间隔
     */
    public static List<Vec3> getRoseLinePos(double radius, double count, double size, double delta) {
        double x, y;
        List<Vec3> positions = new ArrayList<>();

        count = count / size;

        for (double i = 0; i < 4 * Math.PI; i += delta) {

            double r = Math.sin(count * i);
            x = r * Math.cos(i) * radius;
            y = r * Math.sin(i) * radius;

            positions.add(new Vec3((float) x, (float) y, 0));

        }
        return positions;
    }

    public static List<Vec3> getHeartLinePos(float radius, double delta) {
        double t = 0;
        double maxT = 2 * Math.PI;
        List<Vec3> positions = new ArrayList<>();

        for (int i = 0; i < Math.ceil(maxT / delta); i++) {
            float x = (float) (16 * GSKOMathUtil.pow3(Math.sin(t)));
            float y = (float) (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t));
            t += delta;
            positions.add(new Vec3(x * radius, y * radius, 0));
        }
        return positions;
    }

    public static List<Vec3> getStarLinePos(float radius, double t, Plane planeIn) {
        List<Vec3> positions = new ArrayList<>();

        for (int i = 0; i < Math.PI * 2; i += t) {
            double x = radius * GSKOMathUtil.pow3(Math.cos(t));
            double y = radius * GSKOMathUtil.pow3(Math.sin(t));

            switch (planeIn) {
                case XY:
                    positions.add(new Vec3(x, y, 0));
                    break;
                default:
                case XZ:
                    positions.add(new Vec3(x, 0, y));
                    break;
                case YZ:
                    positions.add(new Vec3(0, y, x));
                    break;
            }

        }
        return positions;
    }

    /**
     * 这里的旋转角度是弧度制
     *
     * @param prevPositions 之前的弹幕图案的坐标列表
     * @param yaw           对每一个坐标执行的 yaw 旋转角度
     * @param pitch         对每一个坐标执行的 pitch 旋转角度
     */
    public static List<Vec3> getRotatedPos(List<Vec3> prevPositions, float yaw, float pitch) {
        List<Vec3> newPos = new ArrayList<>();
        for (Vec3 prevPos : prevPositions) {
            newPos.add(prevPos.rotatePitch(pitch).rotateYaw(yaw));
        }
        return newPos;
    }

    public static List<Vec3> getParaboloidPos(Vec2 range, double a, double b, double delta) {
        List<Vec3> positions = new ArrayList<>();
        for (int i = 0; i < range.x; i += delta) {
            for (int j = 0; j < range.y; j += delta) {
                double z = GSKOMathUtil.pow2(i) / GSKOMathUtil.pow2(a) -
                        GSKOMathUtil.pow2(j) / GSKOMathUtil.pow2(b);
                positions.add(new Vec3(i, j, z));
            }
        }
        return positions;
    }

    public static List<Vec3> getEllipticParaboloidPos(Vec2 start, Vec2 end, double a, double b, double delta) {
        List<Vec3> positions = new ArrayList<>();
        return positions;
    }

    public static List<Vec3> spheroidPos(double radius, int count) {
        List<Vec3> coordinates = new ArrayList<>();
        List<Vec3> pos1 = ellipticPos(new Vec2(0,0), radius, count);

        for (int i = 0; i < pos1.size(); i++) {
            for (int j = 0; j < pos1.size(); j++) {
                Vec3 vector3d = pos1.get(j).rotatePitch((float) Math.PI * 2 / pos1.size() * j);
                pos1.set(j, vector3d);
            }
            coordinates.addAll(pos1);
        }

        return coordinates;
    }

    public static List<Vec3> ellipticPos(Vec2 center, double radius, int count) {
        ArrayList<Vec3> coordinates = new ArrayList<>();

        // 定义生成坐标的数量
        // 计算每个点的角度间隔
        double angleInterval = 2 * Math.PI / count;

        // 生成坐标
        for (int i = 0; i < count; i++) {
            double angle = i * angleInterval;
            double x = center.x + radius * Math.cos(angle);
            double y = center.y + radius * Math.sin(angle);
            coordinates.add(new Vec3(x, 0, y));
        }

        return coordinates;
    }

    public static Vec3 getAimedVec(LivingEntity shooter, LivingEntity target) {
        return target.getPositionVec().subtract(shooter.getPositionVec());
        // return new Vec3(target.getPosX() - shooter.getPosX(), target.getPosY() - shooter.getPosY(), target.getPosZ() - shooter.getPosZ());
    }

    public static List<DanmakuColor> getRainbowColoredDanmaku() {

        return Lists.newArrayList(
                DanmakuColor.RED,
                DanmakuColor.ORANGE,
                DanmakuColor.YELLOW,
                DanmakuColor.GREEN,
                DanmakuColor.AQUA,
                DanmakuColor.BLUE,
                DanmakuColor.PURPLE,
                DanmakuColor.MAGENTA);
    }

    public enum Plane {
        XZ,
        XY,
        YZ,
        XYZ;
    }

}

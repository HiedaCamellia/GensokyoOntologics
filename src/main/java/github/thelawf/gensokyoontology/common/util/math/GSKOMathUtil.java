package github.thelawf.gensokyoontology.common.util.math;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import github.thelawf.gensokyoontology.common.util.math.function.CosineFunc;
import github.thelawf.gensokyoontology.common.util.math.function.SineFunc;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GSKOMathUtil {

    private GSKOMathUtil() {
    }

    /**
     * 数值周期约束算法，比一般的约束算法多了一个判断周期的条件。周期的判定如下：<br>
     * 当数值小于下限时，即number
     * %
     * max
     * -
     * min
     * ==
     * 0同时number / max <= 1时，返回number本身，否则，先获取数值除以上限之后的整数部分，表示数值是上限的多少倍，然后用上限乘以这个倍率，最后用数值与这个结果相减，得出数值在下限和上限约束的周期内的对应值。
     */
    public static double clampPeriod(double number, double min, double max) {
        return number % max - min == 0 && number / max <= 1 ? number :
                number - max * Math.floor(number / max);
    }

    public static float clampPeriod(float number, float min, float max) {
        return number % max - min == 0 && number / max <= 1 ? number :
                (float) (number - max * Math.floor(number / max));
    }

    public static int clampPeriod(int number, int min, int max) {
        return number % max - min == 0 && number / max <= 1 ? number :
                (int) (float) (number - max * Math.floor((double) number / max));
    }

    /**
     * 传入四个双精度浮点数
     *
     * @param x1 第一个点的x坐标
     * @param y1 第一个点的y坐标
     * @param x2 第二个点的x坐标
     * @param y2 第二个点的y坐标
     * @return 上述两点间的距离
     */
    public static double distanceOf2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(square(x1 - x2) + square(y1 - y2));
    }

    public static double distanceOf3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 + z2, 2), 0.5);
    }

    public static ArrayList<Vec3> getCirclePoints2D(Vec3 center,
                                                        double radius, int count) {

        ArrayList<Vec3> coordinates = new ArrayList<>();
        double radians = (Math.PI / 180) * Math.round(360d / count);
        for (int i = 0; i < count; i++) {
            double x = center.getX() + radius * Math.sin(radians * i);
            double y = center.getY() + radius * Math.cos(radians * i);
            Vec3 coordinate = new Vec3(x, y, 0);
            coordinates.add(coordinate);
        }
        return coordinates;
    }

    public static Vec3 getPointOnCircle(Vec3 center,
                                            double radius, double angle) {
        return new Vec3(
                center.getX() + radius * Math.cos(Math.toDegrees(angle)),
                0, center.getZ() + radius * Math.sin(Math.toDegrees(angle)));
    }


    public static Vec3 bezier2(Vec3 start, Vec3 end, Vec3 p, float time) {
        return lerp(time, lerp(time, start, p), lerp(time, p, end));
    }

    /**
     * 用于小于等于90度的水平转弯
     *
     * @param start 起始点
     * @param p     控制点
     * @param end   终点
     * @param time  步长
     * @return 在控制点的作用下，以起止点为基准的下一个点在曲线上的位置
     */
    public static Vec3 bezier2(Vec3 start, Vec3 end, Vec3 p, double time) {
        if (time > 1 || time < 0)
            return null;
        Vec3 v1 = start.scale(pow2(1 - time));
        Vec3 v2 = p.scale(2 * time * (1 - time));
        Vec3 v3 = end.scale(pow2(time));

        return v1.add(v2.add(v3));
    }

    public static Vec3 bezier3(Vec3 p1, Vec3 p2, Vec3 q1, Vec3 q2, float time) {
        if (time > 1 || time < 0)
            return null;
        Vec3 v1 = lerp(time, p1, q1);
        Vec3 v2 = lerp(time, q1, q2);
        Vec3 v3 = lerp(time, q2, p2);
        Vec3 inner1 = lerp(time, v1, v2);
        Vec3 inner2 = lerp(time, v2, v3);
        return lerp(time, inner1, inner2);
    }

    public static Vec3 bezierDerivative(Vec3 p1, Vec3 p2, Vec3 q1, Vec3 q2, float t) {
        return p1.scale(-3 * t * t + 6 * t - 3)
                .add(q1.scale(9 * t * t - 12 * t + 3))
                .add(q2.scale(-9 * t * t + 6 * t))
                .add(p2.scale(3 * t * t));
    }


    public static Vec3 lerp(float progress, Vec3 start, Vec3 end) {
        return start.add(end.subtract(start).scale(progress));
    }

    public static BlockPos lerp(float progress, BlockPos start, BlockPos end) {
        int x = MathHelper.floor(MathHelper.lerp(progress, start.getX(), end.getX()));
        int y = MathHelper.floor(MathHelper.lerp(progress, start.getY(), end.getY()));
        int z = MathHelper.floor(MathHelper.lerp(progress, start.getZ(), end.getZ()));
        return new BlockPos(x, y, z);
    }

    // tickExisted / MAX_TICK => not very smooth
    // tickExisted / MAX_TICK => a value between zero and one
    // partialTicks => a value between zero and one and is called between each tick
    // tick: 0, 0.1, 0.2, 0.3 ... 1, 1.1, 1.2, 1.3 ... 2 ...
    // ∴ (tickEx + partial) / MAX_TICK => a very smooth approach.
    public static float lerpTicks(float partial, int maxTick, int presentTick, float minValue, float maxValue) {
        return MathHelper.lerp((presentTick + partial) / maxTick, minValue, maxValue);
    }

    /**
     * 求三维向量模长的运算
     *
     * @param x 三维向量的x坐标
     * @param y 三维向量的y坐标
     * @param z 三维向量的z坐标
     * @return 返回三维向量的模长，或者立方体体对角线的长度
     */
    public static double toModulus3D(double x, double y, double z) {
        return Math.pow(x * x + y * y + z * z, 0.5);
    }

    public static double toModulus2D(double x, double y) {
        return Math.pow(x * x + y * y, 0.5);
    }

    /**
     * 球坐标转直角坐标，注意计算机图形学中的空间坐标与数学的空间坐标不同，x, z轴为水平轴，而y轴为竖直轴，
     *
     * @param sc 球坐标的三维的向量，成员属性 x 为球坐标半径Radius，y 为球坐标天顶角theta，z为球坐标方位角phi
     * @return 返回空间直角坐标系的三维向量
     */
    public static Vec3 toRectVec(Vec3 sc) {
        return new Vec3(sc.x * Math.sin(sc.y) * Math.cos(sc.z),
                sc.x * Math.sin(sc.y) * Math.sin(sc.z),
                sc.x * Math.cos(sc.y));
    }

    /**
     * 直角坐标转球坐标
     *
     * @param rc 空间直角坐标系的三维向量
     * @return 返回球坐标系的三维向量
     */
    public static Vec3 toSphereVec(Vec3 rc) {
        double r = GSKOMathUtil.toModulus3D(rc.x, rc.y, rc.z);
        return new Vec3(r, Math.acos(rc.z / r), Math.atan(rc.y / rc.x));
    }

    public static Vec3 toSphereVec(double x, double y, double z) {
        return toSphereVec(new Vec3(x, y, z));
    }

    /**
     * 思路是：先将一个圆的所有点的集合传进来，这个集合中存放的点是使用的平面直角坐标系，然后，将这些点转为球坐标系表示，并执行球坐标上的天顶角和方位角旋转。
     *
     * @param circleDots    组成一个圆周的所有点的平面直角坐标
     * @param thetaRotation 天顶角的旋转度数，取值为 0 ~ π
     * @return 旋转之后该圆周的每个新的点的平面直角坐标
     */
    public static List<Vec3> rotateCircle(List<Vec3> circleDots, double thetaRotation, double phiRotation) {
        List<Vec3> nextCircleDots = new ArrayList<>();
        for (Vec3 dotOnCircle : circleDots) {

            Vec3 prevSphereVec = toSphereVec(dotOnCircle);
            Vec3 nextSphereVec = new Vec3(prevSphereVec.x, prevSphereVec.y + thetaRotation, prevSphereVec.z + phiRotation);
            nextCircleDots.add(toRectVec(nextSphereVec));
        }
        return nextCircleDots;
    }

    public static Vec3 rotateCircleDot(Vec3 rectVec, double thetaRotation, double phiRotation) {
        Vec3 sphereVec = toSphereVec(rectVec);
        return new Vec3(sphereVec.x, sphereVec.y + thetaRotation, sphereVec.z + phiRotation);
    }

    public static Vec3 toLocalCoordinate(Vec3 newOriginIn, Vec3 globalIn) {
        return new Vec3(globalIn.getX() - newOriginIn.getX(),
                globalIn.getY() - newOriginIn.getY(),
                globalIn.getZ() - newOriginIn.getZ());

    }


    /**
     * 计算方法：设斜边为r，两条直角边为x和y，斜边与y轴夹角为d，那么——
     * <p>
     * 1. 求出 tan(d) 的值，为一个常量，用a表示，由已知条件可得：tan()函数表示的是对边比邻边，即x比上y，且两条直角边的平方和等于斜边的平方，所以——
     * <p>
     * 2. 设方程① -- x / y = a;
     * <p>
     * 3. 设方程② -- x^2 + y^2 = r^2;
     * <p>
     * 4. 联立方程①②可得：
     * <p>
     * x = a * y;
     * <p>
     * a^2 * y^2 + y^2 = r^2;
     * <p>
     * y^2 * (a^2 + 1) = r^2;
     * <p>
     * ∴ y = √(z^2 / a^2 + 1)
     *
     * @param hypotenuse 斜边长，即空间向量在该平面上的投影线段
     * @param roll       斜边与y轴的夹角
     * @return 返回一个仅有可知的两边组成的平面向量坐标
     */
    public static Vec3 toRollCoordinate(double hypotenuse, double roll) {
        double x = Math.sqrt(Math.pow(hypotenuse, 2) / Math.pow(Math.tan(roll), 2) + 1);
        return new Vec3(x, Math.tan(roll) * x, 0);
    }

    public static Vec3 toYawCoordinate(double hypotenuse, double yaw) {
        double z = Math.sqrt(Math.pow(hypotenuse, 2) / Math.pow(Math.tan(yaw), 2) + 1);
        return new Vec3(Math.tan(yaw) * z, 0, z);
    }

    public static Vec3 toPitchCoordinate(double hypotenuse, double pitch) {
        double y = Math.sqrt(Math.pow(hypotenuse, 2) / Math.pow(Math.tan(pitch), 2) + 1);
        return new Vec3(0, y, Math.tan(pitch) * y);
    }

    public static Vec3 vecCeil(Vec3 vec) {
        return new Vec3(
                Math.ceil(vec.getX()),
                Math.ceil(vec.getY()),
                Math.ceil(vec.getZ()));
    }

    public static Vec3 vecFloor(Vec3 vec) {
        return new Vec3(
                Math.floor(vec.getX()),
                Math.floor(vec.getY()),
                Math.floor(vec.getZ()));
    }

    public static Vec2 getEulerAngle(Vec3 vectorA, Vec3 vectorB) {
        // 计算旋转矩阵的第一行
        double m11 = vectorA.x * vectorB.x + vectorA.y * vectorB.y;
        double m12 = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
        double m13 = vectorA.z * vectorB.x;

        // 计算旋转矩阵的第三行
        double m31 = -vectorA.y;
        double m32 = vectorA.x;
        double m33 = 0;

        // 计算 yaw、pitch 和 roll 欧拉角
        double yaw = Math.atan2(m12, m11);
        double pitch = Math.asin(m31);

        return new Vec2((float) yaw, (float) pitch);
    }

    public static int randomRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static float randomRange(float min, float max) {
        Random random = new Random();
        float randomValue = random.nextFloat();
        return min + (randomValue * (max - min));
    }

    public static double randomRange(double min, double max) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        return min + (randomValue * (max - min));
    }

    public static Vec3 randomVec(double min, double max) {
        return new Vec3(randomRange(min, max), randomRange(min, max), randomRange(min, max));
    }

    /**
     * Random Spherical Range Algorithm, returns a pair of coordinates on the given surface of a sphere.
     */
    public static Pair<Vec3, Vec3> rsr(Vec3 orientation, float yawRange, float pitchRange) {
        double yaw = toYawPitch(orientation).x;
        double pitch = toYawPitch(orientation).y;
        if (yaw < yawRange || yaw >= yawRange) {
            if (pitch > pitchRange) {
                return Pair.of(fromYawPitch(yawRange, pitchRange), fromYawPitch(yawRange, pitchRange).inverse());
            } else if (pitch < pitchRange) {
                return Pair.of(fromYawPitch(yawRange, pitchRange), fromYawPitch(yawRange, pitchRange).inverse());
            } else {
                return Pair.of(fromYawPitch(yawRange, (float) pitch), fromYawPitch(yawRange, (float) pitch).inverse());
            }
        } else {
            if (pitch > pitchRange) {
                return Pair.of(fromYawPitch(yawRange, pitchRange), fromYawPitch(yawRange, pitchRange).inverse());
            } else if (pitch < pitchRange) {
                return Pair.of(fromYawPitch(yawRange, pitchRange), fromYawPitch(yawRange, pitchRange).inverse());
            }
            return Pair.of(orientation, orientation.inverse());
        }
    }

    public static <V> V rollByWeight(int total, int weight, V value) {
        return new Random().nextInt(total) < weight ? null : value;
    }

    public static Vec2 toYawPitch(Vec3 vector3d) {
        double yaw = Math.atan2(-vector3d.x, vector3d.z);
        double pitch = Math.atan2(vector3d.y, Math.sqrt(vector3d.x * vector3d.x + vector3d.z * vector3d.z));
        return new Vec2((float) toDegree(yaw), (float) toDegree(pitch));
    }

    public static void rotateMatrixToLookVec(MatrixStack matrixStackIn, Vec3 rotationVec) {
        float f5 = (float)Math.acos(rotationVec.y);
        float f6 = (float)Math.atan2(rotationVec.z, rotationVec.x);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(((float)Math.PI / 2 - f6) * (180 / (float)Math.PI)));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f5 * (180 / (float)Math.PI)));
    }

    public static Vec3 fromYawPitch(float yaw, float pitch) {
        return new Vec3(Math.cos(yaw) * Math.cos(pitch), Math.sin(yaw) * Math.sin(pitch), Math.sin(pitch));
    }

    /**
     * 弧度值除以Math.PI的结果为【180度的几分之几】
     *
     * @param radIn 弧度值
     * @return 角度值
     */
    public static double toDegree(double radIn) {
        return radIn / Math.PI * 180d;
    }

    /**
     * Math.PI 除以180度的结果为【每一角度等于多少弧度】
     *
     * @param degIn 角度值
     * @return 弧度值
     */
    public static double toRadian(double degIn) {
        return degIn * Math.PI / 180d;
    }

    public static double pow2(double base) {
        return square(base);
    }

    public static double pow3(double base) {
        return cube(base);
    }

    public static double square(double base) {
        return base * base;
    }

    public static double cube(double base) {
        return base * base * base;
    }

    public static Vec3 rotatePitchYaw(Vec3 prev, float pitch, float yaw) {
        CosineFunc cf = new CosineFunc(prev.x, 1,0);
        SineFunc sf = new SineFunc(1,1,0);
        double x = 0;
        return new Vec3(0,0,0);
    }

    /**
     * Generate this code field by ChatGPT
     *
     * @param world 世界
     * @param block 将要被放置的方块
     * @param start 起始点
     * @param end   终点
     */
    public static void generateBlockOnBezierCurve(Level world, Block block, BlockPos start, BlockPos end) {
        // 获取起点和终点之间的距离
        double dist = start.distanceSq(end);

        // 计算中点和控制点的坐标
        double mx = (start.getX() + end.getX()) / 2.0;
        double my = (start.getY() + end.getY()) / 2.0 + (dist / 16.0);
        double mz = (start.getZ() + end.getZ()) / 2.0;
        double cx = (start.getX() + end.getX() + my - start.getY()) / 2.0;
        double cz = (start.getZ() + end.getZ() + my - start.getY()) / 2.0;

        // 获取贝塞尔曲线上的坐标，并在每个坐标处放置草方块
        int numPoints = (int) Math.sqrt(dist) * 2;
        for (int i = 0; i <= numPoints; i++) {
            double t = (double) i / (double) numPoints;
            double x = Math.pow(1 - t, 2) * start.getX() + 2 * (1 - t) * t * cx + Math.pow(t, 2) * end.getX();
            double y = Math.pow(1 - t, 2) * start.getY() + 2 * (1 - t) * t * my + Math.pow(t, 2) * end.getY();
            double z = Math.pow(1 - t, 2) * start.getZ() + 2 * (1 - t) * t * cz + Math.pow(t, 2) * end.getZ();

            BlockPos pos = new BlockPos(x, y, z);
            if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
                world.setBlockState(pos, block.getDefaultState());
            }
        }
    }

    public static ArrayList<Vec3> getBlocksOnBezierCurve(BlockPos start, BlockPos end) {
        // 获取起点和终点之间的距离
        double dist = start.distanceSq(end);
        ArrayList<Vec3> vecList = new ArrayList<>();

        // 计算中点和控制点的坐标
        double mx = (start.getX() + end.getX()) / 2.0;
        double my = (start.getY() + end.getY()) / 2.0 + (dist / 16.0);
        double mz = (start.getZ() + end.getZ()) / 2.0;
        double cx = (start.getX() + end.getX() + my - start.getY()) / 2.0;
        double cz = (start.getZ() + end.getZ() + my - start.getY()) / 2.0;

        // 获取贝塞尔曲线上的坐标，并在每个坐标处放置草方块
        int numPoints = (int) Math.sqrt(dist) * 2;
        for (int i = 0; i <= numPoints; i++) {
            double t = (double) i / (double) numPoints;
            double x = Math.pow(1 - t, 2) * start.getX() + 2 * (1 - t) * t * cx + Math.pow(t, 2) * end.getX();
            double y = Math.pow(1 - t, 2) * start.getY() + 2 * (1 - t) * t * my + Math.pow(t, 2) * end.getY();
            double z = Math.pow(1 - t, 2) * start.getZ() + 2 * (1 - t) * t * cz + Math.pow(t, 2) * end.getZ();

            vecList.add(new Vec3(x, y, z));
        }
        return vecList;
    }

    public static Quaternion conjugate(Quaternion quaternionIn) {
        return new Quaternion(-quaternionIn.getX(), -quaternionIn.getY(), -quaternionIn.getZ(), quaternionIn.getW());
    }

    public static Quaternion vecToQuaternion(Vec3 vector3d) {
        double yaw = Math.atan2(vector3d.z, vector3d.x);
        double pitch = Math.asin(vector3d.y);

        // 将角度转为四元数
        Quaternion quaternion = new Quaternion(Vector3f.YP, (float) Math.toDegrees(-yaw), true);// 设置水平旋转
        quaternion.multiply(new Quaternion(Vector3f.XP, (float) Math.toDegrees(pitch), true)); // 设置垂直旋转

        return quaternion;
    }

    public static Quaternion vecToQuaternion(Vector3f vector3f) {
        double yaw = Math.atan2(vector3f.getZ(), vector3f.getX());
        double pitch = Math.asin(vector3f.getY());

        // 将角度转为四元数
        Quaternion quaternion = new Quaternion(Vector3f.YP, (float) Math.toDegrees(-yaw), true);// 设置水平旋转
        quaternion.multiply(new Quaternion(Vector3f.XP, (float) Math.toDegrees(pitch), true)); // 设置垂直旋转

        return quaternion;
    }

    public static boolean isBetween(int num, int min, int max) {
        return num >= min && num < max;
    }

    public static boolean isBetween(float num, float min, float max) {
        return num >= min && num < max;
    }

    public static boolean isBetween(double num, double min, double max) {
        return num >= min && num < max;
    }

}

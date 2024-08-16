package github.thelawf.gensokyoontology.common.util.danmaku;

import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.common.util.math.LineSegment;
import github.thelawf.gensokyoontology.common.util.math.LineSegment3D;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public class TransformFunction extends ITransform.AbstractTransform {
    public static final Logger LOGGER = LogManager.getLogger();

    private Level worldIn;

    private Player playerIn;

    // -------------Initialize Rotations and Locations--------------//

    /**
     * 设置向量的枢轴点坐标
     */
    public double pivotX, pivotY, pivotZ = 0.D;

    /**
     * 设置向量的旋转角度，以弧度值为单位
     */
    public double yaw, pitch, roll = 0.D;

    public double x, y, z = 0.D;
    public Vec3 initLocation = new Vec3(0d, 0d, 0d);
    public Vec3 shootVector = new Vec3(0d, 0d, 0d);

    // --------------------Function settings---------------------//

    /**
     * 设定函数在每次释放符卡时被执行的次数
     */
    public int executeTimes = 0;

    /**
     * 函数的生命周期，即每次执行时执行的时长
     */
    public int lifeSpan = 0;

    /**
     * 设置本函数所有执行次数完成后间隔多久执行另外的函数
     */
    public double executeInterval = 0.d;

    /**
     * 设置本函数应用于的发射口发出的弹幕是否瞄准某个实体
     */
    public UUID aimingAt = null;

    public ArrayList<VectorOperations> transformOrders = new ArrayList<>();
    public ArrayList<TransformUnit> transformUnits = new ArrayList<>();

    public VectorOperations vectorOperations;

    // -------------Parameters to Set Future Motions--------------//

    /**
     * 实体的瞬时矢量速度
     */
    public Vec3 speedV3 = new Vec3(0, 0, 0);

    public Vec3 nextShootVec = new Vec3(0, 0, 0);

    /**
     * 实体的瞬时合成速度
     */
    public double resultantSpeed;

    /**
     * 线性增加量，一般用于旋转下一个弹幕的发射角度
     */
    public double increment = 0.D;

    /**
     * 用于将原有的速度向量使用向量数乘法乘以该参数
     */
    public float scaling = 0.F;

    /**
     * 在弹幕实体发tick()方法里面将原有的速度向量使用向量相加的方式加上该速度向量，一般
     * 用于让弹幕在其生成之后的运动轨迹为一条弧线。
     */
    public Vec3 acceleration = new Vec3(0, 0, 0);

    /**
     * 用于将原有的速度向量使用向量相减的方式减去该参数的方式返回新的速度向量
     */
    public Vec3 subtraction = new Vec3(0, 0, 0);

    // ======================== Conditions and Predicate ========================= //


    // -------------Constructors, Builders, and Implements------------- //

    public static class Builder extends TransformFunction {
        public static TransformFunction create() {
            return new TransformFunction();
        }
    }

    public TransformFunction() {
    }

    public TransformFunction(double yaw, double pitch, double roll,
                             double posX, double posY, double posZ) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        this.resultantSpeed = GSKOMathUtil.toModulus3D(posX, posY, posZ);
    }

    public TransformFunction setAimingAt(UUID aimingAt) {
        this.aimingAt = aimingAt;
        return this;
    }

    public TransformFunction setShootVector(Vec3 shootVector) {
        this.shootVector = shootVector;
        return this;
    }

    public TransformFunction setAcceleration(Vec3 acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public TransformFunction setExecuteInterval(double executeInterval) {
        this.executeInterval = executeInterval;
        return this;
    }

    public TransformFunction setExecuteTimes(int executeTimes) {
        this.executeTimes = executeTimes;
        return this;
    }

    public TransformFunction setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
        return this;
    }

    public TransformFunction setLevel(Level worldIn) {
        this.worldIn = worldIn;
        return this;
    }

    public TransformFunction setScaling(float scaling) {
        this.scaling = scaling;
        return this;
    }

    public TransformFunction setYaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    public TransformFunction setRoll(double roll) {
        this.roll = roll;
        return this;
    }

    public TransformFunction setPitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    public TransformFunction setTransformOrders(ArrayList<VectorOperations> transformOrders) {
        this.transformOrders = transformOrders;
        return this;
    }

    public Vec3 increaseYaw(float yawIncrement) {
        float f = MathHelper.cos(yawIncrement);
        float f1 = MathHelper.sin(yawIncrement);
        double d0 = this.x * (double) f + this.z * (double) f1;
        double d1 = this.y;
        double d2 = this.z * (double) f - this.x * (double) f1;
        this.speedV3 = new Vec3(this.speedV3.x + d0, this.speedV3.y, this.speedV3.z + d2);
        return new Vec3(d0, d1, d2);
    }

    public Vec3 increasePitch(float pitchIncrement) {
        float f = MathHelper.cos(pitchIncrement);
        float f1 = MathHelper.sin(pitchIncrement);
        double d0 = this.x;
        double d1 = this.y * (double) f + this.z * (double) f1;
        double d2 = this.z * (double) f - this.y * (double) f1;
        this.speedV3 = new Vec3(this.speedV3.x, this.speedV3.y + d1, this.speedV3.z + d2);
        return new Vec3(d0, d1, d2);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 increaseRoll(float rollIncrement) {
        float f = MathHelper.cos(rollIncrement);
        float f1 = MathHelper.sin(rollIncrement);
        double d0 = this.x * (double) f + this.y * (double) f1;
        double d1 = this.y * (double) f - this.x * (double) f1;
        double d2 = this.z;
        this.speedV3 = new Vec3(this.speedV3.x + d0, this.speedV3.y + d1, this.speedV3.z);
        return new Vec3(d0, d1, d2);
    }


    public TransformFunction setInitLocation(Vec3 initLocation) {
        this.initLocation = initLocation;
        this.x = initLocation.x;
        this.y = initLocation.y;
        this.z = initLocation.z;
        return this;
    }

    public TransformFunction setResultantSpeed(double resultantSpeed) {
        this.resultantSpeed = resultantSpeed;
        return this;
    }

    public TransformFunction setPlayer(Player playerIn) {
        this.playerIn = playerIn;
        return this;
    }


    public TransformFunction setIncrement(double increment) {
        this.increment = increment;
        return this;
    }

    public TransformFunction addTransformUnit(TransformUnit transformUnit) {
        this.transformUnits.add(transformUnit);
        return this;
    }

    public Player getPlayer() {
        return this.playerIn;
    }

    public Level level() {
        return worldIn;
    }

    public int getExecuteTimes() {
        return executeTimes;
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public double getExecuteInterval() {
        return executeInterval;
    }


    public Vec3 getInitLocation() {
        return initLocation;
    }

    public Vec3 getShootVector() {
        return shootVector;
    }

    /**
     * 通过给定的组件坐标和枢轴点坐标旋转该组件
     *
     * @param vecPrev     之前的向量坐标方位
     * @param incrementV3 三维向量增加量。vector内的三个参数分别对应roll，yaw和pitch，单位是角度值
     */
    @Override
    public void rotate(Vec3 vecPrev, Vec3 incrementV3) {
        double incrementRoll = incrementV3.x;
        double incrementYaw = incrementV3.y;
        double incrementPitch = incrementV3.z;
    }

    @Deprecated
    public Vec3 rotate(Vec3 prevPos, Vec3 pivotLocation, Vec3 rotationV3) {
        double radius = GSKOMathUtil.distanceOf3D(prevPos.x, prevPos.y, prevPos.z,
                pivotLocation.x, pivotLocation.y, pivotLocation.z);
        double incrementX = 0;
        double incrementY = 0;
        double incrementZ = 0;

        if (rotationV3.x != 0) {
            LineSegment hypotenuse = new LineSegment3D(pivotLocation.x, pivotLocation.y, 0d, prevPos.x, prevPos.y, 0d);
            Vec3 rc = GSKOMathUtil.toRollCoordinate(hypotenuse.getLength(), rotationV3.x);
            incrementX = rc.x;
            incrementY = rc.y;
        }
        if (rotationV3.y != 0) {
            LineSegment hypotenuse = new LineSegment3D(pivotLocation.x, 0d, pivotLocation.z, prevPos.x, 0d, prevPos.z);
            Vec3 rc = GSKOMathUtil.toYawCoordinate(hypotenuse.getLength(), rotationV3.y);
            incrementX = rc.x;
            ;
            incrementZ = rc.z;
        }
        if (rotationV3.z != 0) {
            LineSegment hypotenuse = new LineSegment3D(0d, pivotLocation.y, pivotLocation.z, 0d, prevPos.y, prevPos.z);
            Vec3 rc = GSKOMathUtil.toPitchCoordinate(hypotenuse.getLength(), rotationV3.z);
            incrementY = rc.y;
            incrementZ = rc.z;
        }
        this.x += incrementX;
        this.y += incrementY;
        this.z += incrementZ;
        return new Vec3(prevPos.x + incrementX, prevPos.y + incrementY, prevPos.z + incrementZ);
    }

    public void transform(double x, double y, double z, double yaw, double pitch, double roll) {

    }

    @Override
    public Vec3 translate(double pivotX, double pivotY, double pivotZ) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;
        return new Vec3(pivotX, pivotY, pivotZ);
    }

    /**
     * 将旋转角度的增加值转为三维向量的速度，并使用如下方法计算三维坐标——
     * <br>方法一，直接解三角形；
     * <br>方法二，使用球坐标和平面直角坐标的转换，θ-方位角 φ-天顶角 r-径向距离（在本例中是弹幕的合成速度）：
     * <br> x = r * sin(θ) * cos(φ)
     * <br> y = r * sin(θ) * sin(φ)
     * <br> z = r * cos(θ)
     *
     * @param incrementYawIn   yaw轴的旋转角度的增加值，即方位角 θ
     * @param incrementPitchIn pitch轴的旋转角度的增加值，即天顶角 φ
     * @return 返回一个三维向量(xSpeed, ySpeed, zSpeed)，注意x和z才是水平方向，而y是垂直方向。
     */
    @Deprecated
    public Vec3 angleToVector(double incrementYawIn, double incrementPitchIn) {
        return new Vec3(this.resultantSpeed * Math.sin(90D - incrementYawIn),
                this.resultantSpeed * Math.sin(incrementPitchIn) / Math.sin(90D - incrementPitchIn),
                this.resultantSpeed * Math.sin(incrementYawIn) / Math.sin(90D));
    }

    /**
     * 将旋转角度的增加值转为三维向量的速度，旋转角度增加值可以用360 / 弹幕总量得到，并使用如下方法计算三维坐标——
     * <br>方法一，直接解三角形；
     * <br>方法二，使用球坐标和平面直角坐标的转换，θ-方位角 φ-天顶角 r-径向距离（在本例中是弹幕的合成速度）：
     * <br> x = r * sin(θ) * cos(φ)
     * <br> y = r * sin(θ) * sin(φ)
     * <br> z = r * cos(θ)
     *
     * @param resultantSpeedIn 弹幕的合成速度
     * @param incrementYawIn   yaw轴每tick的旋转角度，即方位角 θ
     * @param incrementPitchIn pitch轴每tick的旋转角度，即天顶角 φ
     * @return 返回一个三维向量(xSpeed, ySpeed, zSpeed)，注意x和z才是水平方向，而y是垂直方向。
     * @deprecated 现在请使用 {@link GSKOMathUtil} 来将球坐标角度转为直角坐标向量
     */
    @Deprecated
    public Vec3 angleToVector(double resultantSpeedIn, double incrementYawIn, double incrementPitchIn) {
        return new Vec3(resultantSpeedIn * Math.sin(90D - incrementYawIn),
                resultantSpeedIn * Math.sin(incrementPitchIn) / Math.sin(90D - incrementPitchIn),
                resultantSpeedIn * Math.sin(incrementYawIn) / Math.sin(90D));
    }

    @Override
    public Vec3 accelerate(Vec3 acceleration) {
        return this.acceleration = acceleration;
    }

}

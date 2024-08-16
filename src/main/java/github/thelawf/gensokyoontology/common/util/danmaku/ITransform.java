package github.thelawf.gensokyoontology.common.util.danmaku;

import net.minecraft.world.phys.Vec3;

public interface ITransform {

    // rotate()函数接受所有的旋转操作
    void rotate(Vec3 vecPrev, Vec3 rotationVec);

    // transform()函数接受所有的变换操作
    void transform(double x, double y, double z, double yaw, double pitch, double roll);

    // 设置对象旋转的枢轴点坐标
    Vec3 translate(double pivotX, double pivotY, double pivotZ);

    abstract class AbstractTransform implements ITransform {
        public abstract Vec3 accelerate(Vec3 v3dIn);

    }
}

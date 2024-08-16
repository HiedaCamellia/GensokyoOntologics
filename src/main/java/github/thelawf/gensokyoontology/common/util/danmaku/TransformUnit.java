package github.thelawf.gensokyoontology.common.util.danmaku;

import net.minecraft.world.phys.Vec3;

public class TransformUnit {
    private final float yaw, roll, pitch;
    private final float scaling;
    private final Vec3 acceleration, subtraction;

    public TransformUnit(float yaw, float roll, float pitch, float scaling, Vec3 acceleration, Vec3 subtraction) {
        this.yaw = yaw;
        this.roll = roll;
        this.pitch = pitch;
        this.scaling = scaling;
        this.acceleration = acceleration;
        this.subtraction = subtraction;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public float getPitch() {
        return pitch;
    }

    public float getScaling() {
        return scaling;
    }

    public Vec3 getAcceleration() {
        return acceleration;
    }

    public Vec3 getSubtraction() {
        return subtraction;
    }
}

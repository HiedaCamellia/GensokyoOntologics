package github.thelawf.gensokyoontology.common.util.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Vec3NBT extends CompoundTag{
    public static final String TYPE = "vector3d";
    private final StringNBT name;
    private final CompoundTag value = new CompoundTag();
    public final double x;
    public final double y;
    public final double z;

    public Vec3NBT(String name, Vec3 vector3d) {
        this.name = StringNBT.valueOf(name);
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.value.putDouble("x", this.x);
        this.value.putDouble("y", this.y);
        this.value.putDouble("z", this.z);
    }

    public String getName() {
        return this.name.getString();
    }

    public CompoundTag getValue() {
        return this.value;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Vec3 toVec3() {
        return new Vec3(this.x, this.y, this.z);
    }
}

package github.thelawf.gensokyoontology.common.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class GSKOPowerCapability implements INBTSerializable<CompoundTag> {
    private float count;
    private boolean isDirty;
    public static final float MAX = 5.00F;
    public static final float MIN = 0.00F;
    public static GSKOPowerCapability INSTANCE;

    public GSKOPowerCapability(float count) {
        this.count = count;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("power_count", this.count);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.count = nbt.getFloat("power_count");
    }

    public void setCount(float count) {
        this.count = MathHelper.clamp(count, MIN, MAX);
        this.markDirty();
    }

    public float getCount() {
        return this.count;
    }

    public void add(float count) {
        this.count = MathHelper.clamp(this.count + count, MIN, MAX);
        this.markDirty();
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    public static class Storage implements Capability.IStorage<GSKOPowerCapability> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<GSKOPowerCapability> capability, GSKOPowerCapability instance, Direction side) {
            CompoundTag nbt = new CompoundTag();
            nbt.putFloat("power_count", instance.getCount());
            return nbt;
        }

        @Override
        public void readNBT(Capability<GSKOPowerCapability> capability, GSKOPowerCapability instance, Direction side, INBT nbt) {
            CompoundTag compoundNBT = (CompoundTag) nbt;
            instance.setCount(compoundNBT.getFloat("power_count"));
        }
    }
}

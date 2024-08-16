package github.thelawf.gensokyoontology.common.capability.world;

import net.minecraft.nbt.CompoundTag;

public class ImperishableNightCapability implements IImperishableNight {

    private int time;
    private boolean isTriggered;

    public ImperishableNightCapability(int time, boolean isTriggered) {
        this.time = time;
        this.isTriggered = isTriggered;
    }

    @Override
    public void setDayTime(int time) {
        this.time = time;
    }

    @Override
    public int getDayTime() {
        return this.time;
    }

    @Override
    public void setTriggered(boolean triggered) {
        this.isTriggered = triggered;
    }

    @Override
    public boolean isTriggered() {
        return this.isTriggered;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("time", this.time);
        nbt.putBoolean("is_triggered", this.isTriggered);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("time")) {
            this.time = nbt.getInt("time");
        }
        if (nbt.contains("is_triggered")) {
            this.isTriggered = nbt.getBoolean("is_triggered");
        }
    }
}

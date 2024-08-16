package github.thelawf.gensokyoontology.common.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class ActualityCapability implements INBTSerializable<CompoundTag> {
    private float actuality = 20f;
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("actuality", this.actuality);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.actuality = nbt.getFloat("actuality");
    }
}

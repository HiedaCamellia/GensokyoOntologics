package github.thelawf.gensokyoontology.common.capability.entity;

import net.minecraft.command.impl.GameModeCommand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.GameType;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class ExtraLifeCapability implements INBTSerializable<CompoundTag> {
    private int extraLifeCount = 0;

    public int getExtraLifeCount() {
        return extraLifeCount;
    }

    public void setExtraLifeCount(int extraLifeCount) {
        this.extraLifeCount = extraLifeCount;
    }

    public void addExtraLife(int extraLifeCount) {
        this.extraLifeCount += extraLifeCount;
    }

    @Override
    @NotNull
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("extra_life", this.extraLifeCount);
        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag nbt) {
        if (nbt.contains("extra_life")) {
            this.extraLifeCount = nbt.getInt("extra_life");
        }
    }
}

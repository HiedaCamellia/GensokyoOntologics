package github.thelawf.gensokyoontology.common.capability.special;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IInputEntry extends INBTSerializable<CompoundTag> {
    List<InputType> getEntries();
    void addEntry(InputType type);
    void addEntries(InputType... types);
}

package github.thelawf.gensokyoontology.common.util.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConstNBT extends CompoundTag{
    public final StringNBT type;
    private final StringNBT name;
    private final CompoundTag value = new CompoundTag();

    public ConstNBT(String type, String name, INBT valueEntry) {
        this.type = StringNBT.valueOf(type);
        this.name = StringNBT.valueOf(name);
        this.value.put("value", valueEntry);
    }

    @Nullable
    @Override
    public INBT put(@NotNull String key, @NotNull INBT value) {
        return new ConstNBT(this.type.getString(), key, value);
    }

    public CompoundTag getValue() {
        return this.value;
    }
}

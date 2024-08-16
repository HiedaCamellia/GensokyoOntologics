package github.thelawf.gensokyoontology.common.nbt.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.concurrent.atomic.AtomicReference;

public enum ConstPreset {
    NONE(GSKONBTUtil.wrap(() -> {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putString("none", "None");
        return compoundNBT;
    })),
    PI(GSKONBTUtil.wrap(() -> {
        CompoundTag compound = new CompoundTag();
        compound.putDouble("pi", Math.PI);
        return compound;
    })),
    TWO_PI(GSKONBTUtil.wrap(() -> {
        CompoundTag compound = new CompoundTag();
        compound.putDouble("two_pi", Math.PI * 2);
        return compound;
    })),
    E(GSKONBTUtil.wrap(() -> {
        CompoundTag compound = new CompoundTag();
        compound.putDouble("e", Math.E);
        return compound;
    }));

    public final CompoundTag nbt;

    ConstPreset(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public CompoundTag get() {
        return this.nbt;
    }

    public String getKey() {
        AtomicReference<String> str = new AtomicReference<>("");
        this.nbt.keySet().stream().filter(s -> s.equals(this.name().toLowerCase())).findFirst().ifPresent(str::set);
        return str.get();
    }

    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.",".const_builder.button.preset." + getKey());
    }
}

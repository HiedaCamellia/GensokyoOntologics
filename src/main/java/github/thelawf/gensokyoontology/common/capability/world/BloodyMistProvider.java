package github.thelawf.gensokyoontology.common.capability.world;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class BloodyMistProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private BloodyMistCapability capability;
    private List<String> biomeRegistryNames;
    private boolean isTriggered;

    public BloodyMistProvider(List<String> biomeRegistryNames, boolean isTriggered) {
        this.biomeRegistryNames = biomeRegistryNames;
        this.isTriggered = isTriggered;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == GSKOCapabilities.BLOODY_MIST ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @NotNull
    BloodyMistCapability getOrCreateCapability() {
        if (this.capability == null) {
            this.capability = new BloodyMistCapability(this.biomeRegistryNames, this.isTriggered);
        }
        return this.capability;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }

}

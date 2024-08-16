package github.thelawf.gensokyoontology.common.capability.world;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public class BloodyMistCapability implements IIncidentCapability {

    private List<String> biomeRegistryNames;
    private boolean isTriggered;
    private List<Pair<String, Boolean>> biomes;
    public static BloodyMistCapability INSTANCE;

    public BloodyMistCapability(List<String> biomes, boolean isTriggered) {
        this.biomeRegistryNames = biomes;
        this.isTriggered = isTriggered;
    }

    public List<String> getBiomeRegistryNames() {
        return this.biomeRegistryNames;
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
        ListNBT listNBT = new ListNBT();

        for (String registryName : this.biomeRegistryNames) {
            CompoundTag biomeNbt = new CompoundTag();
            biomeNbt.putString("biome", registryName);
            listNBT.add(biomeNbt);
        }
        nbt.putBoolean("is_triggered", this.isTriggered);
        nbt.put("biome_list", listNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        List<String> biomeNames = new ArrayList<>();
        if (nbt.get("biome_list") instanceof ListNBT) {
            ListNBT listNBT = (ListNBT) nbt.get("biome_list");

            if (listNBT == null) return;

            for (INBT inbt : listNBT) {
                if (inbt instanceof CompoundTag) {
                    CompoundTag compound = (CompoundTag) inbt;
                    biomeNames.add(compound.getString("biome"));
                }
            }
        }
        this.biomeRegistryNames = biomeNames;
        this.isTriggered = nbt.getBoolean("is_triggered");
    }
}

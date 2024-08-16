package github.thelawf.gensokyoontology.data.world;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.LevelSavedData;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class GSKOLevelSavedData extends LevelSavedData {
    private static final String NAME = "GSKOLevelSavedData";
    private GensokyoSeason season = GensokyoSeason.SPRING;
    private final Stack<ResourceLocation> incidents = new Stack<>();
    public GSKOLevelSavedData() {
        super(NAME);
    }
    public GSKOLevelSavedData(String name) {
        super(name);
    }

    public static GSKOLevelSavedData getInstance(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel serverLevel = (ServerLevel) worldIn;
        DimensionSavedDataManager storage = serverLevel.getSavedData();
        return storage.getOrCreate(GSKOLevelSavedData::new, NAME);
    }

    @Override
    public void read(CompoundTag nbt) {
        this.season = GensokyoSeason.valueOf(nbt.getString("season"));
        ListNBT listNBT = (ListNBT) nbt.get("incidents");
        if (listNBT != null) {
            for (INBT inbt : listNBT) {
                if (inbt instanceof StringNBT) {
                    StringNBT value = (StringNBT) inbt;
                    this.incidents.push(GensokyoOntology.withRL(value.getString()));
                }
            }
        }
    }

    @Override
    @NotNull
    public CompoundTag write(CompoundTag compound) {
        ListNBT listNBT = new ListNBT();
        incidents.forEach((location) -> {
            StringNBT stringNBT = StringNBT.valueOf(location.toString());
            listNBT.add(stringNBT);
        });
        compound.putString("season", this.season.name());
        compound.put("incidents", listNBT);
        return compound;
    }

    public GensokyoSeason getSeason() {
        return this.season;
    }

    public void setSeason(GensokyoSeason season) {
        this.season = season;
    }

    public Stack<ResourceLocation> getIncidents() {
        return this.incidents;
    }

}

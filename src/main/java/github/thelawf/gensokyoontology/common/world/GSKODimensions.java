package github.thelawf.gensokyoontology.common.world;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.level.Level;

public final class GSKODimensions {
    public static long seed;
    public static final RegistryKey<Level> GENSOKYO = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
            ResourceLocation.parse(GensokyoOntology.MODID, "gensokyo"));
    public static final RegistryKey<DimensionType> GENSOKYO_TYPE = RegistryKey.getOrCreateKey(
            Registry.DIMENSION_TYPE_KEY, ResourceLocation.parse(GensokyoOntology.MODID, "gensokyo"));

    public static final RegistryKey<Level> FORMER_HELL_WORLD = RegistryKey.getOrCreateKey(
            Registry.WORLD_KEY, ResourceLocation.parse(GensokyoOntology.MODID, "former_hell"));
    public static final RegistryKey<DimensionType> FORMER_HELL_TYPE = RegistryKey.getOrCreateKey(
            Registry.DIMENSION_TYPE_KEY, ResourceLocation.parse(GensokyoOntology.MODID, "former_hell"));
}

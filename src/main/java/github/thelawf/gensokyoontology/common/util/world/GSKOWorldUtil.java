package github.thelawf.gensokyoontology.common.util.world;

import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomesProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.Registry;

import net.minecraft.world.level.Level;
import net.minecraft.world.biome.Biome;
import net.minecraft.server.level.ServerLevel;

// 种子：-7023638334721123514
public class GSKOLevelUtil {

    public static RegistryKey<Level> levelDimension(ResourceLocation location) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, location);
    }

    public static boolean isEntityInDimension(Entity entity, RegistryKey<Level> worldKey) {
        return entity.level().dimension().equals(worldKey);
    }

    public static boolean isEntityInBiome(Entity entity, RegistryKey<Biome> biomeRegistry) {
        ResourceLocation rl = entity.level().getBiome(entity.getPosition()).getRegistryName();
        return rl != null && rl.toString().equals(biomeRegistry.getLocation().toString());
    }

    public static boolean isGensokyoBiome(ServerLevel serverLevel, ResourceLocation biomeName) {
        return GSKOBiomesProvider.GSKO_BIOMES.stream().anyMatch(biome -> biome.getRegistryName().equals(biomeName));
    }
}

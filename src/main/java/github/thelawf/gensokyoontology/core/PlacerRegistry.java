package github.thelawf.gensokyoontology.core;

import com.mojang.serialization.Codec;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.world.feature.placer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.LevelGenRegistries;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.event.RegistryEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = GensokyoOntology.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PlacerRegistry {
    public static final List<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = new ArrayList<>();

    public static final TrunkPlacerType<BranchTrunkPlacer> BRANCH_TRUNK_PLACER = registerTrunkPlacer("branch_trunk_placer",
            BranchTrunkPlacer.CODEC);
    public static final TrunkPlacerType<MagicTrunkPlacer> MAGIC_TRUNK_PLACER = registerTrunkPlacer("magic_trunk_placer",
            MagicTrunkPlacer.CODEC);
    public static final FoliagePlacerType<SphericalFoliagePlacer> SPHERICAL_FOLIAGE_PLACER = foliage(
            GensokyoOntology.withRL("spherical_foliage_placer"), SphericalFoliagePlacer.CODEC);

    public static void registerPlacers() {
        Registry<TrunkPlacerType<?>> registry = Registry.TRUNK_REPLACER;

        Registry.register(registry, ResourceLocation.parse(GensokyoOntology.MODID, "redwood_trunk_placer"),
                new TrunkPlacerType<>(RedwoodTrunkPlacer.CODEC));
        Registry.register(registry, ResourceLocation.parse(GensokyoOntology.MODID, "magic_trunk_placer"),
                new TrunkPlacerType<>(MagicTrunkPlacer.CODEC));
        // Registry.register(registry, ResourceLocation.parse(GensokyoOntology.MODID, "branch_trunk_placer"), BRANCH_TRUNK_PLACER);
    }

    public static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(String id, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_REPLACER, GensokyoOntology.withRL(id), new TrunkPlacerType<>(codec));
    }

    public static <P extends FoliagePlacer> FoliagePlacerType<P> foliage(ResourceLocation name, Codec<P> codec) {
        FoliagePlacerType<P> type = new FoliagePlacerType<>(codec);
        type.setRegistryName(name);
        FOLIAGE_PLACER_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    public static void registerFoliagePlacers(RegistryEvent.Register<FoliagePlacerType<?>> evt) {
        evt.getRegistry().registerAll(FOLIAGE_PLACER_TYPES.toArray(new FoliagePlacerType<?>[0]));
    }
}

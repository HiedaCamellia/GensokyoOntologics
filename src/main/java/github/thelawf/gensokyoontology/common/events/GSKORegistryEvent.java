package github.thelawf.gensokyoontology.common.events;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.PlacerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.event.RegistryEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = GensokyoOntology.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GSKORegistryEvent {

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<FoliagePlacerType<?>> event) {
        // PlacerRegistry.registerFoliagePlacers(event);
    }
}

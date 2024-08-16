package github.thelawf.gensokyoontology.data;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = GensokyoOntology.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GSKODataGenerator {

    @SubscribeEvent
    public static void onDataGenerate(GatherDataEvent event) {
        event.getGenerator().addProvider(new LootTableGenerator(event.getGenerator()));
    }
}

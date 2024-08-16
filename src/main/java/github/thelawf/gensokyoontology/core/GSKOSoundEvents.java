package github.thelawf.gensokyoontology.core;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.minecraftforge.fml.DeferredRegister;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GSKOSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, GensokyoOntology.MODID);

    public static final DeferredRegister<SoundEvent> MUSIC_GENSOKYO = register("music_gensokyo");
    public static final DeferredRegister<SoundEvent> CICADA_AMBIENT = register("cicada_ambient");
    public static final DeferredRegister<SoundEvent> BAMBOO_PARTRIDGE = register("bamboo_partridge");

    private static DeferredRegister<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(ResourceLocation.parse(
                GensokyoOntology.MODID, name)));
    }

    public static void registerSound(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

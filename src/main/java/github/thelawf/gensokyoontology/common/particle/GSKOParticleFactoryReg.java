package github.thelawf.gensokyoontology.common.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.world.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GSKOParticleFactoryReg {

    @SubscribeEvent
    public static void onParticleFactoryRegistry(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(GSKOParticleRegistry.SPACE_FISSURE.get(),
                SpaceFissureParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onCommonSetUpEvent(FMLCommonSetupEvent event) {
    }
}



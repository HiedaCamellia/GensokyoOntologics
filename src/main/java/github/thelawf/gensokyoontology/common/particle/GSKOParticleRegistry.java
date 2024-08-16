package github.thelawf.gensokyoontology.common.particle;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GSKOParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(
            BuiltInRegistries.PARTICLE_TYPE, GensokyoOntology.MODID);
    public static final DeferredHolder<ParticleType<?>, SpaceFissureParticleType> SPACE_FISSURE = PARTICLE_TYPES.register(
            "space_fissure", SpaceFissureParticleType::new);
}

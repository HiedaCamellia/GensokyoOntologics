package github.thelawf.gensokyoontology.common.world.surface;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.util.registry.LevelGenRegistries;
import net.minecraft.world.gen.surfacebuilders.*;
import net.minecraftforge.fml.DeferredRegister;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class GSKOSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(
            ForgeRegistries.SURFACE_BUILDERS, GensokyoOntology.MODID);

    public static final DeferredRegister<YatsugaTakeSurface> YATSUGA_TAKE_SURFACE = SURFACE_BUILDERS.register(
            "yatsuga_take", () -> new YatsugaTakeSurface(SurfaceBuilderConfig.CODEC));

}

package github.thelawf.gensokyoontology.core.init;


import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class FluidRegistry {
    public static final ResourceLocation STILL_HOTSPRING_TEX = ResourceLocation.fromNamespaceAndPath(
            GensokyoOntology.MODID, "tileentity/water_still");
    public static final ResourceLocation FLOW_HOTSPRING_TEX = ResourceLocation.fromNamespaceAndPath(
            GensokyoOntology.MODID, "tileentity/water_flow");

    public static final ResourceLocation SAKE_WINE_STILL_TEX = STILL_HOTSPRING_TEX;
    public static final ResourceLocation SAKE_WINE_FLOW_TEX = FLOW_HOTSPRING_TEX;

    public static final ResourceLocation PAPER_PULP_STILL_TEX = STILL_HOTSPRING_TEX;
    public static final ResourceLocation PAPER_PULP_FLOW_TEX = FLOW_HOTSPRING_TEX;

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
            BuiltInRegistries.FLUID, GensokyoOntology.MODID);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> HOT_SPRING_SOURCE = FLUIDS.register(
            "hot_spring_fluid",
            () -> new BaseFlowingFluid.Source(FluidRegistry.HOT_SPRING_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> HOT_SPRING_FLOWING = FLUIDS.register(
            "hot_spring_fluid_flowing",
            () -> new BaseFlowingFluid.Flowing(FluidRegistry.HOT_SPRING_PROPERTIES));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> PAPER_PULP_SOURCE = FLUIDS.register(
            "paper_pulp_fluid",
            () -> new BaseFlowingFluid.Source(FluidRegistry.PAPER_PULP_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> PAPER_PULP_FLOWING = FLUIDS.register(
            "paper_pulp_fluid_flowing",
            () -> new BaseFlowingFluid.Flowing(FluidRegistry.PAPER_PULP_PROPERTIES));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> SAKE_WINE_SOURCE = FLUIDS.register(
            "sake_wine_fluid", () -> new BaseFlowingFluid.Source(FluidRegistry.SAKE_WINE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> SAKE_WINE_FLOWING = FLUIDS.register(
            "sake_wine_fluid_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.SAKE_WINE_PROPERTIES));

    public static final BaseFlowingFluid.Properties HOT_SPRING_PROPERTIES = new BaseFlowingFluid.Properties(
            HOT_SPRING_SOURCE, HOT_SPRING_FLOWING, FluidAttributes.builder(STILL_HOTSPRING_TEX, FLOW_HOTSPRING_TEX)
            .color(0xFF00FFFF)
            .density(5000)
            .viscosity(4000))
            .bucket(ItemRegistry.HOTSPRING_BUCKET)
            .block(BlockRegistry.HOT_SPRING_BLOCK)
            .slopeFindDistance(3).explosionResistance(100F);

    public static final BaseFlowingFluid.Properties SAKE_WINE_PROPERTIES = new BaseFlowingFluid.Properties(
            SAKE_WINE_SOURCE, SAKE_WINE_FLOWING, FluidAttributes.builder(SAKE_WINE_STILL_TEX, SAKE_WINE_FLOW_TEX)
            .color(0xFF8888BB)
            .density(3000)
            .viscosity(3800))
            .bucket(ItemRegistry.SAKE_BUCKET)
            .block(BlockRegistry.SAKE_WINE_BLOCK)
            .slopeFindDistance(3).explosionResistance(100F);

    public static final BaseFlowingFluid.Properties PAPER_PULP_PROPERTIES = new BaseFlowingFluid.Properties(
            PAPER_PULP_SOURCE, PAPER_PULP_FLOWING, FluidAttributes.builder(PAPER_PULP_STILL_TEX, PAPER_PULP_FLOW_TEX)
            .color(0xDDE1C699)
            .density(6000)
            .viscosity(2800))
            .bucket(ItemRegistry.PAPER_PULP_BUCKET)
            .block(BlockRegistry.PAPER_PULP_BLOCK)
            .slopeFindDistance(3).explosionResistance(100F);
}

package github.thelawf.gensokyoontology.client.renderer;

import github.thelawf.gensokyoontology.client.renderer.world.ScarletSkyRenderer;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GSKODimensionRenderInfo extends DimensionRenderInfo {
    public GSKODimensionRenderInfo(float couldHeight, boolean placebo, FogType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(couldHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
    }

    @Override
    @NotNull
    public Vec3 func_230494_a_(@NotNull Vec3 biomeFogColor, float daylight) {
        return biomeFogColor.mul(daylight * 0.94F + 0.06F, (daylight * 0.94F + 0.06F), (daylight * 0.91F + 0.09F));
    }

    @Override
    public boolean func_230493_a_(int p_230493_1_, int p_230493_2_) {
        return false;
    }

    @Nullable
    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
        return new ScarletSkyRenderer();
    }

}

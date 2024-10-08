package github.thelawf.gensokyoontology.client.renderer.entity.creature;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.passive.HumanResidentEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HumanResidentRenderer extends LivingRenderer<HumanResidentEntity, BipedModel<HumanResidentEntity>> {

    public static final ResourceLocation HUMAN_RESIDENT_TEXTURE = ResourceLocation.parse(
            GensokyoOntology.MODID, "textures/entity/human_resident.png");

    public HumanResidentRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new BipedModel<>(1.0f), 0.8f);
    }

    @Override
    protected boolean canRenderName(HumanResidentEntity entity) {
        return super.canRenderName(entity) && entity.hasCustomName();
    }

    @Override
    protected void renderName(HumanResidentEntity entityIn, Component displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

    }

    @Override
    @NotNull
    public ResourceLocation getEntityTexture(@NotNull HumanResidentEntity entity) {
        return HUMAN_RESIDENT_TEXTURE;
    }
}

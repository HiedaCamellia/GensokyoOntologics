package github.thelawf.gensokyoontology.client.renderer.entity.creature;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.model.YukairiModel;
import github.thelawf.gensokyoontology.common.entity.YukariEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.resources.ResourceLocation;

public class YukariRenderer extends LivingRenderer<YukariEntity, YukairiModel> {

    private static final ResourceLocation YUKARI_TEXTURE = ResourceLocation.parse(
            GensokyoOntology.MODID, "textures/entity/yukari_texture.png");

    public YukariRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new YukairiModel(), 0.8f);
    }

    @Override
    public ResourceLocation getEntityTexture(YukariEntity entity) {
        return YUKARI_TEXTURE;
    }
}

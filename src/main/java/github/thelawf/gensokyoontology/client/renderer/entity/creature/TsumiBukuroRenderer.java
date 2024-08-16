package github.thelawf.gensokyoontology.client.renderer.entity.creature;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.client.model.HumanNPCModel;
import github.thelawf.gensokyoontology.common.entity.monster.TsumiBukuroEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TsumiBukuroRenderer extends LivingRenderer<TsumiBukuroEntity, HumanNPCModel<TsumiBukuroEntity>> {

    public static final ResourceLocation TSUMI_BUKURO_TEXTURE = ResourceLocation.parse(
            GensokyoOntology.MODID, "textures/entity/tsumi_bukuro.png");

    public TsumiBukuroRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new HumanNPCModel<>(1.0F), 0.7F);
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull TsumiBukuroEntity entity) {
        return TSUMI_BUKURO_TEXTURE;
    }
}

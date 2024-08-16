package github.thelawf.gensokyoontology.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import github.thelawf.gensokyoontology.client.model.KoishiHatModel;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.client.entity.player.AbstractClientPlayer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.ItemStack;

public class GSKOLayerRenderer extends LayerRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public GSKOLayerRenderer(IEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack helm = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (!helm.isEmpty() && helm.getItem() == ItemRegistry.KOISHI_HAT.get()) {
            matrixStackIn.push();
            getEntityModel().bipedHead.translateRotate(matrixStackIn);
            matrixStackIn.translate(-0.2, -0.15, -0.3);
            matrixStackIn.scale(0.4F, -0.4F, -0.4F);
            //IBakedModel model = ModelLoader
            // IBakedModel model = (IBakedModel) ItemRegistry.KOISHI_HAT.get().getArmorModel(player, helm, EquipmentSlotType.HEAD, this.getEntityModel());

            KoishiHatModel model = new KoishiHatModel(1f);
            IVertexBuilder builder = bufferIn.getBuffer(Atlases.getCutoutBlockType());
            // Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
            //         .renderModelBrightnessColor(matrixStackIn.getLast(), builder, null, model, 1, 1, 1, packedLightIn, OverlayTexture.NO_OVERLAY);
            matrixStackIn.pop();

        }
    }
}

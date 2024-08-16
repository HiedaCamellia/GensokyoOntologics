package github.thelawf.gensokyoontology.client.renderer.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;

import static org.lwjgl.opengl.GL11.*;

@OnlyIn(Dist.CLIENT)
public class ScarletSkyRenderer implements ISkyRenderHandler {
    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientLevel world, Minecraft mc) {
        RenderSystem.clearColor(1.0F, 0.0F, 0.0F, 1.0F);
        RenderSystem.clear(GL_COLOR_BUFFER_BIT, MinecraftForgeClient.getRenderLayer().isColoredOutlineBuffer());
        RenderSystem.matrixMode(GL_PROJECTION);
        RenderSystem.loadIdentity();
        float f = 30.0F;
        RenderSystem.ortho(-f, f, -f, f, 100.0F, 300.0F);
        RenderSystem.matrixMode(GL_MODELVIEW);
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0F, 0.0F, -200.0F);
    }

    public void shouldRender(ServerLevel serverLevel) {

    }


}

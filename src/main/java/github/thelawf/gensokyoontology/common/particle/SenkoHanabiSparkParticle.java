package github.thelawf.gensokyoontology.common.particle;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientLevel;

public class SenkoHanabiSparkParticle extends SpriteTexturedParticle {
    protected SenkoHanabiSparkParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return null;
    }
}

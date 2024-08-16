package github.thelawf.gensokyoontology.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.awt.*;

public class SpaceFissureParticleType extends ParticleType<SpaceFissureParticleData> {

    private static final boolean ALWAYS_SHOW = true;

    @SuppressWarnings("deprecation")
    public SpaceFissureParticleType() {
        super(ALWAYS_SHOW, SpaceFissureParticleData.DESERIALIZER);
    }

    @Override
    @Nonnull
    public Codec<SpaceFissureParticleData> func_230522_e_() {
        return Codec.unit(new SpaceFissureParticleData(new Vec3(0, 0, 0),
                new Color(0), 0));
    }

}

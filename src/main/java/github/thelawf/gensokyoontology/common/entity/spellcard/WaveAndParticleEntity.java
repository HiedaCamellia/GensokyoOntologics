package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * 八云紫的经典符卡：【境符】波与粒的境界
 */
public class WaveAndParticleEntity extends SpellCardEntity {
    public WaveAndParticleEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public WaveAndParticleEntity(Level worldIn, Player player) {
        super(EntityRegistry.WAVE_AND_PARTICLE_ENTITY.get(), worldIn, player);
    }

    @Override
    public void tick() {
        super.tick();

        for (int i = 0; i < 3; i++) {
            RiceShotEntity riceShot = new RiceShotEntity((LivingEntity) this.getOwner(), world, DanmakuType.RICE_SHOT, DanmakuColor.PURPLE);

            Vec3 muzzle = this.getLookVec().rotateYaw((float) Math.PI * 2 / 3 * i);
            Vec3 shootAngle = muzzle.rotateYaw((float) (Math.PI / 180 *
                    ticksExisted * ((float) ticksExisted / 5)));

            setDanmakuInit(riceShot, this.getPositionVec(), shootAngle.x, shootAngle.z);
            riceShot.shoot(shootAngle.getX(), 0, shootAngle.getZ(), 0.6f, 0f);
            world.addEntity(riceShot);

        }

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull ItemStack getItem() {
        return new ItemStack(ItemRegistry.SC_WAVE_AND_PARTICLE.get());
    }
}

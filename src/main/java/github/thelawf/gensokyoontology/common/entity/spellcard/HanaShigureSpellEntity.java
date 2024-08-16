package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * 春符「花时雨」
 */
public class HanaShigureSpellEntity extends SpellCardEntity {
    private Random random = new Random();
    public HanaShigureSpellEntity(Level worldIn, LivingEntity player) {
        super(EntityRegistry.HANA_SHIGURE_ENTITY.get(), worldIn);
        this.setOwner(player);
        this.setLocationAndAngles(player.getPosX(),player.getPosY(), player.getPosZ(), player.rotationYaw, player.rotationPitch);
        this.lifeSpan = 1200;
    }

    public HanaShigureSpellEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(EntityRegistry.HANA_SHIGURE_ENTITY.get(), worldIn);
        this.lifeSpan = 1200;
    }

    @Override
    public void tick() {

        Vec3 start = new Vec3(GSKOMathUtil.randomRange(-1D, 1D),
                GSKOMathUtil.randomRange(-1D,1D), GSKOMathUtil.randomRange(-1D, 1D)).scale(8);
        Vec3 end = start.inverse();
        float f = (float) 1 / 75 * (ticksExisted % 200);
        Vec3 pos = GSKOMathUtil.lerp(MathHelper.clamp(f, 0f, 1f), start, end);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                RiceShotEntity riceShot = new RiceShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.RICE_SHOT, DanmakuColor.PURPLE);
                Vec3 initPos = pos.add(0, 0.05 * i, 0);
                Vec3 shootVec = pos.rotateYaw((float) Math.PI * 2 / 60 * j);

                setDanmakuInit(riceShot, this.getPositionVec().add(initPos), GSKOMathUtil.toYawPitch(shootVec), false);
                riceShot.shoot(shootVec.x, shootVec.y, shootVec.z, 0.5f, 0f);
                riceShot.setNoGravity(false);
                LogManager.getLogger().info(riceShot.hasNoGravity());
                world.addEntity(riceShot);
            }
        }

        super.tick();
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SPELL_CARD_BLANK.get());
    }
}

package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FullCherryBlossomEntity extends SpellCardEntity {

    public FullCherryBlossomEntity(Level worldIn, LivingEntity living) {
        super(EntityRegistry.FULL_CHERRY_BLOSSOM_ENTITY.get(), worldIn, living);
        this.setOwner(living);
    }

    public FullCherryBlossomEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    /**
     * 这个方法是为了方便其它类在外部调用符卡的弹幕演出
     */
    public void onTick(Level world, LivingEntity living, int ticksExisted) {
        List<Vec3> roseLinePos = DanmakuUtil.getRoseLinePos(1.2, 3, 2, 0.05);

        if (ticksExisted % 20 == 0) {
            for (Vec3 vector3d : roseLinePos) {
                RiceShotEntity riceShot = new RiceShotEntity(living, world, DanmakuType.RICE_SHOT, DanmakuColor.PURPLE);
                Vec3 shootVec = new Vec3(vector3d.x, vector3d.y, vector3d.z);
                shootVec = DanmakuUtil.rotateRandomAngle(shootVec, (float) Math.PI * 2, (float) Math.PI * 2);
                vector3d = vector3d.add(DanmakuUtil.getRandomPosWithin(3.5f, DanmakuUtil.Plane.XYZ));
                vector3d = vector3d.add(this.getPositionVec());

                DanmakuUtil.initDanmaku(riceShot, vector3d, new Vec2((float) vector3d.x, (float) vector3d.y), true);
                riceShot.shoot(shootVec.x, shootVec.y, shootVec.z, 0.3f, 0f);
                world.addEntity(riceShot);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        onTick(world, (LivingEntity) this.getOwner(), ticksExisted);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

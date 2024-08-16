package github.thelawf.gensokyoontology.common.entity.spellcard;

import com.mojang.blaze3d.matrix.MatrixStack;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MobiusRingEntity extends SpellCardEntity {

    public MobiusRingEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MobiusRingEntity(Level worldIn, Player player) {
        super(EntityRegistry.MOBIUS_RING_WORLD_ENTITY.get(), worldIn, player);
    }

    @Override
    public void tick() {

        // 创建圆环的水平面：
        // 定义一个位于 X-Z 平面的 PQ 向量，以P为圆心形成圆P
        Vec3 horizonVec = new Vec3(Vector3f.ZP);
        horizonVec = horizonVec.scale(6);

        // 创建竖圆：
        Vec3 verticalVec = new Vec3(Vector3f.ZP);
        verticalVec = verticalVec.scale(3);

        float velocity = 0.2f;
        float rotation = (float) (Math.PI / 80 * 2 * ticksExisted);

        horizonVec = horizonVec.rotateYaw(rotation);
        List<DanmakuColor> colors = DanmakuUtil.getRainbowColoredDanmaku();

        for (int i = 0; i < colors.size(); i++) {
            SmallShotEntity smallShot = new SmallShotEntity((LivingEntity) this.getOwner(), this.world,
                    DanmakuType.SMALL_SHOT, colors.get(i));

            /*
             * 这里的操作是不是意味着：
             * 设verticalVec 为 V，俯仰角为θ，偏航角为φ
             * 俯仰角旋转操作等同于 V(x, y * cos(θ) + z * sin(θ), z * cos(θ) - y * sin(θ))
             * 偏航角旋转操作等同于 V(x * cos(φ) + z * sin(φ), y, z1 * cos(φ) - x * sin(φ))
             * 整体的旋转操作等同于 V(x * cos(φ) + z * sin(φ), y, (z * cos(θ) - y * sin(θ) * cos(φ)) - x * sin(φ))
             */
            verticalVec = verticalVec.rotatePitch((float) Math.PI / colors.size() * i);
            verticalVec = verticalVec.rotateYaw((float) Math.PI / 80 * 2 * ticksExisted);

            setDanmakuInit(smallShot, horizonVec.add(this.getPositionVec()));
            smallShot.shoot((float) verticalVec.getX(), (float) verticalVec.getY(), (float) verticalVec.getZ(), velocity, 0f);

            world.addEntity(smallShot);
        }

        int lifeSpan = 1000;
        if (ticksExisted >= lifeSpan) {
            this.remove();
        }
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SC_MOBIUS_RING_WORLD.get());
    }

}

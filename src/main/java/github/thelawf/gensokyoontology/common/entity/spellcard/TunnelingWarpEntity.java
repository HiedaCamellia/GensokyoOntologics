package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.monster.FairyEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallStarShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 跃迁「超时空隧道折跃」
 */
public class TunnelingWarpEntity extends SpellCardEntity {
    public TunnelingWarpEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn, Player player) {
        super(entityTypeIn, worldIn, player);
    }

    public TunnelingWarpEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        LivingEntity shooter = (LivingEntity) this.getOwner();
        if (shooter == null) return;

        Vec3 starInitPos = new Vec3(Vector3f.XP).scale(1.8);
        Vec3 starRadialShootVec = new Vec3(Vector3f.ZP).scale(3);

        Vec3 tunnelPos = new Vec3(Vector3f.XP).scale(3.4);

        List<DanmakuColor> colors = DanmakuUtil.getRainbowColoredDanmaku();

        if (ticksExisted % 3 == 0) {
            for (int i = 0; i < colors.size(); i++) {
                SmallShotEntity smallShot = new SmallShotEntity(shooter, world, DanmakuType.SMALL_SHOT, colors.get(i));
                tunnelPos = tunnelPos.rotateYaw((float) Math.PI / colors.size() * i);

                setDanmakuInit(smallShot, tunnelPos.add(this.getPositionVec()),
                        (float) tunnelPos.x, (float) tunnelPos.z, false);

                world.addEntity(smallShot);
            }
        }
        for (int i = 0; i < 3; i++) {
            SmallStarShotEntity smallStar = new SmallStarShotEntity(shooter, world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE);

            setDanmakuInit(smallStar, starInitPos.add(new Vec3(0, shooter.getPosY() + 15, 0)),
                    (float) starInitPos.x, (float) starInitPos.z, true);
            smallStar.shoot(starRadialShootVec.getX(), starRadialShootVec.getY(), starRadialShootVec.getZ(),
                    5.2f, 0f);
            world.addEntity(smallStar);
        }
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SPELL_CARD_BLANK.get());
    }

}

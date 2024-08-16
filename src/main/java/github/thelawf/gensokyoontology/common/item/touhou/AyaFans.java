package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class AyaFans extends Item implements IRayTraceReader {
    public AyaFans(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {
        if (playerIn.getCooldownTracker().hasCooldown(this))
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        AxisAlignedBB aabb = new AxisAlignedBB(playerIn.getPositionVec().subtract(new Vec3(5, 10, 5)),
                playerIn.getPositionVec().add(new Vec3(5, 10, 5)));

        Vec3 lookVec = playerIn.getLookVec();
        // List<LivingEntity> livings = worldIn.getEntitiesWithinAABB(LivingEntity.class, aabb);
//
        // livings.forEach(living -> {
        //     if (playerIn.getPositionVec().distanceTo(living.getPositionVec()) <= 5 &&
        //             !(living instanceof Player)) {
        //         living.applyKnockback(5.0f, -lookVec.x, -lookVec.z);
        //     }
        // });

        Predicate<LivingEntity> predicate = living -> !(living instanceof Player);
        getEntityWithinSphere(worldIn, LivingEntity.class, predicate, aabb, 12F).forEach(living -> {
            living.applyKnockback(3.0f, -lookVec.x, -lookVec.z);
        });

        // 和上面是一样的功能，只不过先使用Stream流进行了判断。
        // 首先仍然是获取在一个方形碰撞箱内的所有投掷物实体，然后使用Stream.filter()保留了距离玩家12格以内的投掷物实体
        // 将这些被保留的实体使用Stream.collect()方法重新转换成列表，再用列表的内置.forEach()方法对每一个投掷物执行操作
        AxisAlignedBB box = new AxisAlignedBB(playerIn.getPositionVec().subtract(new Vec3(12, 12, 12)),
                playerIn.getPositionVec().add(new Vec3(12, 12, 12)));

        getEntityWithinSphere(worldIn, ProjectileEntity.class, box, 12).forEach(projectile ->
                applyProjectileKnockback(projectile, 3.0f, -lookVec.x, -lookVec.z));

        if (worldIn.isRemote) {
            for (int i = 0; i < GSKOMathUtil.randomRange(30, 60); i++) {
                worldIn.addParticle(ParticleTypes.CLOUD, false, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                        lookVec.x + random.nextDouble(), lookVec.y + random.nextDouble(), lookVec.z + random.nextDouble());
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void applyProjectileKnockback(ProjectileEntity projectile, float strength, double ratioX, double ratioZ) {
        if (!(strength <= 0.0F)) {
            Vec3 vector3d = projectile.getMotion();
            Vec3 vector3d1 = (new Vec3(ratioX, 0.0D, ratioZ)).normalize().scale(strength);
            projectile.setMotion(vector3d.x / 2.0D - vector3d1.x, projectile.isOnGround() ? Math.min(0.4D, vector3d.y / 2.0D + (double) strength) : vector3d.y, vector3d.z / 2.0D - vector3d1.z);
        }
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".aya_fans"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

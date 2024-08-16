package github.thelawf.gensokyoontology.common.item.touhou;

import com.google.common.collect.ImmutableList;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.entity.misc.LaserSourceEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class KoishiEyeOpen extends Item implements IRayTraceReader {
    private int totalCount = 0;
    public KoishiEyeOpen(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn.getCooldownTracker().hasCooldown(this))
            return ActionResult.resultPass(stack);

        if (stack.getTag() == null || !stack.getTag().contains("total_count")) {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("total_count",  playerIn.ticksExisted + this.getUseDuration(stack));
            stack.setTag(nbt);
        }

        LaserSourceEntity laserSource = new LaserSourceEntity(worldIn, playerIn);
        laserSource.init(200, 40, 85);
        laserSource.setARGB(0x88FF0000);
        // lasers(worldIn, playerIn);

        laserSource.setLocationAndAngles(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight() * 0.5,
                playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
        worldIn.addEntity(laserSource);

        playerIn.setActiveHand(handIn);
        if (stack.getTag() != null && playerIn.ticksExisted < stack.getTag().getInt("total_count")) {
            if (playerIn.ticksExisted < stack.getTag().getInt("total_count")) {
                playerIn.stopActiveHand();
                applyRayDamage(worldIn, playerIn, stack, 15);
            }
            else onItemUseFinish(stack, worldIn, playerIn);
        }

        if (playerIn.isCreative()) return super.onItemRightClick(worldIn, playerIn, handIn);
        // playerIn.getCooldownTracker().setCooldown(this, 100);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void lasers(Level worldIn, Player playerIn) {
        List<Integer> colors = ImmutableList.of(
                0x88FF0000, 0x88FF8C00, 0x88FFFF00, 0x8800FF00, 0x8800FFFF, 0x880000FF, 0x88FF00FF, 0x88800080
        );
        for (int i = 0; i < colors.size(); i++) {
            LaserSourceEntity laser = new LaserSourceEntity(worldIn, playerIn);
            laser.init(500, 40, 120);
            laser.setARGB(colors.get(i));
            laser.setLocationAndAngles(playerIn.getPosX() + (double) i / 2, playerIn.getPosY() + playerIn.getEyeHeight() * 0.5,
                    playerIn.getPosZ(), 0, 0);
            worldIn.addEntity(laser);
        }
    }


    /**
     * 这里实现的是妖怪测谎仪的激光，需要注意的是这里第一行的谓词需要取非再传入IRayTraceReader.getEntityWithinSphere()
     */
    public void applyRayDamage(Level worldIn, LivingEntity user, ItemStack stack, int radius) {
        Predicate<LivingEntity> predicate = living -> living instanceof Player;

        if (predicate.test(user)) {
            Player player = (Player) user;
            if (player.getCooldownTracker().hasCooldown(this)) return;
            AxisAlignedBB box = createCubeBox(player.getPositionVec(), radius);

            Vec3 start = player.getPositionVec();
            Vec3 vec = new Vec3(player.getLookVec().x, 0, player.getLookVec().z);
            Vec3 vector3d = start.add(vec.scale(10));

            // TODO: 妖怪测谎仪数值设计
            for (int i = 0; i < 8; i++) {
                double angle = Math.PI * 2 / this.getUseDuration(stack) * totalCount;
                Vec3 end = vector3d.rotateYaw((float) Math.PI * 2 / 8 * i).rotateYaw((float) angle);
                getEntityWithinSphere(worldIn, LivingEntity.class, predicate.negate(), box, 12F).stream()
                        .filter(living -> isIntersecting(start, end, living.getBoundingBox().offset(0, -1, 0)))
                        .forEach(living -> living.attackEntityFrom(DamageSource.causePlayerDamage(player), 8F));
                if (angle >= Math.PI * 2) totalCount = 0;
            }
        }
    }

    @Override
    public void onPlayerStoppedUsing(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        stack.setTag(new CompoundTag());
    }

    @Override
    public @NotNull ItemStack onItemUseFinish(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        stack.setTag(new CompoundTag());
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 50;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".koishi_eye_open"));
        if (Screen.hasShiftDown()) {
            tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".koishi_eye_open.comment"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BOW;
    }
}

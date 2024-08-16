package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KoishiEyeClosed extends Item {
    public KoishiEyeClosed(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {

        if (playerIn.getCooldownTracker().hasCooldown(this))
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        // 获取绝对坐标
        Vec3 playerPos = playerIn.getPositionVec();

        List<AbstractDanmakuEntity> entities = new ArrayList<>();
        if (worldIn.isClientSide) {
            for (int i = 0; i < 50; i++) {
                Vec3 lookVec = playerIn.getLookVec().scale(i);

                Vec3 posLine = new Vec3(lookVec.x > 0 ? Vector3f.XP : Vector3f.XN);
                Vec3 posColumn = new Vec3(lookVec.z > 0 ? Vector3f.ZP : Vector3f.ZN);
                Vec3 posVertical = new Vec3(lookVec.y > 0 ? Vector3f.YP : Vector3f.YN);

                Vec3 rayPos = playerPos.add(lookVec);

                AxisAlignedBB aabb = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos),
                        GSKOMathUtil.vecCeil(rayPos));
                AxisAlignedBB aabb1 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posLine)),
                        GSKOMathUtil.vecCeil(rayPos.add(posLine)));
                AxisAlignedBB aabb2 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posColumn)),
                        GSKOMathUtil.vecCeil(rayPos.add(posColumn)));
                AxisAlignedBB aabb3 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posVertical)),
                        GSKOMathUtil.vecCeil(rayPos.add(posVertical)));

                entities.addAll(worldIn.getEntitiesWithinAABB(AbstractDanmakuEntity.class, aabb.grow(2)));
                entities.addAll(worldIn.getEntitiesWithinAABB(AbstractDanmakuEntity.class, aabb1.grow(2)));
                entities.addAll(worldIn.getEntitiesWithinAABB(AbstractDanmakuEntity.class, aabb2.grow(2)));
                entities.addAll(worldIn.getEntitiesWithinAABB(AbstractDanmakuEntity.class, aabb3.grow(2)));

                entities.forEach(Entity::remove);

            }
        }

        if (playerIn.isCreative())
            return super.onItemRightClick(worldIn, playerIn, handIn);

        playerIn.getCooldownTracker().setCooldown(this, 300);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".koishi_eye_closed"));
        if (Screen.hasShiftDown()) {
            tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".koishi_eye_closed.comment"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

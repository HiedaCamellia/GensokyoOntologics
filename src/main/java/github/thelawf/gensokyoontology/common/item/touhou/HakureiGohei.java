package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.client.gui.screen.skill.GoheiModeSelectScreen;
import github.thelawf.gensokyoontology.common.entity.misc.DreamSealEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.InYoJadeDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.EnumUtil;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.common.util.math.GeometryUtil;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * 博丽灵梦的御币
 */
public class HakureiGohei extends Item implements IRayTraceReader {
    public static final Component TITLE = GensokyoOntology.withTranslation("gui.", ".hakurei_gohei.title");
    public HakureiGohei(Properties properties) {
        super(properties);
    }

    private void setMode(CompoundTag nbt, Mode mode) {
        nbt.putInt("mode", mode.ordinal());
    }

    public static Mode getMode(CompoundTag nbt) {
        return Mode.values()[nbt.getInt("mode")];
    }

    private Mode switchMode(CompoundTag nbt) {
        return EnumUtil.switchEnum(Mode.class, getMode(nbt));
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {
        if (playerIn.getCooldownTracker().hasCooldown(this) && !playerIn.isCreative())
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getTag() != null) {
            switch(getMode(stack.getTag())) {
                default:
                case DANMAKU:
                    InYoJadeDanmakuEntity inYoJade = new InYoJadeDanmakuEntity(worldIn, playerIn);
                    DanmakuUtil.shootDanmaku(worldIn, playerIn, inYoJade, 1F, 0F);
                    break;
                case DREAM_SEAL:
                    fireDreamSeal(worldIn, playerIn);
                    break;
                case SPELL_CARD:
                    break;
            }
        }

        if (playerIn.isCreative()) return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        playerIn.getCooldownTracker().setCooldown(this, 10);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    public void fireDreamSeal(Level worldIn, Player playerIn) {
        for (int i = 0; i < 8; i++) {
            int i1 = i % 3;
            DanmakuColor color;
            switch (i1) {
                default:
                case 0:
                    color = DanmakuColor.RED;
                    break;
                case 1:
                    color = DanmakuColor.BLUE;
                    break;
                case 2:
                    color = DanmakuColor.GREEN;
                    break;
            }
            Vec3 vector3d = i % 2 == 0 ? new Vec3(2, 3, 0).rotatePitch((float) Math.PI * 2 / i) :
                    new Vec3(2, 3, 0).rotatePitch((float) -Math.PI * 2 / i);
            // Vec3 shootVec = playerIn.getLookVec();
            Vec3 initPos = vector3d.add(playerIn.getPositionVec());

            DreamSealEntity dreamSeal = new DreamSealEntity(worldIn, playerIn, color);
            dreamSeal.setNoGravity(true);
            dreamSeal.shoot(vector3d.x, vector3d.y, vector3d.z, 1.2f, 0f);
            dreamSeal.setLocationAndAngles(initPos.x, initPos.y, initPos.z, playerIn.rotationYaw, playerIn.rotationPitch);
            worldIn.addEntity(dreamSeal);

        }
    }

    @Override
    public void fillItemGroup(@NotNull ItemGroup group, @NotNull NonNullList<ItemStack> items) {
        if (group == GSKOItemTab.GSKO_ITEM_TAB) {
            ItemStack itemStack = new ItemStack(this);
            CompoundTag tag = new CompoundTag();
            tag.putInt("mode", Mode.DANMAKU.ordinal());
            itemStack.setTag(tag);
            items.add(itemStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        Component text = GensokyoOntology.withTranslation("tooltip.", ".hakurei_gohei.mode");
        if (stack.getTag() != null) {
            switch (getMode(stack.getTag())) {
                default:
                case DANMAKU:
                    tooltip.add(GoheiModeSelectScreen.DANMAKU);
                    break;
                case SPELL_CARD:
                    break;
                case DREAM_SEAL:
                    tooltip.add(GoheiModeSelectScreen.DREAM_SEAL);
                    break;
            }
        }
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }

    public enum Mode {
        DANMAKU,
        SPELL_CARD,
        DREAM_SEAL
    }
}

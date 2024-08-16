package github.thelawf.gensokyoontology.common.item.danmaku;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.HeartShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOCombatTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HeartShot extends DanmakuItem {

    DanmakuType type;

    public HeartShot(DanmakuType type) {
        super(new Properties().group(GSKOCombatTab.GSKO_COMBAT_TAB));
        this.type = type;
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        final String typeName = String.valueOf(this.getItem().getRegistryName());
        final String modid = GensokyoOntology.MODID + ":";
        DanmakuColor danmakuColor = DanmakuColor.NONE;

        switch (typeName) {
            case modid + "heart_shot_red":
                danmakuColor = DanmakuColor.RED;
                break;
            case modid + "heart_shot_pink":
                danmakuColor = DanmakuColor.PINK;
                break;
            case modid + "heart_shot_aqua":
                danmakuColor = DanmakuColor.AQUA;
                break;
            case modid + "heart_shot_blue":
                danmakuColor = DanmakuColor.BLUE;
                break;
            default:
                break;
        }

        CompoundTag nbt = new CompoundTag();
        nbt.putInt("color", danmakuColor.ordinal());

        HeartShotEntity heartShot = new HeartShotEntity(playerIn, worldIn, nbt);
        DanmakuUtil.shootDanmaku(worldIn, playerIn, heartShot, 0.6f, 0f);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

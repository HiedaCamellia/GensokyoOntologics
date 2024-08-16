package github.thelawf.gensokyoontology.common.item.danmaku;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOCombatTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RiceShot extends DanmakuItem {
    public RiceShot(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        final String typeName = String.valueOf(this.getItem().getRegistryName());
        final String modid = GensokyoOntology.MODID + ":";
        DanmakuColor danmakuColor = DanmakuColor.NONE;

        switch (typeName) {
            case modid + "rice_shot_red":
                danmakuColor = DanmakuColor.RED;
                break;
            case modid + "rice_shot_blue":
                danmakuColor = DanmakuColor.BLUE;
                break;
            case modid + "rice_shot_purple":
                danmakuColor = DanmakuColor.PURPLE;
                break;
            default:
                break;
        }

        RiceShotEntity riceShot = new RiceShotEntity(playerIn, worldIn, DanmakuType.RICE_SHOT, danmakuColor);
        DanmakuUtil.shootDanmaku(worldIn, playerIn, riceShot, 0.6f, 0f);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

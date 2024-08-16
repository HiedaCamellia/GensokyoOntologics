package github.thelawf.gensokyoontology.common.item.danmaku;

import github.thelawf.gensokyoontology.common.entity.projectile.FakeLunarEntity;
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

public class FakeLunarItem extends DanmakuItem {
    public DanmakuType type;

    public FakeLunarItem(DanmakuType type) {
        super(new Properties().group(GSKOCombatTab.GSKO_COMBAT_TAB));
        this.type = type;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        FakeLunarEntity fakeLunar = new FakeLunarEntity(playerIn, worldIn, DanmakuType.FAKE_LUNAR, DanmakuColor.NONE);
        DanmakuUtil.shootDanmaku(worldIn, playerIn, fakeLunar, 0.6f, 0f);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

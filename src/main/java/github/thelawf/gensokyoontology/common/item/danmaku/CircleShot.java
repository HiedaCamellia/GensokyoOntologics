package github.thelawf.gensokyoontology.common.item.danmaku;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.projectile.CircleShotEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.ScriptedDanmakuEntity;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CircleShot extends DanmakuItem {
    public CircleShot(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        final String typeName = String.valueOf(this.getItem().getRegistryName());
        final String modid = GensokyoOntology.MODID + ":";
        DanmakuColor danmakuColor = DanmakuColor.NONE;

        switch (typeName) {
            case modid + "circle_shot_green":
                danmakuColor = DanmakuColor.GREEN;
                break;
            case modid + "circle_shot_blue":
                danmakuColor = DanmakuColor.BLUE;
                break;
            case modid + "circle_shot_magenta":
                danmakuColor = DanmakuColor.MAGENTA;
                break;
            default:
                break;
        }

        CompoundTag nbt = new CompoundTag();
        nbt.putInt("color", danmakuColor.ordinal());

        CircleShotEntity circleShot = new CircleShotEntity(playerIn, worldIn, nbt);
        DanmakuUtil.shootDanmaku(worldIn, playerIn, circleShot, 0.6f, 0f);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

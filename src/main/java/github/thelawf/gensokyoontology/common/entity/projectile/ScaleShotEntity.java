package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ScaleShotEntity extends AbstractDanmakuEntity {

    public ScaleShotEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public ScaleShotEntity(LivingEntity throwerIn, Level worldIn, DanmakuType danmakuTypeIn, DanmakuColor danmakuColorIn) {
        super(EntityRegistry.SCALE_SHOT_ENTITY.get(), throwerIn, worldIn, danmakuTypeIn, danmakuColorIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        IItemProvider item;
        switch (getDanmakuColor()) {
            case RED:
            case PINK:
            case NONE:
            case ORANGE:
            case YELLOW:
                item = ItemRegistry.SCALE_SHOT_YELLOW.get();
                break;
            case GREEN:
                item = ItemRegistry.SCALE_SHOT_GREEN.get();
                break;
            case AQUA:
            case BLUE:
                item = ItemRegistry.SCALE_SHOT_BLUE.get();
                break;
            case MAGENTA:
            case PURPLE:
                item = ItemRegistry.SCALE_SHOT_PURPLE.get();
                break;
            default:
                item = ItemRegistry.SCALE_SHOT_RED.get();
                break;
        }

        return new ItemStack(item);
    }
}

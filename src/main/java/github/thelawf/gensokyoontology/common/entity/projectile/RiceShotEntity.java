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

public class RiceShotEntity extends AbstractDanmakuEntity {

    public RiceShotEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public RiceShotEntity(LivingEntity livingIn, Level worldIn, DanmakuType danmakuType, DanmakuColor danmakuColor) {
        super(EntityRegistry.RICE_SHOT_ENTITY.get(), livingIn, worldIn, danmakuType, danmakuColor);
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
            case YELLOW:
            case AQUA:
            case GREEN:
            default:
                item = ItemRegistry.RICE_SHOT_RED.get();
                break;
            case BLUE:
                item = ItemRegistry.RICE_SHOT_BLUE.get();
                break;
            case PURPLE:
                item = ItemRegistry.RICE_SHOT_PURPLE.get();
                break;
        }

        return new ItemStack(item);
    }
}

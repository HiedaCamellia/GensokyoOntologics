package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.SpellData;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class LargeShotEntity extends AbstractDanmakuEntity implements IRendersAsItem {

    public LargeShotEntity(Level worldIn) {
        super(EntityRegistry.LARGE_SHOT_ENTITY.get(), worldIn);
    }

    public LargeShotEntity(LivingEntity throwerIn, Level world, SpellData spellData) {
        super(EntityRegistry.LARGE_SHOT_ENTITY.get(), throwerIn, world, spellData);
    }

    public LargeShotEntity(EntityType<LargeShotEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public LargeShotEntity(LivingEntity throwerIn, Level worldIn, DanmakuType danmakuTypeIn, DanmakuColor danmakuColorIn) {
        super(EntityRegistry.LARGE_SHOT_ENTITY.get(), throwerIn, worldIn, danmakuTypeIn, danmakuColorIn);
    }

    public LargeShotEntity(EntityType<LargeShotEntity> entityType, LivingEntity throwerIn, Level worldIn, DanmakuType danmakuTypeIn, DanmakuColor danmakuColorIn) {
        super(entityType, throwerIn, worldIn, danmakuTypeIn, danmakuColorIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        IItemProvider item = null;
        switch (getDanmakuColor()) {
            case RED:
                item = ItemRegistry.LARGE_SHOT_RED.get();
                break;
            case ORANGE:
                item = ItemRegistry.LARGE_SHOT_ORANGE.get();
                break;
            case NONE:
            case PINK:
            case AQUA:
                item = ItemRegistry.LARGE_SHOT_AQUA.get();
                break;
            case YELLOW:
                item = ItemRegistry.LARGE_SHOT_YELLOW.get();
                break;
            case GREEN:
                item = ItemRegistry.LARGE_SHOT_GREEN.get();
                break;
            case BLUE:
                item = ItemRegistry.LARGE_SHOT_BLUE.get();
                break;
            case PURPLE:
                item = ItemRegistry.LARGE_SHOT_PURPLE.get();
                break;
            case MAGENTA:
                item = ItemRegistry.LARGE_SHOT_MAGENTA.get();
                break;
        }

        if (item == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(item);
        }
    }

}

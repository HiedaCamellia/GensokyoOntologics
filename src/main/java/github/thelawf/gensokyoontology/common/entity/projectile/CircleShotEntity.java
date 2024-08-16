package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CircleShotEntity extends ScriptedDanmakuEntity {
    public CircleShotEntity(LivingEntity throwerIn, Level worldIn, CompoundTag scriptIn) {
        super(EntityRegistry.CIRCLE_SHOT_ENTITY.get(), throwerIn, worldIn, DanmakuType.RICE_SHOT, scriptIn);
    }

    public CircleShotEntity(EntityType<CircleShotEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void onScriptTick() {
        super.onScriptTick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        IItemProvider item;
        switch (this.getDanmakuColor()) {
            case RED:
            case PINK:
            case NONE:
            case YELLOW:
            case AQUA:
            case GREEN:
            default:
                item = ItemRegistry.CIRCLE_SHOT_GREEN.get();
                break;
            case BLUE:
                item = ItemRegistry.CIRCLE_SHOT_BLUE.get();
                break;
            case MAGENTA:
                item = ItemRegistry.CIRCLE_SHOT_MAGENTA.get();
                break;
        }

        return new ItemStack(item);
    }
}

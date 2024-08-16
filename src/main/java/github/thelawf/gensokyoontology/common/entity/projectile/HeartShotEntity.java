package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.SpellData;
import github.thelawf.gensokyoontology.common.util.danmaku.TransformFunction;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableEntity;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class HeartShotEntity extends ScriptedDanmakuEntity implements IRendersAsItem {

    public HeartShotEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public HeartShotEntity(LivingEntity throwerIn, Level worldIn, CompoundTag scriptIn) {
        super(EntityRegistry.HEART_SHOT_ENTITY.get(), throwerIn, worldIn, DanmakuType.HEART_SHOT, scriptIn);
    }

    private void applyTransform(HashMap<Integer, TransformFunction> keyTransforms) {
        for (Map.Entry<Integer, TransformFunction> entry : keyTransforms.entrySet()) {
            Integer keyTick = entry.getKey();
            TransformFunction function = entry.getValue();
            if (function.transformOrders == null) return;

        }
    }

    @Override
    public void onScriptTick() {
        super.onScriptTick();
        ListNBT inbts = getBehaviors(this.scriptsNBT);
        for (INBT inbt : inbts) {
            CompoundTag behavior = wrapAsCompound(inbt);
            if (behavior.contains("addMotion") && behavior.get("addMotion") instanceof ListNBT) {
                List<Double> paramList = wrapAsDoubleFromList((ListNBT) behavior.get("addMotion"));
                if (paramList.size() != 3) return;
                this.setMotion(this.getMotion().add(paramList.get(0), paramList.get(1), paramList.get(2)).normalize());
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        IItemProvider item = null;
        switch (getDanmakuColor()) {
            case RED:
            case YELLOW:
            case GREEN:
            case PURPLE:
            case NONE:
                item = ItemRegistry.HEART_SHOT_RED.get();
                break;
            case AQUA:
                item = ItemRegistry.HEART_SHOT_AQUA.get();
                break;
            case BLUE:
                item = ItemRegistry.HEART_SHOT_BLUE.get();
                break;
            case PINK:
                item = ItemRegistry.HEART_SHOT_PINK.get();
                break;
        }

        if (item == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(item);
        }
    }
}

package github.thelawf.gensokyoontology.common.entity.projectile;

import github.thelawf.gensokyoontology.common.util.GSKODamageSource;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.SpellData;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FakeLunarEntity extends AbstractDanmakuEntity {

    public FakeLunarEntity(EntityType<? extends ThrowableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public FakeLunarEntity(LivingEntity throwerIn, Level world, SpellData spellData) {
        super(EntityRegistry.FAKE_LUNAR_ENTITY.get(), throwerIn, world, spellData);
    }

    public FakeLunarEntity(LivingEntity throwerIn, Level worldIn, DanmakuType danmakuTypeIn, DanmakuColor danmakuColorIn) {
        super(EntityRegistry.FAKE_LUNAR_ENTITY.get(), throwerIn, worldIn, danmakuTypeIn, danmakuColorIn);
    }

    @Override
    protected void onEntityHit(@NotNull EntityRayTraceResult result) {
        Entity shooter = getShooter();

        if (result.getEntity() instanceof AbstractDanmakuEntity) {
            AbstractDanmakuEntity danmaku = (AbstractDanmakuEntity) result.getEntity();
            danmaku.remove();
        } else if (!(result.getEntity() instanceof Player)) {
            // entityHit.addPotionEffect(new EffectInstance(EffectRegistry.LOVE_EFFECT.get(), 5 * 40));
            result.getEntity().attackEntityFrom(GSKODamageSource.DANMAKU, 12F);
            this.remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.FAKE_LUNAR_ITEM.get());
    }
}

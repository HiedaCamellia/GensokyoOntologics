package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.common.entity.ai.goal.DamakuAttackGoal;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.passive.IFlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerLevel;
import net.minecraft.world.LightType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class SpectreEntity extends RetreatableEntity implements IRendersAsItem, IFlyingAnimal {

    public SpectreEntity(EntityType<? extends RetreatableEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setNoGravity(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DamakuAttackGoal(this, 30, 0.6f));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.8f));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.getPositionVec().add(new Vec3(0.2,0.2,0.2)).add(new Vec3(
                GSKOMathUtil.randomRange(-0.2,0.2), GSKOMathUtil.randomRange(-0.2,0.2), GSKOMathUtil.randomRange(-0.2,0.2)));
        this.world.addParticle(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, GSKOMathUtil.randomRange(-0.1,0.1), GSKOMathUtil.randomRange(-0.1,0.1), GSKOMathUtil.randomRange(-0.1,0.1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.WANDERING_SOUL.get());
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static boolean canMonsterSpawnInLight(EntityType<SpectreEntity> type, IServerLevel worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn);
    }
    public static boolean isValidLightLevel(IServerLevel worldIn, BlockPos pos, Random randomIn) {
        if (worldIn.getLightFor(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.level().isThundering() ? worldIn.getNeighborAwareLightSubtracted(pos, 10) : worldIn.getLight(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    @Override
    public void danmakuAttack(LivingEntity target) {
        if (ticksExisted % 10 == 0) {
            Vec3 direction = new Vec3(target.getPosX() - this.getPosX(), target.getPosY() - this.getPosY(), target.getPosZ() - this.getPosZ());
            SmallShotEntity danmaku = new SmallShotEntity(this.getOwner(), world, DanmakuType.LARGE_SHOT, DanmakuColor.BLUE);
            DanmakuUtil.initDanmaku(danmaku, this.getPositionVec(), true);
            danmaku.shoot(direction.x, direction.y, direction.z, 0.78f, 0f);
            this.world.addEntity(danmaku);
        }
    }

    private <D extends AbstractDanmakuEntity> void aimedShot(LivingEntity target) {
    }
}

package github.thelawf.gensokyoontology.common.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ILevel;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Predicate;

public abstract class YoukaiEntity extends RetreatableEntity {

    /**
     * 是否被退治
     */
    protected boolean isRetreated = false;
    /**
     * 好感度
     */
    protected int favorability = 0;
    protected boolean duringSpellCard = false;

    // @OnlyIn(Dist.CLIENT)
    // private Animation animation = Animation.IDLE;
    public static final DataParameter<Boolean> DATA_RETREATED = EntityDataManager.createKey(
            YoukaiEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> DATA_FAVORABILITY = EntityDataManager.createKey(YoukaiEntity.class, DataSerializers.VARINT);

    protected YoukaiEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_RETREATED, this.isRetreated);
        this.dataManager.register(DATA_FAVORABILITY, this.favorability);
    }

    /**
     * 怪不得继承自YoukaiEntity的实体死不了呢（）原来是我之前在这里判断如果战胜了妖怪则将她驯服了啊（
     *
     */
    @Override
    public void onDeath(@NotNull DamageSource cause) {
        // if (!this.isRetreated) {
        //     this.setHealth(this.getMaxHealth());
        //     this.setOwnerId(cause.getTrueSource() instanceof Player && cause.getTrueSource() == null ?
        //             cause.getTrueSource().getUniqueID() : null);
        //     if (this.getOwnerId() != null) this.setRetreated(true);
        //     return;
        // }
        super.onDeath(cause);
    }

    public int getFavorability() {
        return this.dataManager.get(DATA_FAVORABILITY);
    }

    public void setFavorability(int favorabilityIn) {
        this.dataManager.set(DATA_FAVORABILITY, favorabilityIn);
    }

    public void setRetreated(boolean isRetreated) {
        this.dataManager.set(DATA_RETREATED, isRetreated);
    }

    public boolean isRetreated() {
        return this.dataManager.get(DATA_RETREATED);
    }

    public boolean isDuringSpellCardAttack(boolean isDuringSpellCardAttack){
        this.duringSpellCard = isDuringSpellCardAttack;
        return duringSpellCard;
    }
    // @OnlyIn(Dist.CLIENT)
    // public void setAnimation(Animation animation) {
    //     this.animation = animation;
    // }

    // @OnlyIn(Dist.CLIENT)
    // public Animation getAnimation() {
    //     return animation;
    // }


    @Override
    public void onKillCommand() {
        this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
    }

    public abstract void danmakuAttack(LivingEntity target);

}

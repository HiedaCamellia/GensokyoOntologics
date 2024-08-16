package github.thelawf.gensokyoontology.common.entity;

import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IAngerable;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.AbstractSkeletonEntity;
import net.minecraft.world.entity.passive.AnimalEntity;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.entity.passive.TurtleEntity;
import net.minecraft.world.entity.passive.WolfEntity;
import net.minecraft.world.entity.passive.horse.LlamaEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;


public class HaniwaEntity extends TameableEntity implements IAngerable {
    public HaniwaEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 0.7D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 0.7D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, true));
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int time) {

    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return null;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {

    }

    @Override
    public void func_230258_H__() {

    }
}

package github.thelawf.gensokyoontology.common.entity.monster;

import com.google.common.collect.ImmutableList;
import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.common.entity.ai.goal.GSKOBossGoal;
import github.thelawf.gensokyoontology.common.entity.ai.goal.LaserSpiralGoal;
import github.thelawf.gensokyoontology.common.entity.ai.goal.SpellCardAttackGoal;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.boss.BossSpell;
import github.thelawf.gensokyoontology.common.entity.spellcard.boss.RemiliaSpellAttack;
import net.minecraft.world.entity.CreatureEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class RemiliaScarletEntity extends YoukaiEntity implements ISpellCardUser {
    public RemiliaScarletEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1D, true));
        this.goalSelector.addGoal(3, new LaserSpiralGoal(this, new GSKOBossGoal.Stage(GSKOBossGoal.Type.NON_SPELL, 500, false)));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4f));
        this.goalSelector.addGoal(8, new LookAtGoal(this, Player.class, 0.8f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, CreatureEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, TsumiBukuroEntity.class, true));
    }

    @Override
    public void danmakuAttack(LivingEntity target) {
    }

    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        List<Runnable> runnables = ImmutableList.of(
                () -> RemiliaSpellAttack.tickLaserSpiral(this),
                () -> RemiliaSpellAttack.sphere(this.world, this)
        );

        runnables.get(this.getRNG().nextInt(runnables.size())).run();
    }
}

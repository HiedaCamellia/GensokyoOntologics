package github.thelawf.gensokyoontology.common.entity.monster;

import com.google.common.collect.ImmutableList;
import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.common.entity.ai.goal.*;
import github.thelawf.gensokyoontology.common.entity.spellcard.FullCherryBlossomEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.boss.FlandreSpellAttack;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.CreatureEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class FlandreScarletEntity extends YoukaiEntity implements ISpellCardUser {

    // public final GSKOBossGoal.Stage stage;
    public FlandreScarletEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
        this.favorability = -10;
        // this.stage = new GSKOBossGoal.Stage(GSKOBossGoal.Type.SPELL_CARD_BREAKABLE,
        //         new ScarletPrisoner(worldIn, this), 500, true);
        // this.setHeldItem(Hand.MAIN_HAND, new ItemStack(ItemRegistry.CLOCK_HAND_ITEM.get()));
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return super.createChild(world, mate);
    }

    @Override
    protected void registerGoals() {

        FlandreSpellAttack flandreSpell = new FlandreSpellAttack(this.world, this);

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1D, true));
        this.goalSelector.addGoal(3, new SummonEyeGoal(this));
        // this.goalSelector.addGoal(3, new DamakuAttackGoal(this, 40, 1f));
        this.goalSelector.addGoal(3, new SpellCardAttackGoal(this, flandreSpell.bossSpell));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4f));
        this.goalSelector.addGoal(6, new LookAtGoal(this, Player.class, 0.8f));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, CreatureEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TsumiBukuroEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, true));
    }

    @Override
    @NotNull
    public ActionResultType getEntityInteractionResult(@NotNull Player playerIn, @NotNull Hand hand) {
        return super.getEntityInteractionResult(playerIn, hand);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        // if (this.getIdleTime() == 0 && this.getAnimation() == Animation.IDLE) {
        //     this.setAnimation(Animation.WALKING);
        // } else {
        //     this.setAnimation(Animation.IDLE);
        // }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void danmakuAttack(LivingEntity target) {
        ImmutableList<Consumer<FlandreScarletEntity>> list = ImmutableList.of(FlandreSpellAttack::laser, FlandreSpellAttack::sphere);
        GSKOUtil.rollFrom(list).accept(this);
    }

    @Override
    public int getAngerTime() {
        return super.getAngerTime();
    }

    @Override
    public void setAngerTime(int time) {
        super.setAngerTime(time);
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return super.getAngerTarget();
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        super.setAngerTarget(target);
    }

    @Override
    public void func_230258_H__() {
        super.func_230258_H__();
    }

    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        if (spellCard == null) return;
        spellCard.onTick(this.world, this, ticksIn);
        spellCard.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        world.addEntity(spellCard);
    }

    public boolean isDoppelganger() {
        return false;
    }

    public static class Doppelganger extends FlandreScarletEntity {
        public static final int LIFE = 1000;
        public final FullCherryBlossomEntity SPELL_CARD = new FullCherryBlossomEntity(world, this);
        // public final GSKOBossGoal.Stage stage = new GSKOBossGoal.Stage(GSKOBossGoal.Type.SPELL_CARD_BREAKABLE,
        //         SPELL_CARD, 1200, true);

        public Doppelganger(EntityType<? extends TameableEntity> type, Level worldIn) {
            super(EntityRegistry.FLANDRE_DOPPELDANGER.get(), worldIn);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(2, new SitGoal(this));
            this.goalSelector.addGoal(3, new FlandreSpellAttackGoal(this, new GSKOBossGoal.Stage(GSKOBossGoal.Type.SPELL_BREAKABLE, 500, false)));
            this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4F));
            this.goalSelector.addGoal(8, new LookAtGoal(this, Player.class, 0.8F));
            this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        }

        @Override
        public void tick() {
            super.tick();
            if (this.ticksExisted >= LIFE) this.remove();
        }

        @Override
        public boolean isDoppelganger() {
            return true;
        }
    }
}

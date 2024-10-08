package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.common.entity.ConversationalEntity;
import github.thelawf.gensokyoontology.common.entity.ai.goal.KoishiSpellAttackGoal;
import github.thelawf.gensokyoontology.common.entity.spellcard.IdonokaihoEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class KomeijiKoishiEntity extends ConversationalEntity implements ISpellCardUser {

    private int angerTime;
    private UUID angerTarget;
    private static final DataParameter<Integer> DATA_FAVORABILITY = EntityDataManager.createKey(KomeijiKoishiEntity.class, DataSerializers.VARINT);
    public static final String KEY_FAVORABILITY = "favourability";

    public KomeijiKoishiEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_FAVORABILITY, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new KoishiSpellAttackGoal(this, 0.7F));

        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4f));
        this.goalSelector.addGoal(8, new LookAtGoal(this, Player.class, 0.8f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, CreatureEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void writeAdditional(@NotNull CompoundTag compound) {
        super.writeAdditional(compound);
        compound.putInt(KEY_FAVORABILITY, getFavorability());
    }

    @Override
    public void readAdditional(@NotNull CompoundTag compound) {
        super.readAdditional(compound);
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int time) {
        this.angerTime = time;
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return this.angerTarget;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        this.angerTarget = target;
    }

    @Override
    public void func_230258_H__() {

    }

    public int getFavorability() {
        return this.dataManager.get(DATA_FAVORABILITY);
    }

    @Override
    public void danmakuAttack(LivingEntity target) {

    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public DialogTreeNode getNextDialog(int optionIndex) {
        return optionIndex == 0 ? new DialogTreeNode("root").accessBranch(optionIndex) :
                new DialogTreeNode("root");
    }

    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        if (spellCard instanceof IdonokaihoEntity) {
            // this.setNoGravity(true);
            // if (GSKOMathUtil.isBetween(ticksIn, 10, 50)) this.setMotion(0, 0.6, 0);
            spellCard.tick();
        }
        this.setNoGravity(false);
    }
}

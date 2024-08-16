package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.api.dialog.DialogTreeNode;
import github.thelawf.gensokyoontology.common.entity.ConversationalEntity;
import github.thelawf.gensokyoontology.common.entity.ai.goal.GSKOBossGoal;
import github.thelawf.gensokyoontology.common.entity.ai.goal.LilyWhiteBossBattleGoal;
import github.thelawf.gensokyoontology.common.entity.spellcard.FullCherryBlossomEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.SpellCardEntity;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.IPacket;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LilyWhiteEntity extends ConversationalEntity implements ISpellCardUser {


    public LilyWhiteEntity(EntityType<LilyWhiteEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setDialog(new DialogTreeNode("lily_white"));
    }


    @Override
    protected void registerGoals() {
        GSKOBossGoal.Stage stage = new GSKOBossGoal.Stage(GSKOBossGoal.Type.NON_SPELL, 500, true);
        // stages.put(GSKOBossGoal.Type.SPELL_CARD_BREAKABLE, Pair.of(50f, 2000));

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LilyWhiteBossBattleGoal(this, stage, 0.4f));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void spellCardAttack(SpellCardEntity spellCard, int ticksIn) {
        if (spellCard == null) {
            return;
        }

        spellCard.onTick(world, this, ticksIn);

    }

    @Nullable
    @Override
    public AgeableMob createChild(@NotNull ServerLevel world, @NotNull AgeableMob mate) {
        return null;
    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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


    @Override
    public DialogTreeNode getNextDialog(int optionIndex) {
        return optionIndex == 0 ? new DialogTreeNode("root").accessBranch(optionIndex) :
                new DialogTreeNode("root");
    }

    @Override
    public void danmakuAttack(LivingEntity target) {

    }
}

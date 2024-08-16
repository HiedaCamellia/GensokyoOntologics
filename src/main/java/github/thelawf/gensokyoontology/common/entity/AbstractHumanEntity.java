package github.thelawf.gensokyoontology.common.entity;

import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobEntity;
import net.minecraft.world.entity.ai.brain.Brain;
import net.minecraft.world.entity.ai.controller.BodyController;
import net.minecraft.world.entity.ai.goal.LookAtGoal;
import net.minecraft.world.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.world.entity.ai.goal.SwimGoal;
import net.minecraft.world.entity.merchant.IMerchant;
import net.minecraft.world.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.passive.ParrotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MerchantOffer;
import net.minecraft.world.item.MerchantOffers;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractHumanEntity extends AgeableMob implements Npc {
    protected AbstractHumanEntity(EntityType<? extends AgeableMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.3D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamicIn) {
        return super.createBrain(dynamicIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}

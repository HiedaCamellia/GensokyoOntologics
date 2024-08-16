package github.thelawf.gensokyoontology.common.entity.passive;

import github.thelawf.gensokyoontology.common.entity.AbstractHumanEntity;
import github.thelawf.gensokyoontology.common.entity.trade.GSKOTrades;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.world.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MerchantOffer;
import net.minecraft.world.item.MerchantOffers;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public class HumanResidentEntity extends AbstractHumanEntity {

    public HumanResidentEntity(EntityType<? extends AbstractVillagerEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    protected PathNavigator createNavigator(Level worldIn) {
        return super.createNavigator(worldIn);
    }


    @Override
    protected void onVillagerTrade(MerchantOffer offer) {
    }

    @Override
    protected ActionResultType getEntityInteractionResult(Player playerIn, Hand hand) {
        if (!this.isAlive() || this.hasCustomer() || this.isSleeping()) return super.getEntityInteractionResult(playerIn, hand);
        if (this.getOffers().isEmpty()) return ActionResultType.func_233537_a_(this.world.isRemote);

        if (!this.world.isRemote) {
            this.setCustomer(playerIn);
            this.openMerchantContainer(playerIn, this.getDisplayName(), 1);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    @Override
    protected void populateTradeData() {
        this.addTrades(this.getOffers(), GSKOTrades.HUMAN_RESIDENT_TRADE, 2);
    }
}

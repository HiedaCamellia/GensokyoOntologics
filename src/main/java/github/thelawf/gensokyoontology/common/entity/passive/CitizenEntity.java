package github.thelawf.gensokyoontology.common.entity.passive;

import github.thelawf.gensokyoontology.common.entity.AbstractHumanEntity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MerchantOffer;
import net.minecraft.world.item.MerchantOffers;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CitizenEntity extends AbstractHumanEntity {

    public CitizenEntity(EntityType<? extends AgeableMob> type, Level worldIn) {
        super(type, worldIn);
    }

}

package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.capability.entity.GSKOPowerCapability;
import github.thelawf.gensokyoontology.common.network.GSKONetworking;
import github.thelawf.gensokyoontology.common.network.packet.CPowerChangedPacket;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PowerItem extends Item {
    public PowerItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        if (worldIn.isRemote) {
            GSKOUtil.showChatMsg(playerIn, "[Client] Power: " + GSKOPowerCapability.INSTANCE.getCount(), 1);
        }
        if (worldIn.isClientSide) {
            GSKOUtil.showChatMsg(playerIn, "[Server] Power: " + GSKOPowerCapability.INSTANCE.getCount(), 1);
        }

        float count = GSKOMathUtil.randomRange(0.1f, 1f);
        GSKOPowerCapability.INSTANCE.add(count);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

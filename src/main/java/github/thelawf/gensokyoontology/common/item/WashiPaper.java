package github.thelawf.gensokyoontology.common.item;

import com.github.tartaricacid.touhoulittlemaid.capability.PowerCapabilityProvider;
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class WashiPaper extends Item {
    public WashiPaper(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, Hand handIn) {
        // if (worldIn.isRemote) {
        //     final String str = "net.minecraft.client.entity.player.";
        //     GSKOUtil.showChatMsg(playerIn, playerIn.getClass().getName().replace(str, "") + " Has Power Cap " + (playerIn.getCapability(PowerCapabilityProvider.POWER_CAP).isPresent()), 1);
        //     GSKOUtil.showChatMsg(playerIn, playerIn.getClass().getName().replace(str, "") + " Has Life time Cap " + (playerIn.getCapability(GSKOCapabilities.SECULAR_LIFE).isPresent()), 1);
        // }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

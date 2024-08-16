package github.thelawf.gensokyoontology.common.block.ore;

import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

public class ImmemorialAlloyBlock extends OreBlock {
    public ImmemorialAlloyBlock() {
        super(Properties.from(Blocks.NETHERITE_BLOCK).hardnessAndResistance(40f, 5000f).harvestLevel(5).setRequiresTool());
    }

    @Override
    public void onBlockHarvested(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (player.getHeldItemMainhand().getItem().canHarvestBlock(state)) {
            GSKOUtil.showChatMsg(player, "You can't harvest", 1);
            return;
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}

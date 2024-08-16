package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.common.tileentity.SorceryExtractorTileEntity;
import github.thelawf.gensokyoontology.common.tileentity.SpellConsoleTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 符卡控制台
 */
public class SpellCardConsoleBlock extends Block {
    public SpellCardConsoleBlock() {
        super(Properties.from(Blocks.ENDER_CHEST));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SpellConsoleTileEntity();
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof SpellConsoleTileEntity) {
                // GSKOUtil.log(this.getClass(), "Item Handler Present? " + (tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()));
                INamedContainerProvider provider = SpellConsoleTileEntity.create(worldIn, pos);
                NetworkHooks.openGui((ServerPlayer) player, provider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Missing Container Provider");
            }
        }
        return ActionResultType.SUCCESS;
    }
}

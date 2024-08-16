package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.common.tileentity.SorceryExtractorTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SorceryExtractorBlock extends Block {
    public SorceryExtractorBlock() {
        super(Properties.from(Blocks.ENCHANTING_TABLE));
    }

    public static final VoxelShape SHAPE;

    static {
        VoxelShape terrace = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);
        VoxelShape crystal = Block.makeCuboidShape(7, 7, 9, 7, 16, 9);
        SHAPE = VoxelShapes.or(terrace, crystal);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SorceryExtractorTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public ActionResultType onBlockActivated(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof SorceryExtractorTileEntity) {
                // GSKOUtil.log(this.getClass(), "Item Handler Present? " + (tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()));
                INamedContainerProvider provider = SorceryExtractorTileEntity.createContainer(worldIn, pos);
                NetworkHooks.openGui((ServerPlayer) player, provider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Missing Container Provider");
            }
        }
        return ActionResultType.SUCCESS;
    }
}

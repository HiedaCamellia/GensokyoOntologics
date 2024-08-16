package github.thelawf.gensokyoontology.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Dakimakura extends Block {
    public static final BooleanProperty FRONT = BooleanProperty.create("front");
    public static final BooleanProperty BACK = BooleanProperty.create("back");
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public Dakimakura() {
        super(Properties.create(Material.WOOL).hardnessAndResistance(1.f).notSolid());
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide && handIn == Hand.MAIN_HAND && state.get(BACK)) {
            state.cycleValue(FRONT);
            worldIn.setBlockState(pos, state);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        } else if (worldIn.isRemote && handIn == Hand.MAIN_HAND && state.get(FRONT)) {
            state.cycleValue(BACK);
            worldIn.setBlockState(pos, state);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        return ActionResultType.PASS;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return super.createTileEntity(state, world);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        BlockState state = context.level().getBlockState(context.getPos());
        return state.matchesBlock(this) && state.get(FACING) == direction ? this.getDefaultState().with(
                FACING, direction.getOpposite()) : this.getDefaultState().with(FACING, direction);
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FRONT);
        builder.add(BACK);
        builder.add(FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public Block getBlock() {
        return this;
    }

}

package github.thelawf.gensokyoontology.common.block.decoration;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.capability.GSKOCapabilities;
import github.thelawf.gensokyoontology.common.tileentity.HaniwaTileEntity;
import github.thelawf.gensokyoontology.common.util.BeliefType;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;


import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HaniwaBlock extends Block {
    public static final VoxelShape BODY_MAIN = makeCuboidShape(3, 0, 3, 13, 12, 13);
    public static final VoxelShape BODY_TOP = makeCuboidShape(4, 12, 4, 12, 14, 12);
    public static final VoxelShape LEFT_BASE = makeCuboidShape(13, 5, 7, 14, 8, 10);
    public static final VoxelShape LEFT_ARM = makeCuboidShape(14, 3, 8, 16, 8, 10);
    public static final VoxelShape RIGHT_BASE = makeCuboidShape(2, 5, 7, 3, 8, 10);
    public static final VoxelShape RIGHT_ARM_HORIZONTAL = makeCuboidShape(-2, 6, 7, 2, 8, 9);
    public static final VoxelShape RIGHT_ARM_VERTICAL = makeCuboidShape(-2, 8, 7, 0, 12, 9);

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return VoxelShapes.or(BODY_MAIN, BODY_TOP, LEFT_BASE, LEFT_ARM, RIGHT_BASE, RIGHT_ARM_HORIZONTAL, RIGHT_ARM_VERTICAL);
    }

    public HaniwaBlock() {
        super(Properties.from(Blocks.FLOWER_POT));
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HaniwaTileEntity();
    }

    @Override
    public void onBlockPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            if (serverLevel.getTileEntity(pos) instanceof HaniwaTileEntity) {
                HaniwaTileEntity haniwaTile = (HaniwaTileEntity) serverLevel.getTileEntity(pos);
                if (haniwaTile != null) {
                    haniwaTile.setCanAddCount(true);
                }
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {

        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            if (serverLevel.getTileEntity(pos) instanceof HaniwaTileEntity) {
                HaniwaTileEntity haniwaTile = (HaniwaTileEntity) serverLevel.getTileEntity(pos);
                if (haniwaTile != null) {
                    if (!haniwaTile.canAddCount()) {
                        player.sendMessage(GensokyoOntology.withTranslation("msg.",".haniwa_block.in_cooldown"), player.getUniqueID());
                        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
                    }

                    haniwaTile.addFaith(1);
                    haniwaTile.setCanAddCount(false);
                    haniwaTile.setOwnerId(player.getUniqueID());

                    player.sendMessage(GensokyoOntology.withTranslation("msg.", ".haniwa_block.added_count"), player.getUniqueID());
                    GSKOUtil.showChatMsg(player, haniwaTile.getFaithCount(), 1);
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);
        if (!world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) world;
            if (serverLevel.getTileEntity(pos) instanceof HaniwaTileEntity) {
                HaniwaTileEntity haniwaTile = (HaniwaTileEntity) serverLevel.getTileEntity(pos);
                if (haniwaTile != null) {
                    haniwaTile.setFaith(0);
                }
            }
        }
    }

}

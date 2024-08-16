package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.common.tileentity.SpaceFissureTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpaceFissureBlock extends Block {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final VoxelShape shape;

    static {
        VoxelShape portalPane = Block.makeCuboidShape(1, 0, 8, 15, 24, 8);
        shape = VoxelShapes.or(portalPane);
    }

    public SpaceFissureBlock() {
        super(Properties.create(Material.PORTAL).hardnessAndResistance(11400.f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        return new SpaceFissureTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, @NotNull Level worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn instanceof ServerLevel && !(entityIn instanceof Player)) {
            entityIn.attackEntityFrom(DamageSource.OUT_OF_WORLD, 12.0F);
        } else if (worldIn instanceof ServerLevel && !entityIn.isPassenger() &&
                !entityIn.isBeingRidden() && entityIn.canChangeDimension()) {
            RegistryKey<Level> dimensionKey = entityIn.world.dimension() == Level.OVERWORLD ? GSKODimensions.GENSOKYO : Level.OVERWORLD;
            ServerLevel world = ((ServerLevel) worldIn).getServer().level(dimensionKey);

            if (world == null) {
                LOGGER.warn("The Dimsion {} on this server does not exist!", dimensionKey.getRegistryName());
                return;
            }
            entityIn.changeDimension(world);
            LOGGER.info("Player {} has been to {}", ((Player) entityIn).getGameProfile().getName(),
                    dimensionKey.getRegistryName());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }


}

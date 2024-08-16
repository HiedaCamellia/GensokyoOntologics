package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.INBTRunnable;
import github.thelawf.gensokyoontology.api.util.INBTWriter;
import github.thelawf.gensokyoontology.common.tileentity.GapTileEntity;
import github.thelawf.gensokyoontology.common.world.TeleportHelper;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.network.chat.Component;


import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class GapBlock extends Block implements INBTWriter {

    private BlockPos tilePos;

    // public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape SUKIMA_PLANE_X = Block.makeCuboidShape(-4.0D, 0.0D, 4.0D, 20.0D, 16.0D, 4.0D);
    protected static final VoxelShape SUKIMA_PLANE_Z = Block.makeCuboidShape(4.0D, 0.0D, -4.0D, 4.0D, 16.0D, 20.0D);

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return SUKIMA_PLANE_X;
    }

    public GapBlock() {
        super(Properties.from(Blocks.NETHER_PORTAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GapTileEntity();
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel worldIn, @NotNull BlockPos pos, @NotNull Random random) {
        super.randomTick(state, worldIn, pos, random);
        // if (worldIn.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < worldIn.getDifficulty().getId()) {
        //
        // }
    }

    @Override
    public void onBlockPlacedBy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (!(placer instanceof Player)) return;
        Player player = (Player) placer;

        if (stack.getTag() != null) {
            CompoundTag nbt = stack.getTag();
            setBlockTileSecond(player, worldIn, pos, nbt);
            stack.setTag(new CompoundTag());
            stack.shrink(1);
            return;
        }

        setBlockTileFirst(worldIn, player, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);

        if (worldIn.isClientSide && entityIn instanceof ServerPlayer) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            ServerPlayer serverPlayer = (ServerPlayer) entityIn;
            tryTeleport(serverLevel, serverPlayer, pos);
        }
    }

    private void setBlockTileFirst(Level worldIn, Player player, BlockPos firstPos) {
        CompoundTag itemNBT = new CompoundTag();
        itemNBT.putBoolean("is_first_placement", true);
        itemNBT.putLong("first_pos", firstPos.toLong());

        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            RegistryKey<Level> departureLevel = serverLevel.dimension();
            itemNBT.putString("departure_world", departureLevel.getLocation().toString());
            itemNBT.putInt("pos_x", firstPos.getX());
            itemNBT.putInt("pos_y", firstPos.getY());
            itemNBT.putInt("pos_z", firstPos.getZ());

            ItemStack itemStack = player.getHeldItemMainhand();
            itemStack.setTag(itemNBT);
            itemStack.grow(1);

            // player.sendMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.set_first_gap"), player.getUniqueID());
            // player.sendMessage(Component.literal(firstPos.getCoordinatesAsString()), player.getUniqueID());
            // player.sendMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.in_dimension"), player.getUniqueID());
            // player.sendMessage(Component.translatable(departureLevel.getLocation().toString()), player.getUniqueID());
        }

        worldIn.setBlockState(firstPos, BlockRegistry.GAP_BLOCK.get().getDefaultState());

        if (worldIn.getTileEntity(firstPos) instanceof GapTileEntity) {
            GapTileEntity gapTile = (GapTileEntity) worldIn.getTileEntity(firstPos);
            if (gapTile != null) {
                gapTile.markDirty();
            }
        }
    }

    private void setBlockTileSecond(Player player, Level worldIn, BlockPos secondPos, @NotNull CompoundTag itemNBT) {
        worldIn.setBlockState(secondPos, BlockRegistry.GAP_BLOCK.get().getDefaultState());

        if (worldIn.isClientSide && worldIn.getTileEntity(secondPos) instanceof GapTileEntity) {

            RegistryKey<Level> departureKey = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ResourceLocation.parse(itemNBT.getString("departure_world")));
            if (worldIn.getServer() == null) return;
            ServerLevel depatureLevel = worldIn.getServer().level(departureKey);
            if (depatureLevel == null) return;

            ServerLevel arrivalLevel = (ServerLevel) worldIn;
            RegistryKey<Level> arrivalKey = arrivalLevel.dimension();
            BlockPos firstPos = new BlockPos(getNBTInt(itemNBT, "pos_x"), getNBTInt(itemNBT, "pos_y"), getNBTInt(itemNBT, "pos_z"));

            GapTileEntity secondPlacedSukima = (GapTileEntity) arrivalLevel.getTileEntity(secondPos);
            GapTileEntity firstPlacedSukima = (GapTileEntity) depatureLevel.getTileEntity(firstPos);

            if (secondPlacedSukima != null) {
                // player.sendMessage(Component.literal("2nd Gap is Present"), player.getUniqueID());
                secondPlacedSukima.setDestinationPos(firstPos);
                secondPlacedSukima.setDestinationLevel(departureKey);
                secondPlacedSukima.markDirty();

            }
            if (firstPlacedSukima != null) {
                // player.sendMessage(Component.literal("1st Gap is Present"), player.getUniqueID());
                firstPlacedSukima.setDestinationPos(secondPos);
                firstPlacedSukima.setDestinationLevel(arrivalKey);
                firstPlacedSukima.markDirty();

                // player.sendMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.set_second_gap"), player.getUniqueID());
                // player.sendMessage(Component.literal("§3" + firstPos.getCoordinatesAsString()), player.getUniqueID());
                // player.sendMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.in_dimension"), player.getUniqueID());
                // player.sendMessage(Component.translatable("§a" + arrivalKey.getLocation()), player.getUniqueID());
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, @NotNull List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getTag() != null && stack.getTag().contains("first_pos")) {
            CompoundTag nbt = stack.getTag();
            if (nbt.contains("first_pos")) {
                BlockPos pos = BlockPos.fromLong(nbt.getLong("first_pos"));
                tooltip.add(Component.literal("第一处隙间设置为: " + pos.getCoordinatesAsString()));
            }
            if (nbt.contains("departure_world")) {
                tooltip.add(Component.translatable(nbt.getString("departure_world")));
            }
        }
    }

    public void tryTeleport(ServerLevel departureLevel, ServerPlayer serverPlayer, BlockPos depaturePos) {
        if (departureLevel.getTileEntity(depaturePos) instanceof GapTileEntity) {
            GapTileEntity departureGap = getGapTile(departureLevel, depaturePos);
            ServerLevel destinationLevel = departureLevel.getServer().level(departureGap.getDestinationLevel());

            if (departureGap.getCooldown() > 1) return;
            if (destinationLevel == null) {
                serverPlayer.sendStatusMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.teleport_fail.destination_not_present"), true);
                return;
            }

            if (getGapTile(destinationLevel, departureGap.getDestinationPos()) == null) {
                serverPlayer.sendStatusMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.teleport_fail.arrival_gap_not_present"), true);
                return;
            }
            GapTileEntity arrivalGap = getGapTile(destinationLevel, departureGap.getDestinationPos());
            arrivalGap.setCooldown(400);
            TeleportHelper.applyGapTeleport(serverPlayer, destinationLevel, departureGap);

            if (departureGap.getDestinationPos() == BlockPos.ZERO) {
                serverPlayer.sendStatusMessage(GensokyoOntology.withTranslation("msg.", ".gap_block.teleport_fail.illegal_position"), true);
            }
        }
    }

    public GapTileEntity getGapTile(ServerLevel serverLevel, BlockPos pos) {
        return (GapTileEntity) serverLevel.getTileEntity(pos);
    }

}

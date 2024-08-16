package github.thelawf.gensokyoontology.common.world;

import com.mojang.datafixers.util.Pair;
import github.thelawf.gensokyoontology.common.tileentity.GapTileEntity;
import github.thelawf.gensokyoontology.common.util.GSKOUtil;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.util.RegistryKey;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.gen.feature.structure.WoodlandMansionStructure;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fluids.IFluidBlock;
import org.apache.logging.log4j.LogManager;

import java.util.function.Function;

public class TeleportHelper {
    public static void teleport(ServerPlayer player, ServerLevel destination, BlockPos pos) {

        if (canTeleport(destination, pos)) {
            player.changeDimension(destination, new ITeleporter() {
                @Override
                public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destLevel,
                                          float yaw, Function<Boolean, Entity> repositionEntity) {
                    BlockPos validPos = findValidPos(destination, pos).getSecond();

                    entity = repositionEntity.apply(false);
                    entity.setPosition(validPos.getX(), validPos.getY(), validPos.getZ());
                    return entity;
                }
            });
        }
    }

    public static void applyGapTeleport(ServerPlayer player, ServerLevel destination, GapTileEntity gapTile) {
        BlockPos pos = gapTile.getDestinationPos();
        // if (isInSameDimension(player.world.dimension(), destination.dimension())) {
        //     player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
        //     // player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
        //     gapTile.setCooldown(400);
        //     return;
        // }

        player.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
                gapTile.setCooldown(400);
                return player;
            }
        });
    }

    public static void applyStructureTeleport(ServerLevel destination) {

    }

    /**
     * 获取目的地世界对应位置的方块状态，如果玩家位置是空气且玩家脚下的方块是固态方块则进行传送
     */
    public static boolean canTeleport(ServerLevel destination, BlockPos pos) {
        if (destination == null)
            return false;

        BlockPos lowerPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
        BlockPos upperPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        BlockState eyeBlock = destination.getBlockState(upperPos);
        BlockState playerBlock = destination.getBlockState(pos);
        BlockState standBlock = destination.getBlockState(lowerPos);

        if (eyeBlock.equals(Blocks.AIR.getDefaultState()) && playerBlock.equals(Blocks.AIR.getDefaultState())) {
            if (standBlock.getBlock().equals(Blocks.AIR)) {
                destination.setBlockState(lowerPos, BlockRegistry.SAKURA_PLANKS.get().getDefaultState());
                LogManager.getLogger().info("Position Valid");
                return true;
            } else if (standBlock.getBlock() instanceof IFluidBlock) {
                destination.setBlockState(lowerPos, BlockRegistry.SAKURA_PLANKS.get().getDefaultState());
                LogManager.getLogger().info("Stand Position Has Fluids");
                return true;
            } else {
                return isValidPos(destination, pos);
            }
        } else {
            if (standBlock.getBlock().equals(Blocks.AIR)) {
                return clearAndSetBlocks(destination, pos);
            }
            return clearBlocks(destination, pos);
        }
    }

    private static boolean clearAndSetBlocks(ServerLevel destination, BlockPos pos) {
        final BlockState air = Blocks.AIR.getDefaultState();
        final BlockState sakuraPlanks = BlockRegistry.SAKURA_PLANKS.get().getDefaultState();
        destination.setBlockState(pos, air);

        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                destination.setBlockState(pos.toMutable().move(x, pos.getY(), z), sakuraPlanks);
                destination.setBlockState(pos.toMutable().move(-x, pos.getY(), -z), sakuraPlanks);
                for (int y = 0; y < 2; y++) {
                    destination.setBlockState(pos.toMutable().move(x, y, z), air);
                    destination.setBlockState(pos.toMutable().move(-x, y, -z), air);
                }
            }
        }
        return true;
    }

    private static boolean clearBlocks(ServerLevel destination, BlockPos pos) {
        final BlockState air = Blocks.AIR.getDefaultState();
        destination.setBlockState(pos, air);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    destination.setBlockState(pos.toMutable().move(i, j, k), air);
                    destination.setBlockState(pos.toMutable().move(-i, j, -k), air);
                }
            }
        }
        return true;
    }

    private static boolean isValidPos(ServerLevel destination, BlockPos pos) {
        LogManager.getLogger().info("Other Condition is: " + findValidPos(destination, pos).getFirst());
        return findValidPos(destination, pos).getFirst();
    }

    private static Pair<Boolean, BlockPos> findValidPos(ServerLevel destination, BlockPos pos) {
        for (int i = 0; i < 255; i++) {
            BlockPos playerPos = new BlockPos(pos.getX(), i, pos.getZ());
            BlockPos standPos = new BlockPos(pos.down().getX(), i, pos.down().getZ());
            BlockPos eyePos = new BlockPos(pos.up().getX(), i, pos.up().getZ());
            if (!destination.getBlockState(standPos).getBlock().equals(Blocks.AIR) &&
                    destination.getBlockState(playerPos).getBlock().equals(Blocks.AIR) &&
                    destination.getBlockState(eyePos).getBlock().equals(Blocks.AIR)) {
                return Pair.of(true, playerPos);
            }
        }
        return Pair.of(false, pos);
    }

    private static boolean isInSameDimension(RegistryKey<Level> departureLevel, RegistryKey<Level> destination) {
        return departureLevel == destination;
    }
}

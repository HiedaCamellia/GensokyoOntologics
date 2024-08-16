package github.thelawf.gensokyoontology.common.entity.spawn;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.common.util.world.GSKOLevelUtil;
import github.thelawf.gensokyoontology.common.world.dimension.biome.GSKOBiomes;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;

import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Reference From {@link net.minecraft.world.spawner.WanderingTraderSpawner WanderingTraderSpawner} in which the field is <br>
 * field_221249_d -> wanderingTraderSpawnDelay; <br>
 * field_221249_e -> wanderingTraderSpawnChance;
 */
public class LilyWhiteSpawner {

    public static void spawn(ServerLevel serverLevel, Player player, BlockPos pos, int ticks, float chance) {
        Random random = new Random();

        if (validateCondition(serverLevel, ticks, random, chance) && validateSpawnPos(player, serverLevel)) {
            EntityRegistry.LILY_WHITE_ENTITY.get().spawn(serverLevel, null, null, pos, SpawnReason.NATURAL, false, false);
        }
    }

    public static boolean validateCondition(ServerLevel serverLevel, int ticks, Random random, float chance) {
        return GSKOMathUtil.isBetween(ticks % 20000, 0, 10) && random.nextFloat() <= chance &&
                serverLevel.getEntities().noneMatch(entity -> entity.getType() == EntityRegistry.LILY_WHITE_ENTITY.get());
    }

    public static boolean validateSpawnPos(Player player, ServerLevel world) {
        Random random = new Random();

        Vec3 lowerPos = DanmakuUtil.getRandomPos(player.getPositionVec(), new Vector3f(3f, 3f, 3f));

        boolean flag = false;

        for (int i = 0; i < 256; i++) {

            BlockPos pos = new BlockPos(lowerPos.getX(), i, lowerPos.getZ());
            BlockPos upperPos = new BlockPos(lowerPos.getX(), i+2, lowerPos.getZ());
            BlockPos middlePos = new BlockPos(lowerPos.getX(), i+1, lowerPos.getZ());

            BlockState eyeBlock = world.getBlockState(upperPos);
            BlockState middleBlock = world.getBlockState(middlePos);
            BlockState standBlock = world.getBlockState(pos);

            if (eyeBlock.equals(Blocks.AIR.getDefaultState()) && middleBlock.equals(Blocks.AIR.getDefaultState())) {
                if (!standBlock.getBlock().equals(Blocks.AIR)) {
                    flag = true;
                    break;
                }
            }
        }
        //player.sendMessage(Component.literal("Flag is " + flag), player.getUniqueID());

        return flag && GSKOLevelUtil.isEntityInBiome(player, GSKOBiomes.HAKUREI_SHRINE_PRECINCTS_KEY);
    }
}

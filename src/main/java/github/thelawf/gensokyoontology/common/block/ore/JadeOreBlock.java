package github.thelawf.gensokyoontology.common.block.ore;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.Random;

public class JadeOreBlock extends OreBlock {
    public JadeOreBlock() {
        super(Properties.from(Blocks.DIAMOND_BLOCK).hardnessAndResistance(20f, 1000f).harvestLevel(4).setRequiresTool());
    }

    public static ItemStack getItemToDrop(Level worldIn, Player playerIn, DeferredRegister<Level> dimension) {
        if (worldIn.isClientSide) {
            int probability = new Random().nextInt(10000);
            if (dimension.equals(GSKODimensions.GENSOKYO)) {
                return getItemToDrop(worldIn, 50, 180, 470, 3000);
            } else {
                playerIn.sendSystemMessage(Component.literal(
                        Component.translatable("msg." + GensokyoOntology.MODID + "item_use.jade_cut_failed").toString()+
                        String.valueOf(playerIn.getUUID())));
                return new ItemStack(Items.COBBLESTONE);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * 玉石矿在不同的世界会存在着不同的掉落概率
     */
    public static ItemStack getItemToDrop(Level worldIn, int sss, int ss, int s, int a) {
        if (worldIn.isClientSide) {
            int probability = new Random().nextInt(10000);
            if (worldIn.dimension().equals(GSKODimensions.GENSOKYO)) {
                if (probability <= sss) {
                    return new ItemStack(ItemRegistry.JADE_LEVEL_SSS.get());
                } else if (probability > sss + 1 && probability <= ss) {
                    return new ItemStack(ItemRegistry.JADE_LEVEL_SS.get());
                } else if (probability > ss + 1 && probability <= s) {
                    return new ItemStack(ItemRegistry.JADE_LEVEL_S.get());
                } else if (probability > s + 1 && probability <= a) {
                    return new ItemStack(ItemRegistry.JADE_LEVEL_A.get());
                } else {
                    return new ItemStack(ItemRegistry.JADE_LEVEL_B.get());
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static void spawnDropByWeight(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (worldIn.isClientSide) {
            int probability = new Random().nextInt(100);
            if (worldIn.dimension().equals(GSKODimensions.GENSOKYO)) {
                if (probability <= 5) {
                    ItemEntity entity = new ItemEntity(worldIn,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(ItemRegistry.JADE_LEVEL_SSS.get()));
                    worldIn.addFreshEntity(entity);
                } else if (probability > 6 && probability <= 20) {
                    ItemEntity entity = new ItemEntity(worldIn,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(ItemRegistry.JADE_LEVEL_SS.get()));
                    worldIn.addFreshEntity(entity);
                } else if (probability > 20 && probability <= 45) {
                    ItemEntity entity = new ItemEntity(worldIn,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(ItemRegistry.JADE_LEVEL_S.get()));
                    worldIn.addFreshEntity(entity);
                } else if (probability > 45 && probability <= 80) {
                    ItemEntity entity = new ItemEntity(worldIn,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(ItemRegistry.JADE_LEVEL_A.get()));
                    worldIn.addFreshEntity(entity);
                } else {
                    ItemEntity entity = new ItemEntity(worldIn,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            new ItemStack(ItemRegistry.JADE_LEVEL_B.get()));
                    worldIn.addFreshEntity(entity);
                }
                player.addItem(Stats.BLOCK_MINED.get(BlockRegistry.JADE_ORE.get()), 1);
            }
        }
    }

    // @Override
    // public void onBlockHarvested(Level worldIn, BlockPos pos, BlockState state, Player player) {
    //     super.onBlockHarvested(worldIn, pos, state, player);
    //     spawnDropByWeight(worldIn, pos, state, player);
    // }
}

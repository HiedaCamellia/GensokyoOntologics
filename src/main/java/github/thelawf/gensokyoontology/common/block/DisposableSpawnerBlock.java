package github.thelawf.gensokyoontology.common.block;

import github.thelawf.gensokyoontology.common.tileentity.DisposableSpawnerTile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

import net.minecraft.world.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.SpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisposableSpawnerBlock extends Block {
    public DisposableSpawnerBlock() {
        super(Properties.from(Blocks.SPAWNER));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull Hand handIn, @NotNull BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() instanceof SpawnEggItem) {
            SpawnEggItem spawnEgg = (SpawnEggItem) player.getHeldItem(handIn).getItem();
            DisposableSpawnerTile spawnerTile = (DisposableSpawnerTile) worldIn.getTileEntity(pos);

            if (spawnerTile == null) return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
            spawnerTile.setEntityType(spawnEgg.getType(player.getHeldItem(handIn).getTag()));
            player.getHeldItem(handIn).shrink(1);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DisposableSpawnerTile();
    }
}

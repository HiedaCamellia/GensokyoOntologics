package github.thelawf.gensokyoontology.common.tileentity;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowingFluidBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.entity.monster.CreeperEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * 一次性刷怪笼
 */
public class DisposableSpawnerTile extends BlockEntity implements ITickableTileEntity, IRayTraceReader {

    private EntityType<?> entityType;
    private boolean canContinueSpawn;

    public DisposableSpawnerTile() {
        super(TileEntityRegistry.DISPOSABLE_SPAWNER_TILE_ENTITY.get());
        this.canContinueSpawn = true;
        this.entityType = EntityRegistry.FLANDRE_SCARLET.get();
    }

    @Override
    public void tick() {
        if (this.world != null && this.world instanceof ServerLevel) {

            Player player = this.world.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 60, false);
            if (player == null) return;

            Predicate<DisposableSpawnerTile> predicate = tileEntity ->
                    tileEntity.getSpawnEntity() != null && !player.isCreative();
            spawn(predicate, player);
            markDirty();
        }
    }

    private void spawn(Predicate<DisposableSpawnerTile> predicate, Player triggeredPlayer) {
        if (this.getSpawnEntity() != null && !triggeredPlayer.isCreative() && this.world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) this.world;
            CompoundTag compound = this.write(new CompoundTag());
            BlockPos.Mutable blockPos = this.pos.toMutable().move(GSKOMathUtil.randomRange(-3, 3), 1, GSKOMathUtil.randomRange(-3, 3));
            Optional<EntityType<?>> optionalEntity = EntityType.readEntityType(compound);
            optionalEntity.ifPresent(type -> type.spawn(serverLevel, null, null, blockPos.toImmutable(), SpawnReason.SPAWNER, false, false));

            this.canContinueSpawn = false;
            markDirty();
            this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
        }
    }

    public void setEntityType(EntityType<?> entityTypeIn) {
        this.entityType = entityTypeIn;
        markDirty();
    }

    public EntityType<?> getSpawnEntity() {
        return this.entityType;
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundTag nbt) {
        super.read(state, nbt);
        Optional<EntityType<?>> entityOptional = EntityType.readEntityType(nbt);
        entityOptional.ifPresent(type -> this.entityType = entityOptional.get());
    }

    @Override
    @NotNull
    public CompoundTag write(@NotNull CompoundTag compound) {
        super.write(compound);
        compound.putString("id", this.getSpawnEntity().getRegistryName() == null ?
                GensokyoOntology.withRL("flandre_scarlet").toString() : this.getSpawnEntity().getRegistryName().toString());
        compound.putBoolean("can_continue_spawn", this.canContinueSpawn);
        return compound;
    }
}

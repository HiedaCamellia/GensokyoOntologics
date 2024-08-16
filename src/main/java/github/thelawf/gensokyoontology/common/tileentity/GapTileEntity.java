package github.thelawf.gensokyoontology.common.tileentity;

import github.thelawf.gensokyoontology.common.util.world.GSKOLevelUtil;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class GapTileEntity extends BlockEntity implements ITickableTileEntity {

    private static final int MAX_COOLDOWN_TICK = 400;
    private boolean allowTeleport = false;
    private BlockPos destinationPos;
    private RegistryKey<Level> destinationLevel;
    private int cooldown = 0;

    public GapTileEntity(RegistryKey<Level> destinationLevel, BlockPos destinationPos) {
        super(TileEntityRegistry.GAP_TILE_ENTITY.get());
        this.setDestinationLevel(destinationLevel);
        this.setDestinationPos(destinationPos);
    }

    public GapTileEntity() {
        super(TileEntityRegistry.GAP_TILE_ENTITY.get());
        this.setDestinationLevel(Level.OVERWORLD);
        this.setDestinationPos(BlockPos.ZERO);
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundTag tag) {
        super.handleUpdateTag(state, tag);
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundTag nbt) {
        if (nbt.contains("DestinationX") && nbt.contains("DestinationY") && nbt.contains("DestinationZ")) {
            this.destinationPos = new BlockPos(nbt.getInt("DestinationX"), nbt.getInt("DestinationY"), nbt.getInt("DestinationZ"));
        }
        if (nbt.contains("DestinationLevel")) {
            this.destinationLevel = GSKOLevelUtil.levelDimension(ResourceLocation.parse(
                    nbt.getString("DestinationLevel")));
        }
        if (nbt.contains("AllowTeleport")) {
            this.allowTeleport = nbt.getBoolean("AllowTeleport");
        }
        if (nbt.contains("Cooldown")) {
            setCooldown(nbt.getInt("Cooldown"));
        }
        super.read(state, nbt);
    }

    @Override
    @Nonnull
    public CompoundTag write(CompoundTag compound) {
        super.write(compound);
        compound.putString("DestinationLevel", this.destinationLevel.getLocation().toString());
        compound.putBoolean("AllowTeleport", this.allowTeleport);
        compound.putInt("Cooldown", this.cooldown);
        compound.putInt("DestinationX", this.destinationPos.getX());
        compound.putInt("DestinationY", this.destinationPos.getY());
        compound.putInt("DestinationZ", this.destinationPos.getZ());

        return compound;
    }

    public void setDestinationPos(BlockPos destinationPos) {
        this.destinationPos = destinationPos;
        markDirty();
    }

    public void setDestinationLevel(RegistryKey<Level> destinationLevel) {
        this.destinationLevel = destinationLevel;
        this.allowTeleport = true;
        markDirty();
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        markDirty();
    }

    public BlockPos getDestinationPos() {
        return this.destinationPos;
    }

    public RegistryKey<Level> getDestinationLevel() {
        return this.destinationLevel;
    }

    public boolean isAllowTeleport() {
        return this.allowTeleport;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setAllowTeleport(boolean isAllowTeleport) {
        this.allowTeleport = isAllowTeleport;
        markDirty();
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote) {
            if (this.cooldown > 0) {
                this.cooldown--;
                markDirty();
            } else {
                this.cooldown++;
                markDirty();
            }
        }
    }
}

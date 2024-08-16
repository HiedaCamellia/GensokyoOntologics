package github.thelawf.gensokyoontology.common.tileentity;

import com.google.common.collect.ImmutableList;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractList;
import java.util.List;

public class AdobeTileEntity extends BlockEntity {
    private List<Vector3i> positionCarved;
    public AdobeTileEntity() {
        super(TileEntityRegistry.ADOBE_TILE_ENTITY.get());
    }

    @Override
    public void read(@NotNull BlockState state, CompoundTag nbt) {
        if (nbt.get("PositionCarved") != null) {
            INBT iNBT = nbt.get("PositionCarved");
            if (iNBT instanceof ListNBT) {
                ListNBT listNBT = ((ListNBT) iNBT);
                tryReadListNBT(listNBT);
            }
        }
        super.read(state, nbt);
    }

    @NotNull
    @Override
    public CompoundTag write(@NotNull CompoundTag compound) {
        super.write(compound);
        ListNBT listNBT = new ListNBT();
        this.positionCarved.forEach(vector3i -> listNBT.add(new IntArrayNBT(ImmutableList.of(vector3i.getX(), vector3i.getY(), vector3i.getZ()))));
        compound.put("PositionCarved", listNBT);
        return super.write(compound);
    }

    private void tryReadListNBT(ListNBT nbt) {
        for (INBT inbt : nbt) {
            if (inbt instanceof IntArrayNBT) {
                IntArrayNBT arr = ((IntArrayNBT) inbt);
                if (arr.size() != 3) return;
                Vector3i vector3i = new Vector3i(arr.get(0).getInt(), arr.get(1).getInt(), arr.get(2).getInt());
                this.positionCarved.clear();
                this.positionCarved.add(vector3i);
            }
        }
    }

    public List<Vector3i> getCarvedPositions() {
        return this.positionCarved;
    }
    public void add(Vector3i vector3i) {
        this.positionCarved.add(vector3i);
    }
}

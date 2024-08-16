package github.thelawf.gensokyoontology.common.entity.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.world.level.Level;

public class LaevateinEntity extends Entity implements IRendersAsItem {
    public LaevateinEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundTag compound) {

    }

    @Override
    protected void writeAdditional(CompoundTag compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

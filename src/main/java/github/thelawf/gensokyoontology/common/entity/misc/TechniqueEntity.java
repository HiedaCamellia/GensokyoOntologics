package github.thelawf.gensokyoontology.common.entity.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.world.level.Level;

public abstract class TechniqueEntity extends Entity {
    public TechniqueEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

}

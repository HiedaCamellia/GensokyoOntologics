package github.thelawf.gensokyoontology.common.entity.misc;

import github.thelawf.gensokyoontology.common.entity.monster.FlandreScarletEntity;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IRendersAsItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class DestructiveEyeEntity extends CollideDamageEntity {
    public final int MAX_LIVING_TICK = 50;
    public float prevScale;
    public DestructiveEyeEntity(EntityType entityType, Level worldIn) {
        super(entityType, worldIn);
        prevScale = 0.1f;
    }

    public DestructiveEyeEntity(Level worldIn) {
        super(EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get(), worldIn);
        prevScale = 0.1f;
    }


    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ticksExisted == MAX_LIVING_TICK) {
            this.world.getEntitiesWithinAABB(Player.class, this.getBoundingBox())
                    .forEach(player -> player.attackEntityFrom(DamageSource.MAGIC, 40F));
            this.remove();
        }
    }

    @Override
    protected void readAdditional(@NotNull CompoundTag compound) {
    }

    @Override
    protected void writeAdditional(@NotNull CompoundTag compound) {

    }

    @Override
    @NotNull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getMaxLiving() {
        return MAX_LIVING_TICK;
    }
}

package github.thelawf.gensokyoontology.common.entity.passive;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.passive.AnimalEntity;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public class SakeWormEntity extends AnimalEntity {
    protected SakeWormEntity(EntityType<? extends AnimalEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }
}

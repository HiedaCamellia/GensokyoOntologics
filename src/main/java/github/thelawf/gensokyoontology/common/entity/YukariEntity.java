package github.thelawf.gensokyoontology.common.entity;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityClassification;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IAngerable;
import net.minecraft.world.entity.passive.TameableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class YukariEntity extends TameableEntity implements IAngerable {
    private int angerTime;
    private UUID angerTarget;

    public static final EntityType<YukariEntity> YUKARI = EntityType.Builder.<YukariEntity>create(
                    YukariEntity::new, EntityClassification.CREATURE).updateInterval(2)
            .size(0.6f, 1.5f).trackingRange(10).build("yukari");

    protected YukariEntity(EntityType<? extends TameableEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob mate) {
        return null;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int time) {
        this.angerTime = time;
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return this.angerTarget;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        this.angerTarget = target;
    }

    @Override
    public void func_230258_H__() {

    }
}

package github.thelawf.gensokyoontology.common.entity.monster;

import github.thelawf.gensokyoontology.common.entity.AbstractHumanEntity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityClassification;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.IAngerable;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TsumiBukuroEntity extends AbstractHumanEntity implements IAngerable {

    public TsumiBukuroEntity(EntityType<? extends AbstractHumanEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int time) {

    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return null;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {

    }

    @Override
    public void func_230258_H__() {

    }
}

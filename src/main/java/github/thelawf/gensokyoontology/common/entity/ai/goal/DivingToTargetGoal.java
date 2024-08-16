package github.thelawf.gensokyoontology.common.entity.ai.goal;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DivingToTargetGoal extends Goal {

    private final LivingEntity entity;
    private final Entity target;
    private final int speed;

    public DivingToTargetGoal(LivingEntity entity, Entity target, int speed) {
        this.entity = entity;
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }
}

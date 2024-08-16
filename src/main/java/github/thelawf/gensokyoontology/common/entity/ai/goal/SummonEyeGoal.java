package github.thelawf.gensokyoontology.common.entity.ai.goal;

import github.thelawf.gensokyoontology.common.entity.misc.DestructiveEyeEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;
import java.util.stream.Collectors;

public class SummonEyeGoal extends Goal {
    protected final MobEntity entity;
    private static final int MAX_DISTANCE = 8;
    private static final int MAX_EXECUTE_TICK = 300;
    private int tickExecuted = 0;
    private Path path;

    public SummonEyeGoal(MobEntity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.entity.getAttackTarget();
        if (target == null || !target.isAlive()) return;

        this.entity.getLookController().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distance = this.entity.getDistanceSq(target);
        if (this.entity.getEntitySenses().canSee(target) && distance < MAX_DISTANCE) {
            generateEye(entity.world, target);
            ++tickExecuted;
        }
    }

    private void generateEye(Level world, LivingEntity target) {
        if (!world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) world;
            boolean canSummon = serverLevel.getEntities().filter(e -> e.getType() == EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get()).count() <= 20;

            if (canSummon) {
                for (int i = 0; i < 3; i++) {
                    DestructiveEyeEntity eye = new DestructiveEyeEntity(entity.world);
                    Vec3 vector3d = DanmakuUtil.getRandomPosWithin(MAX_DISTANCE, DanmakuUtil.Plane.XYZ).add(target.getPositionVec());
                    eye.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, 0F, 0F);
                    world.addEntity(eye);
                }
            }
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        LivingEntity target = this.entity.getAttackTarget();
        DestructiveEyeEntity eye = new DestructiveEyeEntity(entity.world);
        if (target != null) {
            eye.setLocationAndAngles(target.getPosX(), target.getPosY(), target.getPosZ(), 0F, 0F);
            this.entity.world.addEntity(eye);
        }
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity target = this.entity.getAttackTarget();
        Random random = new Random();
        if (!entity.world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) entity.world;
            long count = serverLevel.getEntities().filter(e -> e.getType() == EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get()).count();
            if (count >= 8) return false;
        }
        return this.entity.ticksExisted % 100 == 0 && target != null && target.isAlive();
    }

    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity target = this.entity.getAttackTarget();
        if (tickExecuted >= MAX_EXECUTE_TICK) {
            return false;
        }
        if (target == null || !target.isAlive()) {
            return false;
        } else {
            boolean isPlayerAndCanNotBeAttacked = target instanceof Player
                    && (target.isSpectator() || ((Player) target).isCreative());
            return !isPlayerAndCanNotBeAttacked;
        }
    }

}

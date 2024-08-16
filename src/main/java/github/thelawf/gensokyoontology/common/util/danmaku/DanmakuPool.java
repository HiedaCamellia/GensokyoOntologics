package github.thelawf.gensokyoontology.common.util.danmaku;

import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 用于优化性能的弹幕实体对象池
 */
public class DanmakuPool<D extends AbstractDanmakuEntity> extends LinkedList<D> {
    // public DanmakuPool

    public D acquire(Vec3 positionVec, Vec2 rotationVec, boolean noGravity) {

        if (removeFirst() != null) {
            D danmaku = removeFirst();
            danmaku.setNoGravity(true);
            DanmakuUtil.initDanmaku(danmaku, positionVec, rotationVec, noGravity);
        }
        return peekFirst();
    }

    public void release(D danmaku) {
        addLast(danmaku);
    }

}

package github.thelawf.gensokyoontology.common.util.danmaku;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SpellBehavior {
    @Nullable
    public Entity target;
    public Vec3 pos;
    public Vec3 motion;
    public Vec2 rotation;
    public int keyTick;

    public SpellBehavior(@Nullable Entity target, Vec3 pos, Vec3 motion, Vec2 rotation, int keyTick) {
        this.target = target;
        this.pos = pos;
        this.rotation = rotation;
        this.motion = motion;
        this.keyTick = keyTick;
    }
}

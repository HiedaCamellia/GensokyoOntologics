package github.thelawf.gensokyoontology.common.entity.misc;

import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.entity.AffiliatedEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MasterSparkEntity extends AffiliatedEntity implements IRayTraceReader {
    public MasterSparkEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MasterSparkEntity(Entity owner, Level worldIn) {
        super(EntityRegistry.MASTER_SPARK_ENTITY.get(), owner, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

    }

    private void generateRays() {
        for (int i = 0; i < 3; i++) {
            List<Vec3> initPositions = DanmakuUtil.ellipticPos(new Vec2((float) this.getPosX(), (float) this.getPosZ()), i, 10);
            List<Vec3> endPositions = initPositions.stream().map(vector3d -> getLookEnd(vector3d, this.getLookVec(), this.getEyeHeight(), 50))
                    .collect(Collectors.toList());
        }
    }
}

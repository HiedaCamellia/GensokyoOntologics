package github.thelawf.gensokyoontology.common.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SunflowerFairyEntity extends FairyEntity{
    public SunflowerFairyEntity(EntityType<? extends FairyEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void danmakuAttack(LivingEntity target) {

    }
}

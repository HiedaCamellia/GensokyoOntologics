package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.SmallStarShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GalacticArmSpellEntity extends SpellCardEntity{
    private List<SmallStarShotEntity> list;
    private static final int COUNT = 60;
    private ArrayList<List<SmallStarShotEntity>> starLists = new ArrayList<>();
    private final List<List<UUID>> idList = new ArrayList<>();
    private final float[] as = {2,3,5,7,9,11,14,18,23,25};
    private final float[] bs = {1,1.2F,2.5F,4,5,5.5F,7.2F,8,10.3F,12};

    public GalacticArmSpellEntity(Level worldIn, LivingEntity living) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(EntityRegistry.GALACTIC_ARM_SPELL_ENTITY.get(), worldIn, living);
        list = newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                SmallStarShotEntity.class, COUNT);
        for (int i = 0; i < 10; i++) {
            starLists.add(newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                    SmallStarShotEntity.class, COUNT));
        }
    }

    public GalacticArmSpellEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(entityTypeIn, worldIn);
        list = newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                SmallStarShotEntity.class, COUNT);
        for (int i = 0; i < 10; i++) {
            starLists.add(newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                    SmallStarShotEntity.class, COUNT));
        }
    }

    // 我自己都不知道为了写个这玩意儿我踩了多少坑
    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote) {
            ServerLevel serverLevel = (ServerLevel) world;
            try {
                tryShoot();
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            // trySetDanmaku(serverLevel);
        }
    }

    private void tryShoot() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        float speed = 0.06F;
        Vec3 startPos = this.getPositionVec().add(1, 0, 0);
        List<UUID> ids = new ArrayList<>();

        if (ticksExisted % 200 == 0) {
            list = newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                    SmallStarShotEntity.class, COUNT);
            for (int i = 0; i < starLists.size(); i++) {
                starLists.set(i, newDanmakuList(() -> new SmallStarShotEntity((LivingEntity) this.getOwner(), this.world, DanmakuType.STAR_SHOT_SMALL, DanmakuColor.BLUE),
                        SmallStarShotEntity.class, COUNT));
            }
        }

        for (int i = 0; i < as.length; i++) {
            float a = as[i];
            float b = bs[i];
            for (int j = 0; j < starLists.get(i).size(); j++) {
                SmallStarShotEntity entity = starLists.get(i).get(j);
                double angle = ((world.getGameTime() + j) * 0.1) % (Math.PI * 2);

                Vec3 nextPos = new Vec3(startPos.x + a * MathHelper.cos((float) angle), startPos.y,
                        startPos.z + b * MathHelper.sin((float) angle));
                double motionX = -a * MathHelper.sin((float) angle) * speed;
                double motionZ = b * MathHelper.cos((float) angle) * speed;

                setDanmakuInit(entity, startPos, new Vec2(this.rotationYaw, this.rotationPitch));
                entity.setPosition(nextPos.x, nextPos.y, nextPos.z);
                entity.setMotion(motionX, 0, motionZ);
                list.set(j, entity);
                ids.add(entity.getUniqueID());
            }
            starLists.add(list);
            idList.add(ids);
            if (starLists.get(i).size() -1 == 0) return;

            SmallStarShotEntity shot = starLists.get(i).get(GSKOMathUtil.clampPeriod(ticksExisted, 0, starLists.get(i).size() -1));
            world.addEntity(shot);
            starLists.get(i).remove(GSKOMathUtil.clampPeriod(ticksExisted, 0, starLists.get(i).size() -1));
        }
    }

    private void trySetDanmaku(ServerLevel serverLevel) {
        float speed = 0.06F;
        Vec3 startPos = this.getPositionVec().add(1, 0, 0);

        for (int i = 0; i < as.length; i++) {
            float a = as[i];
            float b = bs[i];

            for (int j = 0; j < idList.get(i).size(); j++) {
                SmallStarShotEntity entity = (SmallStarShotEntity) serverLevel.getEntityByUuid(idList.get(i).get(j));
                double angle = ((world.getGameTime() + j) * 0.1) % (Math.PI * 2);

                Vec3 nextPos = new Vec3(startPos.x + a * MathHelper.cos((float) angle), startPos.y,
                        startPos.z + b * MathHelper.sin((float) angle));
                double motionX = -a * MathHelper.sin((float) angle) * speed;
                double motionZ = b * MathHelper.cos((float) angle) * speed;

                if (entity != null) {
                    setDanmakuInit(entity, startPos, new Vec2(this.rotationYaw, this.rotationPitch));
                    entity.setMotion(motionX, 0, motionZ);
                }
            }
        }
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

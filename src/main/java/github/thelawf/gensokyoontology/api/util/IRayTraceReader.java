package github.thelawf.gensokyoontology.api.util;

import github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static github.thelawf.gensokyoontology.common.util.math.GSKOMathUtil.toDegree;

public interface IRayTraceReader {

    default List<List<AxisAlignedBB>> getRayTraceBox(Vec3 globalPos, Vec3 rayDirection, int length, float size) {
        List<List<AxisAlignedBB>> boxes = new ArrayList<>();
        List<AxisAlignedBB> aabb = new ArrayList<>();
        for (int i = 0; i < 50; i++) {

            Vec3 posRow = new Vec3(rayDirection.x > 0 ? Vector3f.XP : Vector3f.XN);
            Vec3 posColumn = new Vec3(rayDirection.z > 0 ? Vector3f.ZP : Vector3f.ZN);
            Vec3 posVertical = new Vec3(rayDirection.y > 0 ? Vector3f.YP : Vector3f.YN);

            Vec3 rayPos = globalPos.add(rayDirection);

            AxisAlignedBB aabb0 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos),
                    GSKOMathUtil.vecCeil(rayPos));
            AxisAlignedBB aabb1 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posRow)),
                    GSKOMathUtil.vecCeil(rayPos.add(posRow)));
            AxisAlignedBB aabb2 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posColumn)),
                    GSKOMathUtil.vecCeil(rayPos.add(posColumn)));
            AxisAlignedBB aabb3 = new AxisAlignedBB(GSKOMathUtil.vecFloor(rayPos.add(posVertical)),
                    GSKOMathUtil.vecCeil(rayPos.add(posVertical)));

            aabb.add(aabb0.grow(size));
            aabb.add(aabb1.grow(size));
            aabb.add(aabb2.grow(size));
            aabb.add(aabb3.grow(size));

            boxes.add(aabb);
        }
        return boxes;
    }

    default AxisAlignedBB createCubeBox(Vec3 pos, int radius) {
        return new AxisAlignedBB(pos.subtract(new Vec3(radius, radius, radius)), pos.add(new Vec3(radius, radius, radius)));
    }

    default <T extends Entity> List<T> getEntityWithin(Level worldIn, Class<? extends T> entityClass, AxisAlignedBB aabb,
                                                       @Nullable Predicate<? super T> predicate) {
        if (predicate != null) {
            return worldIn.getEntitiesWithinAABB(entityClass, aabb).stream()
                    .filter(predicate).collect(Collectors.toList());
        } else {
            return worldIn.getEntitiesWithinAABB(entityClass, aabb);
        }
    }

    /**
     * 计算线段与方形碰撞箱是否相交的检测函数
     *
     * @param start  线段起点
     * @param end    线段终点
     * @param boxMin 碰撞箱所有顶点中的最小坐标
     * @param boxMax 碰撞箱所有顶点中的最大坐标
     * @return 是否相交
     */
    default boolean isIntersecting(Vec3 start, Vec3 end, Vec3 boxMin, Vec3 boxMax) {
        // 计算射线的参数
        double tMin = (boxMin.x - start.x) / (end.x - start.x);
        double tMax = (boxMax.x - start.x) / (end.x - start.x);
        if (tMin > tMax) {
            double temp = tMin;
            tMin = tMax;
            tMax = temp;
        }
        double tyMin = (boxMin.y - start.y) / (end.y - start.y);
        double tyMax = (boxMax.y - start.y) / (end.y - start.y);
        if (tyMin > tyMax) {
            double temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }
        if ((tMin > tyMax) || (tyMin > tMax)) {
            return false;
        }
        if (tyMin > tMin) {
            tMin = tyMin;
        }
        if (tyMax < tMax) {
            tMax = tyMax;
        }
        double tzMin = (boxMin.z - start.z) / (end.z - start.z);
        double tzMax = (boxMax.z - start.z) / (end.z - start.z);

        if (tzMin > tzMax) {
            double temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
        }

        return (!(tMin > tzMax)) && (!(tzMin > tMax));
    }

    /**
     * 计算线段与方形碰撞箱是否相交的检测函数。
     *
     * @param start 线段起点
     * @param end   线段终点
     * @param aabb  碰撞箱
     * @return 是否相交
     */
    default boolean isIntersecting(Vec3 start, Vec3 end, AxisAlignedBB aabb) {
        return isIntersecting(start, end,
                new Vec3(aabb.minX, aabb.minY, aabb.minZ),
                new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    /**
     * 计算线段与方形碰撞箱是否相交的检测函数。
     *
     * @param start     线段起点
     * @param direction 玩家视线看向的方向
     * @param distance  向玩家视线方向延伸的长度
     * @param aabb      碰撞箱
     * @return 是否相交
     */
    default boolean isIntersecting(Vec3 start, Vec3 direction, double distance, AxisAlignedBB aabb) {
        return isIntersecting(start, start.add(direction).scale(distance),
                new Vec3(aabb.minX, aabb.minY, aabb.minZ),
                new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    @Nullable
    default EntityRayTraceResult rayTrace(Level worldIn, Entity entityIn, Vec3 start, Vec3 end, AxisAlignedBB boundingBox, Predicate<Entity> selector, double limitDist) {
        double currentDist = limitDist;
        Entity resultEntity = null;

        for(Entity foundEntity : worldIn.getEntitiesInAABBexcluding(entityIn, boundingBox, selector)) {
            AxisAlignedBB axisalignedbb = foundEntity.getBoundingBox().grow(0.5F);
            Optional<Vec3> optional = axisalignedbb.rayTrace(start, end);
            if (optional.isPresent()) {
                double newDist = start.squareDistanceTo(optional.get());
                if (newDist < currentDist) {
                    resultEntity = foundEntity;
                    currentDist = newDist;
                }
            }
        }

        if (resultEntity == null) {
            return null;
        } else {
            return new EntityRayTraceResult(resultEntity);
        }
    }

    default Optional<Entity> rayTrace(Level world, Entity sourceEntity, Vec3 startVec, Vec3 endVec) {
        double closestDistance = startVec.distanceTo(endVec);
        for (Entity entity : world.getEntitiesWithinAABB(Entity.class, sourceEntity.getBoundingBox().grow(startVec.distanceTo(endVec)))) {
            if (entity != sourceEntity) {
                AxisAlignedBB entityAABB = entity.getBoundingBox();
                Optional<Vec3> result = entityAABB.rayTrace(startVec, endVec);

                if (result.isPresent()) {
                    double distance = startVec.squareDistanceTo(result.get());

                    if (distance < closestDistance) {
                        return Optional.of(entity);
                    }
                }
            }
        }
        return Optional.empty();
    }

    default Vec3 getIntersectedPos(Vec3 start, Vec3 end, AxisAlignedBB aabb) {
        return getIntersectedPos(start, end,
                new Vec3(aabb.minX, aabb.minY, aabb.minZ),
                new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    default Vec3 getIntersectedPos(Vec3 start, Vec3 end, Vec3 boxMin, Vec3 boxMax) {
        // 计算射线的参数
        double tMin = (boxMin.x - start.x) / (end.x - start.x);
        double tMax = (boxMax.x - start.x) / (end.x - start.x);
        if (tMin > tMax) {
            double temp = tMin;
            tMin = tMax;
            tMax = temp;
        }
        double tyMin = (boxMin.y - start.y) / (end.y - start.y);
        double tyMax = (boxMax.y - start.y) / (end.y - start.y);
        if (tyMin > tyMax) {
            double temp = tyMin;
            tyMin = tyMax;
            tyMax = temp;
        }
        if ((tMin > tyMax) || (tyMin > tMax)) {
            return Vec3.ZERO;
        }
        if (tyMin > tMin) {
            tMin = tyMin;
        }
        if (tyMax < tMax) {
            tMax = tyMax;
        }
        double tzMin = (boxMin.z - start.z) / (end.z - start.z);
        double tzMax = (boxMax.z - start.z) / (end.z - start.z);

        if (tzMin > tzMax) {
            double temp = tzMin;
            tzMin = tzMax;
            tzMax = temp;
        }

        if ((tMin > tzMax) || (tzMin > tMax)) {
            return Vec3.ZERO;
        }

        if (tyMin > tMin) {
            tMin = tyMin;
        }
        if (tyMax < tMax) {
            tMax = tyMax;
        }

        return new Vec3(
                start.x + tMin * (end.x - start.x),
                start.y + tMin * (end.y - start.y),
                start.z + tMin * (end.z - start.z));
    }


    /**
     * 以传入的碰撞箱体的中心为圆心，获取所有位于这个球形的碰撞区域以内的生物。
     *
     * @param worldIn     世界
     * @param entityClass 生物的类
     * @param radius      球形的半径
     * @param aabb        碰撞箱
     * @return 位于球形碰撞箱内的生物的列表
     */
    default <T extends Entity> List<T> getEntityWithinSphere(Level worldIn, Class<? extends T> entityClass,
                                                             AxisAlignedBB aabb, float radius) {
        return worldIn.getEntitiesWithinAABB(entityClass, aabb).stream()
                .filter(t -> aabb.getCenter().distanceTo(t.getPositionVec()) <= radius)
                .collect(Collectors.toList());
    }

    default Vec3 getLookEnd(Vec3 startPos, Vec3 lookVec, double eyeHeight, double distance) {
        return lookVec.scale(distance).add(startPos.add(0,eyeHeight,0));
    }

    default Vec3 getAimedVec(LivingEntity source, Entity target) {
        return target.getPositionVec().subtract(source.getPositionVec());
    }

    default Vec2 toYawPitch(Vec3 vector3d) {
        double yaw = Math.atan2(-vector3d.x, vector3d.z);
        double pitch = Math.atan2(vector3d.y, Math.sqrt(vector3d.x * vector3d.x + vector3d.z * vector3d.z));
        return new Vec2((float) toDegree(yaw), (float) toDegree(pitch));
    }

    /**
     * 以传入的碰撞箱体的中心为圆心，获取所有位于这个球形的碰撞区域以内，以及同时满足其它条件的生物。
     *
     * @param worldIn     世界
     * @param entityClass 生物的类
     * @param radius      球形的半径
     * @param predicate   其它必要条件
     * @param aabb        碰撞箱
     * @return 位于球形碰撞箱内且满足其它条件的所有生物的列表
     */
    default <T extends Entity> List<T> getEntityWithinSphere(Level worldIn, Class<? extends T> entityClass,
                                                             Predicate<? super T> predicate, AxisAlignedBB aabb, float radius) {
        return worldIn.getEntitiesWithinAABB(entityClass, aabb).stream()
                .filter(t -> aabb.getCenter().distanceTo(t.getPositionVec()) <= radius && predicate.test(t))
                .collect(Collectors.toList());
    }
}

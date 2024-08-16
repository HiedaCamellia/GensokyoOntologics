package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.HeartShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManiaDepressEntity extends SpellCardEntity {

    public ManiaDepressEntity(Level worldIn, Player player) {
        super(EntityRegistry.MANIA_DEPRESS_ENTITY.get(), worldIn, player);
    }

    public ManiaDepressEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        List<Vec3> pinkPositions = DanmakuUtil.getHeartLinePos(0.3f, 0.11);
        List<Vec3> aquaPositions = DanmakuUtil.getHeartLinePos(1.8f, 0.11);
        int shootInterval = 15;
        int ratio = ticksExisted / shootInterval;
        pinkPositions = DanmakuUtil.getRotatedPos(pinkPositions, (float) Math.PI / 12 * ratio, 0f);
        aquaPositions = DanmakuUtil.getRotatedPos(aquaPositions, (float) Math.PI / 2, 0f);
        aquaPositions = DanmakuUtil.getRotatedPos(aquaPositions, (float) Math.PI / 6 * ratio, 0f);

        if (ticksExisted % shootInterval == 0) {

            for (Vec3 vector3d : pinkPositions) {
                CompoundTag nbt = new CompoundTag();
                nbt.putInt("color", DanmakuColor.PINK.ordinal());
                HeartShotEntity heartShotPink = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbt);

                Vec3 shootVec = new Vec3(vector3d.x, vector3d.y, vector3d.z);
                vector3d = vector3d.add(this.getPositionVec());

                setDanmakuInit(heartShotPink, vector3d, new Vec2((float) vector3d.x, (float) vector3d.y));
                heartShotPink.shoot(shootVec.x, shootVec.y, shootVec.z, 0.55f, 0f);
                world.addEntity(heartShotPink);
            }

            for (Vec3 vector3d : aquaPositions) {
                CompoundTag nbt = new CompoundTag();
                nbt.putInt("color", DanmakuColor.AQUA.ordinal());

                HeartShotEntity heartShotAqua = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbt);

                Vec3 shootVec = new Vec3(vector3d.x, vector3d.y, vector3d.z);
                vector3d = vector3d.add(this.getPositionVec());

                setDanmakuInit(heartShotAqua, vector3d, new Vec2((float) vector3d.x, (float) vector3d.y));
                heartShotAqua.shoot(-shootVec.x, -shootVec.y, -shootVec.z, 0.3f, 0f);
                world.addEntity(heartShotAqua);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        return ItemStack.EMPTY;// new ItemStack(ItemRegistry.SC_MANIA_DEPRESS.get());
    }
}

package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.HeartShotEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.nbt.BehaviorFunctions;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class SuperEgoSpellEntity extends SpellCardEntity {

    public SuperEgoSpellEntity(Level worldIn, Player player) {
        super(EntityRegistry.SUPER_EGO_SPELL_ENTITY.get(), worldIn, player);
    }

    public SuperEgoSpellEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        //Player player = this.world.getPlayerByUuid(this.dataManager.get(DATA_OWNER_UUID).get());
        Vec3 center = new Vec3(Vector3f.XP);

        if (ticksExisted % 5 == 0) return;
        for (int i = 0; i < 6; i++) {
            Vec3 clockwise = center.add(120, 0, 0).rotateYaw((float) Math.PI * 2 / 6 * i);
            Vec3 counterClockwise = center.add(120, 0, 0).rotateYaw((float) -Math.PI * 2 / 6 * i);

            clockwise = clockwise.rotateYaw((float) (Math.PI / 180 * ticksExisted));
            counterClockwise = counterClockwise.rotateYaw((float) -Math.PI / 180 * ticksExisted);
            Vec3 clockShoot = clockwise.normalize().inverse();
            Vec3 counterClockShoot = counterClockwise.normalize().inverse();

            CompoundTag nbt = initColor(DanmakuColor.AQUA);
            CompoundTag nbtCounter = initColor(DanmakuColor.AQUA);
            applyFunc(clockwise, nbt);
            applyFunc(counterClockwise, nbtCounter);

            HeartShotEntity heartShot = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbt);
            HeartShotEntity heartCounter = new HeartShotEntity((LivingEntity) this.getOwner(), world, nbtCounter);
            setDanmakuInit(heartShot, clockwise.add(this.getPositionVec()), new Vec2((float) clockShoot.x, (float) clockShoot.z));
            setDanmakuInit(heartCounter, counterClockwise.add(this.getPositionVec()), new Vec2((float) counterClockShoot.x, (float) counterClockShoot.z));

            heartShot.shoot(clockShoot.x, clockShoot.y, clockShoot.z, 0.6f, 0f);
            heartCounter.shoot(counterClockShoot.x, counterClockShoot.y, counterClockShoot.z, 0.6f, 0f);
            world.addEntity(heartShot);
            world.addEntity(heartCounter);
        }
    }

    private void applyFunc(Vec3 motion, CompoundTag script) {
        ListNBT list = new ListNBT();
        CompoundTag behavior = new CompoundTag();
        motion = motion.rotateYaw((float) -Math.PI / 200 * 199).scale(0.1);

        ListNBT params = newDoubleNBTList(motion.x, motion.y, motion.z);
        behavior.put(BehaviorFunctions.ADD_MOTION, params);
        list.add(behavior);

        script.putString("type", "keyTickBehavior");
        script.put("behaviors", list);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SPELL_CARD_BLANK.get());
    }
}

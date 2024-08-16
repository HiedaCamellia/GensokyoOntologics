package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.api.entity.ISpellCardUser;
import github.thelawf.gensokyoontology.common.entity.projectile.AbstractDanmakuEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.RiceShotEntity;
import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import net.minecraft.world.entity.EntityClassification;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FourOfAKindEntity extends SpellCardEntity {

    private int userIndex = 0;

    public static final DataParameter<Integer> DATA_INDEX = EntityDataManager.createKey(FourOfAKindEntity.class, DataSerializers.VARINT);

    public static final EntityType<FourOfAKindEntity> FOUR_OF_A_KIND =
            EntityType.Builder.<FourOfAKindEntity>create(FourOfAKindEntity::new, EntityClassification.MISC)
                    .size(1F, 1F).trackingRange(4).updateInterval(2).build("four_of_a_kind");

    public FourOfAKindEntity(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(FOUR_OF_A_KIND, worldIn);
    }

    public FourOfAKindEntity(Level worldIn, LivingEntity living, int index) {
        super(FOUR_OF_A_KIND, worldIn, living);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_INDEX, this.userIndex);
    }

    public void onTick(Level world, LivingEntity user, int ticksIn) {

        switch (this.dataManager.get(DATA_INDEX)) {
            case 0:
                break;
            case 1:
                SmallShotEntity smallShot = new SmallShotEntity(user, user.world, DanmakuType.SMALL_SHOT, DanmakuColor.BLUE);
                shootSpherical(smallShot, user);
                break;
            case 2:
                RiceShotEntity riceShot = new RiceShotEntity(user, user.world, DanmakuType.RICE_SHOT, DanmakuColor.RED);
                shootSpherical(riceShot, user);
                break;
        }
    }

    private <D extends AbstractDanmakuEntity> void shootSpherical(D danmaku, LivingEntity user) {
        Vec3 vector3d = new Vec3(Vector3f.ZP).scale(2);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {

                vector3d = vector3d.rotatePitch((float) (Math.PI / 12 * i));
                vector3d = vector3d.rotateYaw((float) (Math.PI / 12 * j));

                DanmakuUtil.shootDanmaku(this.world, user, danmaku, vector3d, 0.4f, 0f);
                DanmakuUtil.shootDanmaku(this.world, user, danmaku, vector3d.inverse(), 0.4f, 0f);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.onTick(this.world, (LivingEntity) this.getOwner(), ticksExisted);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }
}

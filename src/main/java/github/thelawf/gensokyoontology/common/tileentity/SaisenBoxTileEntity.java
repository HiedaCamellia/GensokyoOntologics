package github.thelawf.gensokyoontology.common.tileentity;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.util.BlessType;
import github.thelawf.gensokyoontology.core.init.EffectRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SaisenBoxTileEntity extends BlockEntity implements ITickableTileEntity, IRayTraceReader {
    private int count;
    private UUID ownerId;
    private UUID throwerId;
    public int ticks = 0;
    private static final List<Pair<Integer, BlessType>> BLESS_LIST = ImmutableList.of(
            Pair.of(50, BlessType.IMMUNE_POISON),
            Pair.of(100, BlessType.IMMUNE_BLOODY_MIST));
    public SaisenBoxTileEntity() {
        super(TileEntityRegistry.SAISEN_BOX_TILE_ENTITY.get());
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundTag nbt) {
        if (nbt.contains("count")) {
            this.count = nbt.getInt("count");
        }
        super.read(state, nbt);
    }

    @Override
    @NotNull
    public CompoundTag write(@NotNull CompoundTag compound) {
        super.write(compound);
        compound.putInt("count", this.count);
        return compound;
    }

    @Override
    public void tick() {
        AxisAlignedBB aabb = new AxisAlignedBB(getPos().up());

        if (world != null) {
            List<ItemEntity> itemEntities = world.getEntitiesWithinAABB(ItemEntity.class, aabb, EntityPredicates.IS_ALIVE).stream()
                    .filter(itemEntity -> itemEntity.getItem().getItem() == ItemRegistry.SILVER_COIN.get())
                    .collect(Collectors.toList());

            itemEntities.forEach(this::tryApplyBless);
            this.addCoinCount(itemEntities.size());
            markDirty();
        }
        ticks++;
    }

    public void addCoinCount(int count){
        this.count += count;
    }

    private void tryApplyBless(ItemEntity itemEntity) {
        if (itemEntity.getThrowerId() == null || !(world instanceof ServerLevel)) return;

        ServerLevel serverLevel = (ServerLevel) world;
        if (serverLevel.getEntityByUuid(itemEntity.getThrowerId()) instanceof Player) {
            Player player = (Player) serverLevel.getEntityByUuid(itemEntity.getThrowerId());
            if (player == null) return;
            testCount(player);
            itemEntity.getItem().shrink(itemEntity.getItem().getCount());
        }
    }

    public int getCount() {
        return this.count;
    }

    public void testCount(Player player) {
        for (int i = 0; i < BLESS_LIST.size(); i++) {
            if (this.getCount() >= BLESS_LIST.get(i).getFirst()) {
                int duration = 6000 + 500 * i;
                player.addPotionEffect(new EffectInstance(EffectRegistry.HAKUREI_BLESS_EFFECT.get(), duration, i));
                return;
            }
        }
    }
}

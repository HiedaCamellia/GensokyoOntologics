package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.api.util.IRayTraceReader;
import github.thelawf.gensokyoontology.common.block.decoration.ClayAdobeBlock;
import github.thelawf.gensokyoontology.common.tileentity.AdobeTileEntity;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class KeikiChisel extends Item implements IRayTraceReader {
    public KeikiChisel(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        onAdobeCarved(context);
        return super.onItemUse(context);
    }

    public void onAdobeCarved(@NotNull ItemUseContext context) {
        if (context.level().getBlockState(context.getPos()).getBlock() == BlockRegistry.CLAY_ADOBE_BLOCK.get() &&
                context.level() instanceof ServerLevel && context.getPlayer() != null) {
            ServerLevel serverLevel = (ServerLevel) context.level();
            BlockPos pos = context.getPos();
            ClayAdobeBlock adobe = (ClayAdobeBlock) serverLevel.getBlockState(pos).getBlock();
            AdobeTileEntity tileEntity = adobe.getTileEntity(serverLevel, pos);
            tileEntity.add(getIntersectedCurvature(context.getPlayer(), serverLevel, pos, tileEntity));
        }

    }

    public void onTombClicked(@NotNull ItemUseContext context) {

    }

    public Vector3i getIntersectedCurvature(Player player, ServerLevel serverLevel, BlockPos pos, AdobeTileEntity tileEntity) {
        Vec3 start = player.getPositionVec();
        Vec3 end = getLookEnd(start, player.getLookVec(), player.getEyeHeight(), 5);
        AxisAlignedBB aabb = new AxisAlignedBB(pos);
        Vec3 intersected = getIntersectedPos(start, end, aabb);
        ChunkPos chunkPos = serverLevel.getChunk(pos).getPos();
        return new Vector3i(((int) intersected.x * 16) >> chunkPos.x, intersected.y * 16 - pos.getY(), ((int) intersected.z * 16) >> chunkPos.z);
    }
}

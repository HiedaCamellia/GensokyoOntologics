package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.api.util.INBTRunnable;
import github.thelawf.gensokyoontology.api.util.INBTWriter;
import github.thelawf.gensokyoontology.common.block.GapBlock;
import github.thelawf.gensokyoontology.common.tileentity.GapTileEntity;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.network.chat.Component;


import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.util.List;

public class GapItem extends BlockItem implements INBTWriter, INBTRunnable {
    public GapItem(Properties properties) {
        super(BlockRegistry.GAP_BLOCK.get(), properties);
    }

    @Override
    protected boolean onBlockPlaced(@NotNull BlockPos pos, @NotNull Level worldIn, @Nullable Player player, ItemStack stack, BlockState state) {

        if (player == null) return super.onBlockPlaced(pos, worldIn, player, stack, state);

        if (player.getHeldItemMainhand().getItem() == this) {

            if (stack.hasTag()) {
                CompoundTag nbt = stack.getTag();
                // setBlockTileSecond(player, worldIn, pos, nbt);
                stack.setTag(new CompoundTag());
                stack.shrink(1);
                return super.onBlockPlaced(pos, worldIn, player, stack, state);

            }
        }

        // setBlockTileFirst(worldIn, player, pos);
        return super.onBlockPlaced(pos, worldIn, player, stack, state);
    }


    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getTag() != null && stack.getTag().contains("first_pos")) {
            CompoundTag nbt = stack.getTag();
            if (nbt.contains("first_pos")) {
                BlockPos pos = BlockPos.fromLong(nbt.getLong("first_pos"));
                tooltip.add(Component.literal("第一处隙间设置为: " + pos.getCoordinatesAsString()));
            }
            if (nbt.contains("departure_world")) {
                tooltip.add(Component.translatable(nbt.getString("departure_world")));
            }
            if (nbt.contains("is_first_placement")) {
                tooltip.add(Component.literal("是否是第一次点击：" + nbt.getBoolean("is_first_placement")));
            }
        }
    }
}

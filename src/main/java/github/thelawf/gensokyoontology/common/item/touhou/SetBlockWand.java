package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SetBlockWand extends Item {

    BlockState blockState = Blocks.STONE.getDefaultState();
    Mode mode = Mode.CIRCLE;
    CompoundTag nbt = new CompoundTag();

    public SetBlockWand(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (Screen.hasShiftDown()) {
            blockState = context.level().getBlockState(context.getPos());
        }

        ItemStack itemStack = Objects.requireNonNull(context.getPlayer()).getHeldItemMainhand();

        if (!context.level().isRemote) {
            BlockPos pos = context.getPos(); // 获取玩家所在的坐标

            if (this.mode == Mode.CIRCLE && itemStack.getTag() != null &&
                    itemStack.getTag().contains("centerX") &&
                    itemStack.getTag().contains("centerY") &&
                    itemStack.getTag().contains("centerZ")) { // 如果物品没有标签，就设置圆心

                nbt.putInt("centerX", pos.getX());
                nbt.putInt("centerY", pos.getY());
                nbt.putInt("centerZ", pos.getZ());
                itemStack.setTag(nbt);
                context.getPlayer().sendMessage(Component.translatable("set_block_item.message.set_center",
                        pos.getX(), pos.getY(), pos.getZ()), context.getPlayer().getUniqueID());
            } else { // 如果物品已经有标签，就设置半径
                CompoundTag nbt = itemStack.getTag();
                int centerX, centerY, centerZ, radius;

                if (nbt != null && nbt.contains("centerX") &&
                        nbt.contains("centerY") && nbt.contains("centerZ")) {
                    centerX = nbt.getInt("centerX");
                    centerY = nbt.getInt("centerY");
                    centerZ = nbt.getInt("centerZ");
                    radius = (int) Math.sqrt(Math.pow(pos.getX() - centerX, 2) +
                            Math.pow(pos.getY() - centerY, 2) + Math.pow(pos.getZ() - centerZ, 2));
                    nbt.putInt("radius", radius);

                    itemStack.setTag(nbt);
                    context.getPlayer().sendMessage(Component.translatable("item.message.set_radius", radius), context.getPlayer().getUniqueID());
                    placeCircleBlock(new BlockPos(centerX, centerY, centerZ), radius);

                    nbt.remove("centerX");
                    nbt.remove("centerY");
                    nbt.remove("centerZ");
                    nbt.remove("radius");
                    itemStack.setTag(nbt);
                }

            }

            if (this.mode == Mode.BEZIER_CURVE && itemStack.getTag() == null) {

                nbt.putInt("startX", pos.getX());
            }
        }
        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(Level worldIn, Player playerIn, Hand handIn) {
        if (Screen.hasAltDown()) {
            switch (mode) {
                case CIRCLE:
                    mode = Mode.ECLIPSE;
                    this.nbt = GSKONBTUtil.removeAllChildNBT(nbt);
                    break;
                case ECLIPSE:
                    mode = Mode.RECTANGLE;
                    this.nbt = GSKONBTUtil.removeAllChildNBT(nbt);
                    break;
                case RECTANGLE:
                    mode = Mode.BEZIER_CURVE;
                    this.nbt = GSKONBTUtil.removeAllChildNBT(nbt);
                    break;
                case BEZIER_CURVE:
                    mode = Mode.CIRCLE;
                    this.nbt = GSKONBTUtil.removeAllChildNBT(nbt);
                    break;
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip." +
                GensokyoOntology.MODID + "set_block_wand.mode"));
        tooltip.add(Component.translatable("tooltip." +
                GensokyoOntology.MODID + "set_block_wand.key_hint"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public enum Mode {
        CIRCLE("circle_mode"),
        ECLIPSE("eclipse_mode"),
        RECTANGLE("rectangle_mode"),
        BEZIER_CURVE("bezier_mode");

        public final String id;

        Mode(String id) {
            this.id = id;
        }
    }

    public void placeCircleBlock(BlockPos posIn, int radius) {

    }

    public void placeBezierCurveBlock() {

    }
}

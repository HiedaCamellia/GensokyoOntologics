package github.thelawf.gensokyoontology.common.item.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ScriptBuilderItem extends Item {
    public static final String TYPE_HIGHLIGHT = "§6";    /// 金色 ///
    public static final String NAME_HIGHLIGHT = "§d";    /// 粉色 ///
    public static final String VALUE_HIGHLIGHT = "§a";   /// 浅绿 ///
    public static final String STRING_HIGHLIGHT = "§b";  /// 天蓝色 ///
    public static final String EXCEPTION_HIGHLIGHT = "§c";  /// 红色 ///
    public static final String RESET_HIGHLIGHT = "§r";    /// 重置 ///

    public static final Component FILED_TYPE_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_type");
    public static final Component FILED_NAME_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_name");
    public static final Component FILED_VALUE_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_value");
    public ScriptBuilderItem() {
        super(new Item.Properties());
    }

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        if (worldIn.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) worldIn;
            ServerPlayer serverPlayer = (ServerPlayer) playerIn;
            this.openScriptEditGUI(serverLevel, serverPlayer, playerIn.getHeldItem(handIn));
        }
        this.openScriptEditGUI(worldIn, playerIn, playerIn.getHeldItem(handIn));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (stack.getTag() != null) {
            CompoundTag nbt = stack.getTag();
            tooltip.add(FILED_TYPE_TIP);
            tooltip.add(Component.literal(TYPE_HIGHLIGHT + nbt.getString("type")));
            tooltip.add(FILED_NAME_TIP);
            tooltip.add(Component.literal(NAME_HIGHLIGHT + nbt.getString("name")));
            tooltip.add(FILED_VALUE_TIP);
            if (GSKONBTUtil.containsPrimitiveType(nbt)) {
                tooltip.add(Component.literal(VALUE_HIGHLIGHT + GSKONBTUtil.getFromValue(nbt).getString()));
            }
            else if (GSKONBTUtil.containsAllowedType(nbt)) {
                GSKONBTUtil.getMemberValues(nbt).forEach(s -> tooltip.add(Component.literal(s)));
            }
        }
    }

    public abstract void openScriptEditGUI(Level serverLevel, Player serverPlayer, ItemStack stack);
}

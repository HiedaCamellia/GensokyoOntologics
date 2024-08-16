package github.thelawf.gensokyoontology.common.item.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.nbt.GSKONBTUtil;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ScriptReadOnlyItem extends Item {
    public static final String TYPE_HIGHLIGHT = "§6";    /// 金色 ///
    public static final String NAME_HIGHLIGHT = "§d";    /// 粉色 ///
    public static final String VALUE_HIGHLIGHT = "§a";   /// 浅绿 ///
    public static final String STRING_HIGHLIGHT = "§b";  /// 天蓝色 ///
    public static final String EXCEPTION_HIGHLIGHT = "§c";  /// 红色 ///
    public static final String RESET_HIGHLIGHT = "§r";    /// 重置 ///
    public static final Component FILED_TYPE_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_type");
    public static final Component FILED_NAME_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_name");
    public static final Component FILED_VALUE_TIP = GensokyoOntology.withTranslation("tooltip.",".script_builder.field_value");
    public ScriptReadOnlyItem() {
        super(new Properties());
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        addReadOnlyData(level, player, player.getUseItem());
        return super.use(level, player, usedHand);
    }

    public abstract void addReadOnlyData(Level world, Player player, ItemStack stack);

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
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
}

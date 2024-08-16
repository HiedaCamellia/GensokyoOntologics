package github.thelawf.gensokyoontology.common.item.script;

import com.mojang.serialization.Dynamic;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.itemtab.GSKOItemTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class DynamicScriptItem extends Item {
    public static final String TYPE_HIGHLIGHT = "§b";    /// 浅蓝 ///
    public static final String EXCEPTION_HIGHLIGHT = "§c";  /// 红色 ///
    public static final Component OPERATION_TYPE_TIP = GensokyoOntology.withTranslation("tooltip.",".binary_builder.operation_type");
    public static final Component LEFT_TYPE_TIP = GensokyoOntology.withTranslation("tooltip.",".binary_builder.left_type");
    public static final Component RIGHT_TYPE_TIP = GensokyoOntology.withTranslation("tooltip.",".binary_builder.right_type");
    public DynamicScriptItem() {
        super(new Properties());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        // this.addDynamicData(worldIn, playerIn, playerIn.getHeldItem(handIn), Dynamic.);
        openScriptEditGUI(worldIn, playerIn, playerIn.getHeldItem(handIn));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public abstract void addDynamicData(Level world, Player player, ItemStack stack, Dynamic<INBT> dynamic);

    public abstract void openScriptEditGUI(Level world, Player player, ItemStack stack);

}

package github.thelawf.gensokyoontology.common.container.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.item.script.DynamicScriptItem;
import github.thelawf.gensokyoontology.common.item.script.ScriptBuilderItem;
import github.thelawf.gensokyoontology.common.item.script.ScriptReadOnlyItem;
import github.thelawf.gensokyoontology.core.init.ContainerRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

// 31, 21
// 140, 21
public class V3dInvokerContainer extends FunctionInvokerContainer {
    public final IInventory inventory = new Inventory(3);
    public static final Component NAME = Component.translatable("container." +
            GensokyoOntology.MODID + ".v3d_invoker.title");
    public V3dInvokerContainer(int id, Inventory playerInventory) {
        super(ContainerRegistry.V3D_INVOKER_CONTAINER.get(), playerInventory, id);
        this.addInventorySlots(13, 81);
        this.addSlots(this.inventory, 0, 21, 21);
        this.addSlots(this.inventory, 1, 39, 21);
        this.addSlots(this.inventory, 2, 148, 21);
    }

    private void addSlots(IInventory inventory, int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x, y){
            @Override
            public boolean isItemValid(@NotNull ItemStack stack) {
                return index == 0 ? stack.getItem() == ItemRegistry .V3D_BUILDER.get() :
                        index == 2 ? stack.getItem() == ItemRegistry.V3D_INVOKER.get() :
                                stack.getItem() instanceof ScriptBuilderItem ||
                                stack.getItem() instanceof DynamicScriptItem ||
                                stack.getItem() instanceof ScriptReadOnlyItem;
            }
        });
    }

    @Override
    public void onContainerClosed(@NotNull Player playerIn) {
        super.onContainerClosed(playerIn);
        this.clearContainer(playerIn, playerIn.world, this.inventory);
    }

    public static INamedContainerProvider create() {
        return new INamedContainerProvider() {
            @Override
            @NotNull
            public Component getDisplayName() {
                return NAME;
            }
            @NotNull
            @Override
            public Container createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player p_createMenu_3_) {
                return new V3dInvokerContainer(windowId, playerInventory);
            }
        };
    }
}

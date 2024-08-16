package github.thelawf.gensokyoontology.common.container.script;

import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ILevelPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AssignValueContainer extends ScriptBuilderContainer {

    private final IItemHandler playerInventory;
    private final ILevelPosCallable POS_CALLABLE = ILevelPosCallable.DUMMY;
    private final IInventory assignedValue = new Inventory(1);

    protected AssignValueContainer(@Nullable ContainerType<?> type, int id, Player player) {
        super(type, player.inventory, id);
        this.playerInventory = new InvWrapper(player.inventory);
        // addSlot(new SlotItemHandler(playerInventory))
    }

    @Override
    public boolean canInteractWith(@NotNull Player playerIn) {
        return true;
    }
}

package github.thelawf.gensokyoontology.common.container.script;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.container.ContainerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReferenceContainer extends ScriptBuilderContainer{
    public ReferenceContainer(@Nullable ContainerType<?> type, Inventory playerInventory, int id) {
        super(type, playerInventory, id);
    }

    @Override
    public boolean canInteractWith(@NotNull Player playerIn) {
        return true;
    }
}

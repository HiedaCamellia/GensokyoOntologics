package github.thelawf.gensokyoontology.common.container.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.ContainerRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

public class CBContainer extends OneSlotContainer{
    public CBContainer(int id, Inventory playerInventory) {
        super(ContainerRegistry.CB_CONTAINER.get(), id, playerInventory);
    }

    public static INamedContainerProvider create(String title) {
        return new INamedContainerProvider() {
            @Override
            @NotNull
            public Component getDisplayName() {
                return Component.translatable("container." + GensokyoOntology.MODID + "." + title +".title");
            }
            @NotNull
            @Override
            public Container createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player p_createMenu_3_) {
                return new CBContainer(windowId, playerInventory);
            }
        };
    }
}

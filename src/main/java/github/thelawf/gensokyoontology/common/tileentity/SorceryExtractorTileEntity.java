package github.thelawf.gensokyoontology.common.tileentity;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.container.SorceryExtractorContainer;
import github.thelawf.gensokyoontology.core.RecipeRegistry;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import github.thelawf.gensokyoontology.data.recipe.SorceryExtractorRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class SorceryExtractorTileEntity extends BlockEntity implements ITickableTileEntity {
    private final int slotCount = 5;
    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<IItemHandler> optionalHandler = LazyOptional.of(() -> itemHandler);
    public static final Component CONTAINER_NAME = Component.translatable("container." +
            GensokyoOntology.MODID + ".sorcery_extractor.title");

    public SorceryExtractorTileEntity() {
        super(TileEntityRegistry.SORCERY_EXTRACTOR_TILE_ENTITY.get());
    }

    public static INamedContainerProvider createContainer(Level worldIn, BlockPos posIn) {
        return new INamedContainerProvider() {
            @Override
            @NotNull
            public Component getDisplayName() {
                return CONTAINER_NAME;
            }

            @Override
            public Container createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new SorceryExtractorContainer(windowId, worldIn, posIn, playerInventory);
            }
        };
    }

    @Override
    public void read(@NotNull BlockState state, CompoundTag nbt) {
        this.itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    @NotNull
    public CompoundTag write(CompoundTag compound) {
        compound.put("inv", this.itemHandler.serializeNBT());
        return super.write(compound);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(this.slotCount) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                if (slot >= 0 && slot < 25) {
                    return 1;
                } else {
                    return super.getSlotLimit(slot);
                }
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return optionalHandler.cast();
        return super.getCapability(cap, side);
    }

    public void checkCraft() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots() - 1; i++)
            inv.setInventorySlotContents(i, itemHandler.getStackInSlot(i));

        Optional<SorceryExtractorRecipe> recipe = world.getRecipeManager().getRecipe(RecipeRegistry.SORCERY_RECIPE, inv, world);
        recipe.ifPresent(this::doCraft);
    }

    public void doCraft(SorceryExtractorRecipe recipe) {
        itemHandler.insertItem(4, recipe.getRecipeOutput(), false);
        markDirty();
    }

    @Override
    public void tick() {
        if (world != null && world.isRemote) return;
        checkCraft();
    }
}

package github.thelawf.gensokyoontology.common.tileentity;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.container.DanmakuCraftingContainer;
import github.thelawf.gensokyoontology.core.RecipeRegistry;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import github.thelawf.gensokyoontology.core.init.TileEntityRegistry;
import github.thelawf.gensokyoontology.data.recipe.DanmakuRecipe;
import github.thelawf.gensokyoontology.data.recipe.SorceryExtractorRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ICraftingRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DanmakuTabelTileEntity extends BlockEntity implements ITickableTileEntity {
    public DanmakuTabelTileEntity() {
        super(TileEntityRegistry.DANMAKU_TABLE_TILE.get());
    }

    private final ItemStackHandler itemHandler = createItemHandler();
    private final LazyOptional<IItemHandler> optionalHandler = LazyOptional.of(() -> itemHandler);
    public static final Component CONTAINER_NAME = Component.translatable("container." +
            GensokyoOntology.MODID + ".danmaku_craft.title");

    public static INamedContainerProvider createContainer(Level worldIn, BlockPos posIn) {
        return new INamedContainerProvider() {
            @Override
            public Component getDisplayName() {
                return CONTAINER_NAME;
            }

            @Nullable
            @Override
            public Container createMenu(int winwdowId, Inventory playerInventory, Player player) {
                return new DanmakuCraftingContainer(winwdowId, playerInventory);
            }
        };
    }

    @Override
    public void read(@NotNull BlockState state, CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    @NotNull
    public CompoundTag write(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(29) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {

                if (slot >= 0 && slot < 25) {
                    return stack.getItem() == ItemRegistry.DANMAKU_SHOT.get();
                }
                return super.isItemValid(slot, stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                if (slot >= 0 && slot < 25) {
                    return 1;
                } else {
                    return super.getSlotLimit(slot);
                }
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return optionalHandler.cast();
        }
        return super.getCapability(cap);
    }

    public void checkCraft() {
        // CraftingInventory inv = new CraftingInventory((Container) createContainer(world, pos), 5, 5);
        // for (int i = 0; i < itemHandler.getSlots(); i++) {
        //     inv.setInventorySlotContents(i, itemHandler.getStackInSlot(i));
        // }
        // if (world == null) return;

        // Optional<DanmakuRecipe> recipe = world.getRecipeManager().getRecipe(RecipeRegistry.DANMAKU_RECIPE, inv, world);
        // recipe.ifPresent(iRecipe -> craft(world, inv.getSizeInventory()));

        markDirty();
    }

    private void craft(Level world, CraftingInventory inventory, CraftResultInventory inventoryResult) {
        if (!world.isRemote) {
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<DanmakuRecipe> optional = world.getServer().getRecipeManager().getRecipe(RecipeRegistry.DANMAKU_RECIPE, inventory, world);
            if (optional.isPresent()) {
                ICraftingRecipe icraftingrecipe = optional.get();
                itemstack = icraftingrecipe.getCraftingResult(inventory);

            }
            inventoryResult.setInventorySlotContents(0, itemstack);
        }

    }

    @Override
    public void tick() {
        checkCraft();
    }
}

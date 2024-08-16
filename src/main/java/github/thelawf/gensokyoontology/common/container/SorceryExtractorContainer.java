package github.thelawf.gensokyoontology.common.container;

import com.mojang.datafixers.util.Pair;
import github.thelawf.gensokyoontology.common.tileentity.SorceryExtractorTileEntity;
import github.thelawf.gensokyoontology.common.util.client.GSKOGUIUtil;
import github.thelawf.gensokyoontology.core.init.ContainerRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ILevelPosCallable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 各个槽位的位置：<br>
 * 上方槽位：(98, 11)<br>
 * 左边槽位：(53, 55)<br>
 * 【合成结果槽位】中间槽位：(98, 55)<br>
 * 右边槽位：(143, 48)<br>
 * 下方槽位：(98, 100)<br>
 */
public class SorceryExtractorContainer extends Container {
    private final IItemHandler playerInventory;

    public static final Logger LOGGER = LogManager.getLogger();
    public static final List<List<ItemStack>> RECIPES = GSKOGUIUtil.makeRecipes();

    private final ILevelPosCallable POS_CALLABLE = ILevelPosCallable.DUMMY;
    private final TileEntity tileEntity;

    private final IInventory ingredientInventory = new Inventory(5);
    private final IInventory resultInventory = new Inventory(1);

    public SorceryExtractorContainer(int windowId, Level world, BlockPos blockPos, Inventory playerInventory) {
        super(ContainerRegistry.SORCERY_EXTRACTOR_CONTAINER.get(), windowId);
        this.playerInventory = new InvWrapper(playerInventory);
        this.tileEntity = world.getTileEntity(blockPos);
        addInventorySlots(28, 128);

        // addSlot(new Slot(this.ingredientInventory, 0, 71, 4));
        // addSlot(new Slot(this.ingredientInventory, 1, 26, 48));
        // addSlot(new Slot(this.ingredientInventory, 2, 116, 48));
        // addSlot(new Slot(this.ingredientInventory, 3, 71, 93));

        if (this.tileEntity != null) {
            this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                addContainerSlot(itemHandler, 0, 99, 12);
                addContainerSlot(itemHandler, 1, 54, 56);
                addContainerSlot(itemHandler, 2, 145, 56);
                addContainerSlot(itemHandler, 3, 99, 101);
                addContainerSlot(itemHandler, 4, 99, 56);
            });
        }
    }


    private static SorceryExtractorTileEntity getTileEntity(@NotNull Inventory inv, @NotNull PacketBuffer buf) {
        final TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
        if (te instanceof SorceryExtractorTileEntity) {
            return (SorceryExtractorTileEntity) te;
        }
        return new SorceryExtractorTileEntity();
    }

    @Override
    public void onContainerClosed(@NotNull Player playerIn) {
        super.onContainerClosed(playerIn);
        this.clearContainer(playerIn, playerIn.world, this.ingredientInventory);
    }

    @Override
    public boolean canInteractWith(@NotNull Player playerIn) {
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        super.onCraftMatrixChanged(inventoryIn);

    }

    private void addContainerSlot(IItemHandler itemHandler, int index, int xPos, int yPos) {
        addSlot(new SlotItemHandler(itemHandler, index, xPos, yPos){
            @Override
            public ItemStack onTake(Player playerEntity, ItemStack stack) {
                if (this.getSlotIndex() == 4) for (int i = 0; i < 4; i++) this.getItemHandler().extractItem(i, 1, false);
                else this.getItemHandler().extractItem(4, 1, false);
                return super.onTake(playerEntity, stack);
            }
        });
    }

    private void addResultSlot(IItemHandler itemHandler, int index, int xPos, int yPos) {
        addSlot(new SlotItemHandler(itemHandler, index, xPos, yPos));
    }

    private void addIngredientSlotOld(IInventory inventory, int index, int xPos, int yPos) {
        addSlot(new Slot(inventory, index, xPos, yPos) {
            @Override
            public void onSlotChanged() {
                super.onSlotChanged();

                for (List<ItemStack> recipe : RECIPES) {
                    // LOGGER.info("匹配结果：{}", matches(SorceryExtractorContainer.this.ingredientInventory, recipe));

                    if (matches(SorceryExtractorContainer.this.ingredientInventory, recipe)) {
                        ItemStack stack = new ItemStack(recipe.get(recipe.size() - 1).getItem());
                        stack.setCount(1);
                        SorceryExtractorContainer.this.resultInventory.setInventorySlotContents(0, stack);
                    }
                    detectAndSendChanges();
                }
            }

            @Override
            public ItemStack onTake(Player thePlayer, ItemStack stack) {
                SorceryExtractorContainer.this.resultInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                return super.onTake(thePlayer, stack);
            }
        });
    }

    private Pair<IInventory, Integer> getMinStack(IInventory inventory) {
        List<Integer> stackSize = new ArrayList<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            stackSize.add(inventory.getStackInSlot(i).getCount());
        }
        return Pair.of(inventory, Collections.min(stackSize));
    }

    private void addResultSlotOld(IInventory inventory, int index, int xPos, int yPos) {
        addSlot(new Slot(inventory, index, xPos, yPos) {
            @Override
            @NotNull
            public ItemStack onTake(@NotNull Player thePlayer, @NotNull ItemStack stack) {
                for (int i = 0; i < 4; i++) {
                    SorceryExtractorContainer.this.ingredientInventory.decrStackSize(i, stack.getCount());
                }
                detectAndSendChanges();
                return super.onTake(thePlayer, stack);
            }

            @Override
            protected void onCrafting(ItemStack stack) {
                super.onCrafting(stack);
            }
        });
    }

    private boolean matches(IInventory inventory, List<ItemStack> recipes) {

        int totalCount = 0;
        int matchCount = 0;
        Logger logger = LogManager.getLogger();

        for (int i = 0; i < 4; i++) {
            if (!recipes.get(i).getItem().equals(ItemStack.EMPTY.getItem())) {
                totalCount++;
            }

        }
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (!inventory.getStackInSlot(i).getItem().equals(ItemStack.EMPTY.getItem()) &&
                    inventory.getStackInSlot(i).getItem().equals(recipes.get(i).getItem())) {
                matchCount++;
            }
        }
        return totalCount == matchCount;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private void addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int verAmount, int dx, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    private void addInventorySlots(int xStart, int yStart) {
        addSlotBox(playerInventory, 9, xStart, yStart, 9, 3, 18, 18);

        yStart += 58;
        addSlotRange(playerInventory, 0, xStart, yStart, 9, 18);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int INGREDIENT_SLOT_COUNT = 4;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS
    private static final int RESULT_SLOT_COUNT = 1;

    @Override
    @NotNull
    public ItemStack transferStackInSlot(@NotNull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 42) {
                if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
                    if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }
}


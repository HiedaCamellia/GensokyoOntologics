package github.thelawf.gensokyoontology.common.network.packet;

import github.thelawf.gensokyoontology.common.container.SpellCardConsoleContainer;
import github.thelawf.gensokyoontology.common.tileentity.SpellConsoleTileEntity;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Supplier;

public class CAddScriptPacket {

    public CAddScriptPacket() {

    }

    public static CAddScriptPacket fromBytes(PacketBuffer buf) {
        return new CAddScriptPacket();
    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> addScript(ctx.get().getSender()));
        ctx.get().setPacketHandled(true);
    }

    public static void addScript(ServerPlayer serverPlayer) {
        if (serverPlayer == null) return;
        if (!(serverPlayer.openContainer instanceof SpellCardConsoleContainer)) return;
        SpellCardConsoleContainer container = (SpellCardConsoleContainer) serverPlayer.openContainer;
        TileEntity tileEntity = container.getTileEntity();

        if (tileEntity instanceof SpellConsoleTileEntity) {
            SpellConsoleTileEntity spellConsole = (SpellConsoleTileEntity) tileEntity;
            CompoundTag scriptData = new CompoundTag();
            ListNBT scriptList = new ListNBT();

            spellConsole.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                for (int i = 0; i < itemHandler.getSlots() - 1; i++) {
                    if (isAllowedStack(i, itemHandler)) {
                        scriptList.add(getTag(i, itemHandler));
                    }
                }
            });
            scriptData.put("scripts", scriptList);
            container.getOutputStack().setTag(scriptData);
        }
    }

    private static boolean isAllowedStack(int index, IItemHandler itemHandler) {
        return !itemHandler.getStackInSlot(index).isEmpty();
    }

    private static boolean hasAllowedTag(int index, IItemHandler itemHandler) {
        CompoundTag nbt = getTag(index, itemHandler);
        return nbt.contains("type") && nbt.contains("value");
    }

    private static CompoundTag getTag(int index, IItemHandler itemHandler) {
        return itemHandler.getStackInSlot(index).getTag();
    }

    private static ItemStack getOutputStack(IItemHandler itemHandler) {
        return itemHandler.getStackInSlot(itemHandler.getSlots() - 1);
    }
}
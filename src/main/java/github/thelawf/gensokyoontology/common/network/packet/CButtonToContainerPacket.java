package github.thelawf.gensokyoontology.common.network.packet;

import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CButtonToContainerPacket {
    private final ContainerType<?> containerType;
    private final CompoundTag actionData;

    public CButtonToContainerPacket(ContainerType<?> containerType, CompoundTag actionData) {
        this.containerType = containerType;
        this.actionData = actionData;
    }

    public static CButtonToContainerPacket fromBytes(PacketBuffer buf) {
        return new CButtonToContainerPacket(buf.readRegistryId(), buf.readCompoundTag());
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeCompoundTag(this.actionData);
        buf.writeRegistryId(this.containerType);
    }

    public void handle(CButtonToContainerPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

        });
        ctx.get().setPacketHandled(true);
    }

    public boolean matchesContainer(CButtonToContainerPacket packet, ServerPlayer serverPlayer) {
        if (serverPlayer == null) return false;
        return serverPlayer.openContainer.getType() == packet.containerType;
    }
}
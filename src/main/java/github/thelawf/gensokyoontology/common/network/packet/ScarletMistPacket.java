package github.thelawf.gensokyoontology.common.network.packet;

import github.thelawf.gensokyoontology.client.renderer.world.ScarletSkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientLevel;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class ScarletMistPacket {
    private final int color;
    private static final Logger LOGGER = LogManager.getLogger();

    public ScarletMistPacket(PacketBuffer buf) {
        this.color = buf.readInt();
    }

    public ScarletMistPacket(int color) {
        this.color = color;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeVarInt(this.color);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().getReceptionSide().isClient()) {
            context.get().enqueueWork(() -> {
                Minecraft minecraft = Minecraft.getInstance();
                ClientLevel clientLevel = minecraft.world;
                if (clientLevel != null && minecraft.player != null) {
                    ScarletSkyRenderer renderer = new ScarletSkyRenderer();
                }
                LOGGER.info(this.color);
            });
        }
        context.get().setPacketHandled(true);
    }
}

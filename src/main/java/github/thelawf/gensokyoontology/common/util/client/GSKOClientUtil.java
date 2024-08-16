package github.thelawf.gensokyoontology.common.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public class GSKOClientUtil {

    public static ServerLevel getServerLevelFromClient(RegistryKey<Level> dimensionKey) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world != null && mc.world.getServer() != null) {
            return mc.world.getServer().level(dimensionKey);
        }
        return null;
    }
}

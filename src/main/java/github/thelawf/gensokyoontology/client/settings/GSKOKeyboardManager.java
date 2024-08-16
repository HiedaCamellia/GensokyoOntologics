package github.thelawf.gensokyoontology.client.settings;

import com.google.common.collect.Lists;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.client.entity.player.ClientPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.world.entity.SpawnReason;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class GSKOKeyboardManager {

    public static int RENDER_TICK = 80;

    public static final KeyBinding MOUSE_RIGHT = new GSKOKeyBinding("mouse_right", KeyConflictContext.IN_GAME,
            InputMappings.Type.MOUSE, 1, GensokyoOntology.withAffix("key.category.",""));
    public static final KeyBinding KEY_SWITCH_MODE = new GSKOKeyBinding("switch_mode", KeyConflictContext.IN_GAME,
            InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, GensokyoOntology.withAffix("key.category.",""));
    public static final List<KeyBinding> KEY_BINDINGS = Lists.newArrayList(KEY_SWITCH_MODE);

    public static void register() {
        for (KeyBinding key : KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(key);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onSummonDestructiveEye(InputEvent.MouseInputEvent event) {
    }

    private static void trySpawnFromClient(ClientPlayer player) {
        if (player.world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) player.world;
            EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get().spawn(serverLevel, null, null, player.getPosition(), SpawnReason.MOB_SUMMONED, false, false);
            serverLevel.getEntities().filter(entity -> entity.getType() == EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get()).forEach(entity -> {
                entity.canUpdate(false);
            });
        }
    }


    private static void activateEye(ClientPlayer player) {
        if (player.world instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) player.world;
            serverLevel.getEntities().filter(entity -> entity.getType() == EntityRegistry.DESTRUCTIVE_EYE_ENTITY.get()).forEach(entity -> {
                entity.canUpdate(true);
            });
        }
    }
}

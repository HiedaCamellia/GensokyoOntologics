package github.thelawf.gensokyoontology.common.util;

import github.thelawf.gensokyoontology.common.block.DanmakuTableBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.NonNullConsumer;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Random;

public class GSKOUtil {
    public static void showChatMsg(Player receiver, String text, int frequency) {
        if (receiver.ticksExisted % frequency == 0) {
            receiver.sendMessage(Component.literal(text), receiver.getUniqueID());
        }
    }
    public static void showChatMsg(Player player, boolean b, int frequency) {
        if (player.ticksExisted % frequency == 0) {
            player.sendMessage(Component.literal(String.valueOf(b)), player.getUniqueID());
        }
    }
    public static void showChatMsg(Player player, int i, int frequency) {
        if (player.ticksExisted % frequency == 0) {
            player.sendMessage(Component.literal(String.valueOf(i)), player.getUniqueID());
        }
    }
    public static void showChatMsg(Player player, float f, int frequency) {
        if (player.ticksExisted % frequency == 0) {
            player.sendMessage(Component.literal(String.valueOf(f)), player.getUniqueID());
        }
    }

    public static void showChatMsg(Player player, long l, int frequency) {
        if (player.ticksExisted % frequency == 0) {
            player.sendMessage(Component.literal(String.valueOf(l)), player.getUniqueID());
        }
    }

    public static void showChatMsg(Player player, Object obj, int frequency) {
        if (player.ticksExisted % frequency == 0) {
            player.sendMessage(Component.literal(obj.toString()), player.getUniqueID());
        }
    }

    public static void log(Class<?> clazz, String str) {
        LogManager.getLogger().info(clazz.getName() + ": {}", str);
    }

    public static void log(Class<?> clazz, boolean b) {
        LogManager.getLogger().info(clazz.getName() + ": {}", b);
    }

    public static void log(Class<?> clazz, int i) {
        LogManager.getLogger().info(clazz.getName() + ": {}", i);
    }

    public static void log(Class<?> clazz, float f) {
        LogManager.getLogger().info(clazz.getName() + ": {}", f);
    }
    public static void log(Class<?> clazz, Object obj) {
        LogManager.getLogger().info(clazz.getName() + ": {}", obj.toString());
    }
    public static ItemStack findItem(Player player, Item item) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i).getItem() == item) return player.inventory.getStackInSlot(i);
        }
        return ItemStack.EMPTY;
    }

    public static boolean firstMatch(Player player, Item item) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i).getItem() == item) return true;
        }
        return false;
    }

    public static <T> void runIfCapPresent(Entity entity, Capability<T> capability, NonNullConsumer<T> consumer) {
        entity.getCapability(capability).ifPresent(consumer);
    }

    public static <T> T rollFrom(List<T> pool) {
        return pool.get(new Random().nextInt(pool.size() - 1));
    }


}

package github.thelawf.gensokyoontology.api.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Predicate;

public interface INBTRunnable {
    default void runIf(Predicate<ItemStack> predicate, ItemStack stack, Runnable runnable) {
        if (predicate.test(stack)) {
            runnable.run();
        }
    }
}

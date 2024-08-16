package github.thelawf.gensokyoontology.api.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import java.util.Optional;

public interface INBTReader {

    default Optional<CompoundTag> getOrEmptyTag(ItemStack stack) {
        return stack.getTag() == null ? Optional.empty() : Optional.of(stack.getTag());
    }

    default CompoundTag getOrCreateTag(ItemStack stack) {
        return stack.getTag() == null ? new CompoundTag() : stack.getTag();
    }

    default boolean containsKey(ItemStack stack, String key) {
        return getOrEmptyTag(stack).isPresent() && getOrEmptyTag(stack).get().contains(key);
    }

    default int getNBTInt(CompoundTag nbt, String key) {
        return nbt.getInt(key);
    }

    default int getIntOrThrow(ItemStack stack, String key) {
        if (stack.getTag() == null) {
            throw new NullPointerException("Item stack has no key like: " + key);
        }
        return stack.getTag().getInt(key);
    }

    default String getNBTString(ItemStack stack, String key) {
        return containsKey(stack, key) && getOrEmptyTag(stack).isPresent() ?
                getOrEmptyTag(stack).get().getString(key) :
                "NULL";
    }

    default boolean getNBTBoolean(ItemStack stack, String key) {
        return containsKey(stack, key) && getOrEmptyTag(stack).isPresent() &&
                getOrEmptyTag(stack).get().getBoolean(key);
    }

    default BlockPos getNBTBlockPos(ItemStack stack, String key) {
        return containsKey(stack, key) && getOrEmptyTag(stack).isPresent() ?
                BlockPos.fromLong(getOrEmptyTag(stack).get().getLong(key)) :
                BlockPos.ZERO;
    }
}

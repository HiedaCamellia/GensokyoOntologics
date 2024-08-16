package github.thelawf.gensokyoontology.api.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import java.util.function.Predicate;

public interface INBTWriter extends INBTReader {
    default void writeBoolean(ItemStack stack, String key, Boolean value) {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean(key, value);
        stack.setTag(nbt);
    }

    default void writeString(ItemStack stack, String key, String value) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString(key, value);
        stack.setTag(nbt);
    }


    default void writeBlockPos(ItemStack stack, String key, BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        nbt.putLong(key, pos.toLong());
        stack.setTag(nbt);
    }

    default void mergeBoolean(ItemStack stack, String key, Boolean value) {
        CompoundTag nbt = getOrCreateTag(stack);
        CompoundTag newNBT = new CompoundTag();
        newNBT.putBoolean(key, value);
        nbt.merge(newNBT);
        stack.setTag(nbt);
    }

    default void mergeString(ItemStack stack, String key, String value) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString(key, value);
        stack.setTag(nbt);
    }

    default void mergeBlockPos(ItemStack stack, String key, BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        nbt.putLong(key, pos.toLong());
        stack.setTag(nbt);

    }


    default CompoundTag withBlockPos(CompoundTag nbt, String xKey, String yKey, String zKey, BlockPos pos) {
        nbt.putInt(xKey, pos.getX());
        nbt.putInt(yKey, pos.getY());
        nbt.putInt(zKey, pos.getZ());
        return nbt;
    }

    default void mergeBlockPos(ItemStack stack, String xKey, String yKey, String zKey, BlockPos pos) {
        CompoundTag nbt = getOrCreateTag(stack);
        CompoundTag newNBT = new CompoundTag();
        newNBT.putInt(xKey, pos.getX());
        newNBT.putInt(yKey, pos.getY());
        newNBT.putInt(zKey, pos.getZ());
        nbt.merge(newNBT);
        stack.setTag(nbt);
    }

    default void writeStringIf(Predicate<ItemStack> predicate, ItemStack stack, String key, String value) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putString(key, value);
            stack.setTag(nbt);
        }
    }

    default void writeBooleanIf(Predicate<ItemStack> predicate, ItemStack stack, String key, boolean value) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putBoolean(key, value);
            stack.setTag(nbt);
        }
    }

    default void writeBlockPosIf(Predicate<ItemStack> predicate, ItemStack stack, String key, BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putLong(key, pos.toLong());
            stack.setTag(nbt);
        }
    }

    default void mergeStringIf(Predicate<ItemStack> predicate, ItemStack stack, String key, String value) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putString(key, value);
            stack.setTag(nbt);
        }
    }

    default void mergeBooleanIf(Predicate<ItemStack> predicate, ItemStack stack, String key, boolean value) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putBoolean(key, value);
            stack.setTag(nbt);
        }
    }

    default void mergeBlockPosIf(Predicate<ItemStack> predicate, ItemStack stack, String key, BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        if (predicate.test(stack)) {
            nbt.putLong(key, pos.toLong());
            stack.setTag(nbt);
        }
    }
}

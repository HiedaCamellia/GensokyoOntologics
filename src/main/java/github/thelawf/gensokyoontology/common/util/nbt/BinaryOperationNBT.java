package github.thelawf.gensokyoontology.common.util.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNBT;

public class BinaryOperationNBT extends CompoundTag {
    private final StringNBT operation;
    private final CompoundTag left = new CompoundTag();
    private final CompoundTag right = new CompoundTag();

    public BinaryOperationNBT(String operation, CompoundTag leftEntry, CompoundTag rightEntry) {
        this.operation = StringNBT.valueOf(operation);
        this.left.put("left", leftEntry);
        this.right.put("right", rightEntry);
    }

    public StringNBT getOperation() {
        return this.operation;
    }

    public CompoundTag getLeft() {
        return this.left;
    }

    public CompoundTag getRight() {
        return this.right;
    }
}

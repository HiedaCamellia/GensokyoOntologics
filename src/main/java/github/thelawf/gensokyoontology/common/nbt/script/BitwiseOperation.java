package github.thelawf.gensokyoontology.common.nbt.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.network.chat.Component;

public enum BitwiseOperation {
    BITWISE_AND("BitwiseAnd"),
    BITWISE_OR("BitwiseOr"),
    BITWISE_NOT("BitwiseNot"),
    BITWISE_XOR("BitwiseXOR"),
    BITWISE_LEFT("BitwiseLeft"),
    BITWISE_RIGHT("BitwiseRight");


    public final String key;

    BitwiseOperation(String key) {
        this.key = key;
    }
    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.",".operation_builder.button.math." + getKey());
    }

    private String getKey() {
        return this.key;
    }
}

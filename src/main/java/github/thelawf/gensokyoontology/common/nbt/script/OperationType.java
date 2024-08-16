package github.thelawf.gensokyoontology.common.nbt.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.network.chat.Component;

public enum OperationType {
    BITWISE("bitwise"),
    LOGIC("logic"),
    MATH("math");
    public final String key;

    OperationType(String key) {
        this.key = key;
    }
    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.",".operation_builder.button.type." + getKey());
    }

    private String getKey() {
        return this.key;
    }
}

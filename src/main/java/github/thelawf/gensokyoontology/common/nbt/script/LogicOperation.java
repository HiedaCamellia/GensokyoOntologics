package github.thelawf.gensokyoontology.common.nbt.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.network.chat.Component;

public enum LogicOperation {
    AND("And"),
    OR("Or"),
    NOT("Not");
    public final String key;

    LogicOperation(String key) {
        this.key = key;
    }
    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.",".operation_builder.button.math." + getKey());
    }

    private String getKey() {
        return this.key;
    }
}

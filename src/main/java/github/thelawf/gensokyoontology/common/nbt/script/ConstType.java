package github.thelawf.gensokyoontology.common.nbt.script;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.network.chat.Component;

public enum ConstType {
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    STRING("string");
    public final String key;

    ConstType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.",".const_builder.button.constType." + getKey());
    }
}

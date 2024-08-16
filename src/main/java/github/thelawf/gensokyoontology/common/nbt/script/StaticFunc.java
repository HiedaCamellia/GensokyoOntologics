package github.thelawf.gensokyoontology.common.nbt.script;

import com.google.common.collect.Lists;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.EntityRegistry;
import net.minecraft.network.chat.Component;

import java.util.List;

public enum StaticFunc {
    SHOOT("shoot", Lists.newArrayList(InstanceType.DANMAKU.name, InstanceType.VECTOR3D.name,
            ConstType.FLOAT.key, ConstType.FLOAT.key), "void");
    public final String methodName;
    public final List<String> parameters;
    public final String returnType;

    StaticFunc(String methodName, List<String> parameters, String returnType) {
        this.methodName = methodName;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.", ".static_func." + this.methodName);
    }
}

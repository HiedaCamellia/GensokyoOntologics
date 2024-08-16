package github.thelawf.gensokyoontology.client.renderer;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.world.entity.LivingEntity;

public class GSKOBipedModel<T extends LivingEntity> extends BipedModel<T> {
    public GSKOBipedModel(float modelSize) {
        super(modelSize);
    }
}

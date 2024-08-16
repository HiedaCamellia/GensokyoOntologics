package github.thelawf.gensokyoontology.api.entity;

import net.minecraft.world.entity.Entity;

public interface IAlienable<B extends Entity, A extends Entity> {
    B getBefore();

    A getAfter();

    void alienize(B before);
}

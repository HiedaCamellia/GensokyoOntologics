package github.thelawf.gensokyoontology.core.init;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.potion.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EffectRegistry {
    public static final DeferredRegister<MobEffect> POTION_EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT, GensokyoOntology.MODID);
    public static final DeferredHolder<MobEffect,? extends MobEffect> LOVE_EFFECT = POTION_EFFECTS.register("love_potion",
            LovePotionEffect::new);
    public static final DeferredHolder<MobEffect,? extends MobEffect> HYPNOSIS_EFFECT = POTION_EFFECTS.register("hypnosis",
            HypnosisEffect::new);

    public static final DeferredHolder<MobEffect,? extends MobEffect> MANIA_EFFECT = POTION_EFFECTS.register("mania",
            ManiaEffect::new);

    public static final DeferredHolder<MobEffect,? extends MobEffect> DEPRESSION_EFFECT = POTION_EFFECTS.register("depression",
            DepressionEffect::new);

    public static final DeferredHolder<MobEffect,? extends MobEffect> HAKUREI_BLESS_EFFECT = POTION_EFFECTS.register("hakurei_bless", HakureiBlessEffect::new);
}

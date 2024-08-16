package github.thelawf.gensokyoontology.api.client;


import net.minecraft.network.chat.Component;

public interface ITextBuilder {
    default Component withTranslation(String prefix, String suffix){
        return Component.translatable(prefix + "." + "." + suffix);
    }

    default Component withText(String text){
        return Component.literal(text);
    }
}

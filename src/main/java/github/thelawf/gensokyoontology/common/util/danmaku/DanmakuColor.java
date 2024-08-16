package github.thelawf.gensokyoontology.common.util.danmaku;

import github.thelawf.gensokyoontology.GensokyoOntology;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;

public enum DanmakuColor {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    AQUA,
    BLUE,
    PURPLE,
    MAGENTA,
    PINK,
    NONE;

    public Component toTextComponent() {
        return GensokyoOntology.withTranslation("gui.", ".danmaku_color." + this.name().toLowerCase());
    }

    public DanmakuColor getIfMatches(String name) {
        return this.name().toLowerCase().equals(name) ? this : DanmakuColor.NONE;
    }
}

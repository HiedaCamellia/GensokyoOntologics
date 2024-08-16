package github.thelawf.gensokyoontology.common.entity.spellcard;

import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 虹光「光风霁月」
 */
public class TranquilWindAndMoon extends SpellCardEntity {
    public TranquilWindAndMoon(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn, Player player) {
        super(entityTypeIn, worldIn, player);
    }

    public TranquilWindAndMoon(EntityType<? extends SpellCardEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        List<Vec3> starPositions = DanmakuUtil.getStarLinePos(0.3f, 0.11, DanmakuUtil.Plane.XY);


    }

    @Override
    @NotNull
    public ItemStack getItem() {
        return new ItemStack(ItemRegistry.SPELL_CARD_BLANK.get());
    }
}

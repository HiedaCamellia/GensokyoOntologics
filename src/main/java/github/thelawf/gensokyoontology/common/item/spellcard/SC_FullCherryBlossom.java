package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.projectile.SmallShotEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.FullCherryBlossomEntity;
import github.thelawf.gensokyoontology.common.entity.spellcard.HellEclipseEntity;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuColor;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuType;
import github.thelawf.gensokyoontology.common.util.danmaku.DanmakuUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

public class SC_FullCherryBlossom extends SpellCardItem {

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {
        if (worldIn instanceof ServerLevel) {
            FullCherryBlossomEntity fullCherryBlossom = new FullCherryBlossomEntity(worldIn, playerIn);
            fullCherryBlossom.setOwner(playerIn);
            worldIn.addEntity(fullCherryBlossom);
            playerIn.getCooldownTracker().setCooldown(this, 1200);
        }
    }
}

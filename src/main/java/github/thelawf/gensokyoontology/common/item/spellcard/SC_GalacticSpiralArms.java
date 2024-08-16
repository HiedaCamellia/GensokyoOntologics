package github.thelawf.gensokyoontology.common.item.spellcard;

import github.thelawf.gensokyoontology.common.entity.spellcard.GalacticArmSpellEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class SC_GalacticSpiralArms extends SpellCardItem {

    @Override
    @NotNull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull Hand handIn) {
        if (worldIn.isClientSide) {
            GalacticArmSpellEntity galacticArm;
            try {
                galacticArm = new GalacticArmSpellEntity(worldIn, playerIn);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            worldIn.addEntity(galacticArm);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    protected void applySpell(Level worldIn, Player playerIn) {

    }
}

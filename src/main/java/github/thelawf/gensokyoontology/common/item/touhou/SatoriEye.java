package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.api.dialog.IEntityDialog;
import github.thelawf.gensokyoontology.common.entity.ConversationalEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

import org.jetbrains.annotations.NotNull;

public class SatoriEye extends Item {
    public SatoriEye(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResultType itemInteractionForEntity(@NotNull ItemStack stack, @NotNull Player playerIn, @NotNull LivingEntity target, @NotNull Hand hand) {
        if (target instanceof ConversationalEntity) {
            playerIn.sendSystemMessage((Component.translatable(((ConversationalEntity) target).getDialog().name), Component.literal(String.valueOf(playerIn.getUUID())));
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

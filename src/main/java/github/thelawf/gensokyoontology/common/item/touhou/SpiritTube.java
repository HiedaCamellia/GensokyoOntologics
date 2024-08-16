package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.passive.FoxEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritTube extends Item {
    public SpiritTube(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResultType itemInteractionForEntity(ItemStack stack, Player playerIn, LivingEntity target, Hand hand) {
        if (target instanceof FoxEntity) {
            playerIn.inventory.addItemStackToInventory(new ItemStack(
                    ItemRegistry.KUDA_GITSUNE_TUBE.get()));
            stack.shrink(1);
            return ActionResultType.func_233537_a_(playerIn.world.isRemote());
        }
        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".spirit_tube"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

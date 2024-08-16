package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.entity.monster.SpectreEntity;
import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.GhastEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KudaGitsuneTube extends Item {
    public KudaGitsuneTube(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ActionResultType itemInteractionForEntity(@NotNull ItemStack stack, @NotNull Player playerIn, @NotNull LivingEntity target, Hand hand) {
        if (target instanceof SpectreEntity) {
            stack.shrink(1);
            CompoundTag nbt = new CompoundTag();
            nbt.putString("story", "story." + GensokyoOntology.MODID + ".spectre");
            nbt.putString("entity_stored", "entity." + GensokyoOntology.MODID + ".spectre");

            playerIn.inventory.addItemStackToInventory(new ItemStack(
                    ItemRegistry.GITSUNE_TUBE_FULL.get(), 1, nbt));
            return ActionResultType.func_233537_a_(playerIn.world.isRemote());
        } else if (target instanceof GhastEntity) {
            // target.remove();
            stack.shrink(1);
            CompoundTag nbt = new CompoundTag();
            nbt.putString("story", "story." + GensokyoOntology.MODID + ".ghast");
            nbt.putString("entity_stored", "entity.minecraft.ghast");

            playerIn.inventory.addItemStackToInventory(new ItemStack(
                    ItemRegistry.GITSUNE_TUBE_FULL.get(), 1, nbt));
            return ActionResultType.func_233537_a_(playerIn.world.isRemote());
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(GensokyoOntology.withTranslation("tooltip.", ".kuda_gitsune_tube"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

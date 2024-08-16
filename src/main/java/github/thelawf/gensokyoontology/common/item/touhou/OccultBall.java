package github.thelawf.gensokyoontology.common.item.touhou;

import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.world.GSKODimensions;
import github.thelawf.gensokyoontology.common.world.TeleportHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import net.minecraft.world.level.Level;
import net.minecraft.world.biome.Biome;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class OccultBall extends Item {
    public OccultBall(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@NotNull Level worldIn, Player playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.hasTag()) {
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));
            // return ActionResult.resultPass(playerIn.getHeldItem(Hand.MAIN_HAND).equals(
            //         new ItemStack(ItemRegistry.OCCULT_BALL.get())) ?
            //         playerIn.getHeldItem(Hand.MAIN_HAND) : playerIn.getHeldItem(Hand.OFF_HAND));
        }

        CompoundTag nbt = new CompoundTag();
        Biome biome = worldIn.getBiome(playerIn.getPosition());
        nbt.putString("biome", String.valueOf(biome.getRegistryName()));
        nbt.putBoolean("can_travel_to_gensokyo", true);

        stack.setTag(nbt);

        if (worldIn.isClientSide() && nbt.getBoolean("can_travel_to_gensokyo") && playerIn instanceof ServerPlayer) {
            nbt.remove("can_travel_to_gensokyo");
            nbt.putBoolean("can_travel_to_gensokyo", false);
            stack.setTag(nbt);

            ServerLevel serverLevel = (ServerLevel) worldIn;
            ServerPlayer serverPlayer = (ServerPlayer) playerIn;
            ServerLevel gensokyo = serverLevel.getServer().level(GSKODimensions.GENSOKYO);
            TeleportHelper.teleport(serverPlayer, gensokyo, playerIn.getPosition());

        }

        BlockPos blockPos = playerIn.getPosition();

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull ITooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip." + GensokyoOntology.MODID + ".occult_ball.hint"));
        if (stack.getTag() != null && stack.getTag().contains("biome")) {
            String info = "tooltip." + GensokyoOntology.MODID + ".occult_ball.biome";
            tooltip.add(Component.translatable(info));
            tooltip.add(Component.translatable(stack.getTag().getString("biome")));
        }
        if (stack.getTag() != null && stack.getTag().contains("can_travel_to_gensokyo")) {
            String info = "tooltip." + GensokyoOntology.MODID + ".occult_ball.accessablity";
            // tooltip.add(Component.translatable(info));
            tooltip.add(Component.translatable(stack.getTag().getString("can_travel_to_gensokyo")));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @NotNull
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.BLOCK;
    }
}

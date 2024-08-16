package github.thelawf.gensokyoontology.core.init;

import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class DispenserRegister {
    @SubscribeEvent
    public static void onDispenseRegister(FMLCommonSetupEvent event) {
        DispenserBlock.registerDispenseBehavior(ItemRegistry.HOTSPRING_BUCKET.get(),
                new DefaultDispenseItemBehavior() {
                    private final DefaultDispenseItemBehavior behavior =
                            new DefaultDispenseItemBehavior();

                    /**
                     * Dispense the specified stack, play the dispensed sounds and spawn particles.
                     *
                     */
                    @Override
                    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                        BucketItem bucketItem = (BucketItem) stack.getItem();
                        BlockPos blockPos = source.getBlockPos().offset(source.getBlockState()
                                .get(DispenserBlock.FACING));
                        Level world = source.level();
                        if (bucketItem.tryPlaceContainedLiquid(null, world,
                                blockPos, null)) {
                            bucketItem.onLiquidPlaced(world, stack, blockPos);
                            return new ItemStack(Items.BUCKET);
                        } else {
                            return this.behavior.dispense(source, stack);
                        }
                    }
                });

        DispenserBlock.registerDispenseBehavior(ItemRegistry.SAKE_BUCKET.get(),
                new DefaultDispenseItemBehavior() {
                    private final DefaultDispenseItemBehavior behavior =
                            new DefaultDispenseItemBehavior();

                    /**
                     * Dispense the specified stack, play the dispensed sounds and spawn particles.
                     *
                     */
                    @Override
                    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                        BucketItem bucketItem = (BucketItem) stack.getItem();
                        BlockPos blockPos = source.getBlockPos().offset(source.getBlockState()
                                .get(DispenserBlock.FACING));
                        Level world = source.level();
                        if (bucketItem.tryPlaceContainedLiquid(null, world,
                                blockPos, null)) {
                            bucketItem.onLiquidPlaced(world, stack, blockPos);
                            return new ItemStack(Items.BUCKET);
                        } else {
                            return this.behavior.dispense(source, stack);
                        }
                    }
                });

    }
}

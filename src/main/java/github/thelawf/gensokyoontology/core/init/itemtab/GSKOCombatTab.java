package github.thelawf.gensokyoontology.core.init.itemtab;

import github.thelawf.gensokyoontology.core.init.ItemRegistry;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.world.item.ItemStack;

public class GSKOCombatTab extends ItemGroup {

    public static final GSKOCombatTab GSKO_COMBAT_TAB = new GSKOCombatTab();

    public GSKOCombatTab() {
        super("gensokyoontology_combat");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.LARGE_SHOT_RED.get());
    }
}

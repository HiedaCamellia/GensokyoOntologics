package github.thelawf.gensokyoontology.common.block.ore;

import net.minecraft.world.level.block.*;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DragonSphereOre extends OreBlock {
    public DragonSphereOre() {
        super(Properties.from(Blocks.DIAMOND_ORE).hardnessAndResistance(5.0f, 10.0f).setRequiresTool()
                .harvestLevel(3));
    }
}

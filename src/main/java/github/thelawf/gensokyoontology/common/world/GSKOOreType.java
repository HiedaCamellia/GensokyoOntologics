package github.thelawf.gensokyoontology.common.world;

import github.thelawf.gensokyoontology.core.init.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;

public enum GSKOOreType {
    IZANO_OBJECT(Lazy.of(BlockRegistry.IZANO_OBJECT_ORE), 4, 15, 30),
    DRAGON_SPHERE(Lazy.of(BlockRegistry.DRAGON_SPHERE_ORE), 4, 10, 30),
    IMMEMORIAL_ALLOY(Lazy.of(BlockRegistry.IMMEMORIAL_ALLOY_BLOCK), 2, 3, 10),
    JADE_GENSOKYO(Lazy.of(BlockRegistry.JADE_ORE), 6, 8, 20),
    JADE_FORMER_HELL(Lazy.of(BlockRegistry.JADE_ORE), 7, 7, 12);

    private final Lazy<Block> lazyBlock;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    GSKOOreType(Lazy<Block> lazyBlock, int maxVeinSize, int minHeight, int maxHeight) {
        this.lazyBlock = lazyBlock;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Lazy<Block> getLazyBlock() {
        return lazyBlock;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public static GSKOOreType getInstance(Block block) {
        for (GSKOOreType oreType : values()) {
            if (block == oreType.lazyBlock) {
                return oreType;
            }
        }
        return null;
    }
}

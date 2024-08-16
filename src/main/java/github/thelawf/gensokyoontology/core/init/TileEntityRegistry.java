package github.thelawf.gensokyoontology.core.init;

import com.mojang.datafixers.types.Type;
import github.thelawf.gensokyoontology.GensokyoOntology;
import github.thelawf.gensokyoontology.common.tileentity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TileEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, GensokyoOntology.MODID);

    public static final DeferredRegister<BlockEntityType<SpaceFissureTileEntity>> SPACE_FISSURE_TILE_ENTITY =
            TILE_ENTITIES.register("space_fissure_tileentity", () -> BlockEntityType.Builder.of(
                    SpaceFissureTileEntity::new, BlockRegistry.SPACE_FISSURE_BLOCK.get()).build(null));
    public static final DeferredRegister<BlockEntityType<DanmakuTabelTileEntity>> DANMAKU_TABLE_TILE =
            TILE_ENTITIES.register("danmaku_table_tileentity", () -> BlockEntityType.Builder.of(
                    DanmakuTabelTileEntity::new, BlockRegistry.DANMAKU_TABLE.get()).build(null));
    public static final DeferredRegister<BlockEntityType<GapTileEntity>> GAP_TILE_ENTITY =
            TILE_ENTITIES.register("sukima_tileentity", () -> BlockEntityType.Builder.of(
                    GapTileEntity::new, BlockRegistry.GAP_BLOCK.get()).build(null));
    public static final DeferredRegister<BlockEntityType<SorceryExtractorTileEntity>> SORCERY_EXTRACTOR_TILE_ENTITY =
            TILE_ENTITIES.register("sorcery_tileentity", () -> BlockEntityType.Builder.of(
                    SorceryExtractorTileEntity::new, BlockRegistry.SORCERY_EXTRACTOR.get()).build(null));
    public static final DeferredRegister<BlockEntityType<SaisenBoxTileEntity>> SAISEN_BOX_TILE_ENTITY =
            TILE_ENTITIES.register("saisen_box_tileentity", () -> BlockEntityType.Builder.of(
                    SaisenBoxTileEntity::new, BlockRegistry.SAISEN_BOX.get()).build(null));
    public static final DeferredRegister<BlockEntityType<DisposableSpawnerTile>> DISPOSABLE_SPAWNER_TILE_ENTITY =
            TILE_ENTITIES.register("disposable_spawner_tileentity", () -> BlockEntityType.Builder.of(
                    DisposableSpawnerTile::new, BlockRegistry.DISPOSABLE_SPAWNER.get()).build(null));
    public static final DeferredRegister<BlockEntityType<SpellConsoleTileEntity>> SPELL_CONSOLE_TILE_ENTITY =
            TILE_ENTITIES.register("spell_console_tileentity", () -> BlockEntityType.Builder.of(
                    SpellConsoleTileEntity::new, BlockRegistry.SPELL_CARD_CONSOLE.get()).build(null));
    public static final DeferredRegister<BlockEntityType<AdobeTileEntity>> ADOBE_TILE_ENTITY =
            TILE_ENTITIES.register("adobe_tileentity", () -> BlockEntityType.Builder.of(
                    AdobeTileEntity::new, BlockRegistry.CLAY_ADOBE_BLOCK.get()).build(null));
    public static final DeferredRegister<BlockEntityType<HaniwaTileEntity>> HANIWA_TILE_ENTITY =
            TILE_ENTITIES.register("haniwa_tileentity", () -> BlockEntityType.Builder.of(
                    HaniwaTileEntity::new, BlockRegistry.HANIWA_BLOCK.get()).build(null));
}

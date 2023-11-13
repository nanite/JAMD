package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.block.*;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import com.unrealdinnerbone.jamd.world.CustomFlatLevelSource;
import com.unrealdinnerbone.trenzalore.api.platform.services.IRegistry;
import com.unrealdinnerbone.trenzalore.api.registry.Regeneration;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryObjects;
import com.unrealdinnerbone.trenzalore.lib.CreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class JAMDRegistry implements IRegistry {

    private static final RegistryObjects<Block> BLOCKS = Regeneration.create(Registries.BLOCK);
    private static final RegistryObjects<Item> ITEMS = Regeneration.create(Registries.ITEM);
    private static final RegistryObjects<BlockEntityType<?>> TILES = Regeneration.create(Registries.BLOCK_ENTITY_TYPE);
    private static final RegistryObjects<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = Regeneration.create(Registries.CHUNK_GENERATOR);

    public static final RegistryEntry<Codec<? extends ChunkGenerator>> CUSTOM_FLAT_LEVEL_SOURCE = CHUNK_GENERATORS.register("mining", () -> CustomFlatLevelSource.CODEC);

    public static final WorldType OVERWORLD = of("mining", "portal_block", "portal", OverworldPortalBlock::new, OverworldBlockEntity::new, BiomeTags.IS_OVERWORLD);

    public static final WorldType NETHER = of("nether", "nether_portal", "nether_portal", NetherPortalBlock::new, NetherBlockEntity::new, BiomeTags.IS_NETHER);

    public static final WorldType END = of("end", "end_portal", "end_portal", EndPortalBlock::new, EndBlockEntity::new, BiomeTags.IS_END);


    private static WorldType of(String name, String blockName, String tileName, Supplier<Block> blockSupplier, BiFunction<BlockPos, BlockState, PortalTileEntity> tileSupplier, TagKey<Biome> biomeTagKey) {
        RegistryEntry<Block> block = BLOCKS.register(blockName, blockSupplier);
        return new WorldType(name, block, ITEMS.register(blockName, () -> new BlockItem(block.get(), new Item.Properties())), TILES.register(tileName, () -> Regeneration.createBlockEntityType(tileSupplier, block.get())), biomeTagKey);
    }

    @Override
    public void afterRegistered() {
        Regeneration.addItemsToCreateTab(CreativeTabs.FUNCTIONAL_BLOCKS, List.of(OVERWORLD.getItem(), NETHER.getItem(), END.getItem()));
    }

    @Override
    public List<RegistryObjects<?>> getRegistryObjects() {
        return List.of(BLOCKS, ITEMS, TILES, CHUNK_GENERATORS);
    }

    @Override
    public String getModID() {
        return JAMD.MOD_ID;
    }


}

package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.block.PortalBlock;
import com.unrealdinnerbone.jamd.block.PortalTileEntity;
import com.unrealdinnerbone.jamd.world.CustomFlatLevelSource;
import com.unrealdinnerbone.trenzalore.api.platform.Services;
import com.unrealdinnerbone.trenzalore.api.platform.services.IRegistry;
import com.unrealdinnerbone.trenzalore.api.registry.Regeneration;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryFactory;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryObjects;
import com.unrealdinnerbone.trenzalore.lib.CreativeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.List;

public class JAMDRegistry implements IRegistry {


    private static final RegistryObjects<Block> BLOCKS = Regeneration.create(Registries.BLOCK);
    private static final RegistryObjects<Item> ITEMS = Regeneration.create(Registries.ITEM);
    private static final RegistryObjects<BlockEntityType<?>> TILES = Regeneration.create(Registries.BLOCK_ENTITY_TYPE);
    private static final RegistryObjects<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = Regeneration.create(Registries.CHUNK_GENERATOR);

    public static final RegistryEntry<Codec<? extends ChunkGenerator>> CUSTOM_FLAT_LEVEL_SOURCE = CHUNK_GENERATORS.register("mining", () -> CustomFlatLevelSource.CODEC);
    public static final RegistryEntry<Block> PORTAL_BLOCK = BLOCKS.register("portal_block", PortalBlock::new);
    public static final RegistryEntry<Item> PORTAL_BLOCK_ITEM = ITEMS.register("portal_block", () -> new BlockItem(PORTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<BlockEntityType<?>> PORTAL = TILES.register("portal", () -> Regeneration.createBlockEntityType(PortalTileEntity::new, PORTAL_BLOCK.get()));

    public static class Keys {
        public static final ResourceKey<DimensionType> DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(JAMD.MOD_ID, "mining"));
        public static final ResourceKey<Biome> BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(JAMD.MOD_ID, "mining"));

        public static final ResourceKey<Level> LEVEL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(JAMD.MOD_ID, "mining"));
    }

    @Override
    public void afterRegistered() {
        Regeneration.addItemToCreateTab(CreativeTabs.FUNCTIONAL_BLOCKS, PORTAL_BLOCK_ITEM);
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

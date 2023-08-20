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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class JAMDRegistry implements IRegistry {

    private static final RegistryObjects<Block> BLOCKS = Regeneration.create(Registries.BLOCK);
    private static final RegistryObjects<Item> ITEMS = Regeneration.create(Registries.ITEM);
    private static final RegistryObjects<BlockEntityType<?>> TILES = Regeneration.create(Registries.BLOCK_ENTITY_TYPE);
    private static final RegistryObjects<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = Regeneration.create(Registries.CHUNK_GENERATOR);

    public static final RegistryEntry<Codec<? extends ChunkGenerator>> CUSTOM_FLAT_LEVEL_SOURCE = CHUNK_GENERATORS.register("mining", () -> CustomFlatLevelSource.CODEC);

    public static final RegistrySet OVERWORLD = RegistrySet.of("portal_block", "portal", OverworldPortalBlock::new, OverworldBlockEntity::new);
    public static final RegistrySet NETHER = RegistrySet.of("nether_portal", NetherPortalBlock::new, NetherBlockEntity::new);

    public static final RegistrySet END = RegistrySet.of("end_portal", EndPortalBlock::new, EndBlockEntity::new);


    public static class Keys {
        public static final KeySet OVERWORLD = KeySet.of(new ResourceLocation(JAMD.MOD_ID, "mining"));

        public static final KeySet NETHER = KeySet.of(new ResourceLocation(JAMD.MOD_ID, "nether"));

        public static final KeySet END = KeySet.of(new ResourceLocation(JAMD.MOD_ID, "end"));

    }

    @Override
    public void afterRegistered() {
        Regeneration.addItemsToCreateTab(CreativeTabs.FUNCTIONAL_BLOCKS, List.of(OVERWORLD.item(), NETHER.item(), END.item()));
    }

    @Override
    public List<RegistryObjects<?>> getRegistryObjects() {
        return List.of(BLOCKS, ITEMS, TILES, CHUNK_GENERATORS);
    }

    @Override
    public String getModID() {
        return JAMD.MOD_ID;
    }

    public record RegistrySet(RegistryEntry<Block> block, RegistryEntry<BlockItem> item, RegistryEntry<BlockEntityType<PortalTileEntity>> blockEntity) {

        private static RegistrySet of(String name, Supplier<Block> blockSupplier, BiFunction<BlockPos, BlockState, PortalTileEntity> tileSupplier) {
            return of(name, name, blockSupplier, tileSupplier);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion = "4.0.0")
        private static RegistrySet of(String blockName, String tileName, Supplier<Block> blockSupplier, BiFunction<BlockPos, BlockState, PortalTileEntity> tileSupplier) {
            RegistryEntry<Block> block = BLOCKS.register(blockName, blockSupplier);
            RegistryEntry<BlockItem> itemBlock = ITEMS.register(blockName, () -> new BlockItem(block.get(), new Item.Properties()));
            RegistryEntry<BlockEntityType<PortalTileEntity>> tile = TILES.register(tileName, () -> Regeneration.createBlockEntityType(tileSupplier, block.get()));
            return new RegistrySet(block, itemBlock, tile);
        }
    }

    public record KeySet(ResourceKey<Level> level, ResourceKey<DimensionType> dimensionType, ResourceKey<Biome> biome) {

        private static KeySet of(ResourceLocation id) {
            return new KeySet(ResourceKey.create(Registries.DIMENSION, id), ResourceKey.create(Registries.DIMENSION_TYPE, id), ResourceKey.create(Registries.BIOME, id));
        }
    }
}

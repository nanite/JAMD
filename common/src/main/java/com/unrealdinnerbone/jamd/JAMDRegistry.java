package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.block.PortalBlock;
import com.unrealdinnerbone.jamd.block.PortalTileEntity;
import com.unrealdinnerbone.jamd.world.CustomFlatLevelSource;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class JAMDRegistry {

    public static final List<DeferredRegister<?>> REGISTRIES = new ArrayList<>();

    private static final DeferredRegister<Block> BLOCKS = register(Registries.BLOCK);
    private static final DeferredRegister<Item> ITEMS = register(Registries.ITEM);

    private static final DeferredRegister<BlockEntityType<?>> TILES = register(Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = register(Registries.CHUNK_GENERATOR);

    public static final RegistrySupplier<PortalBlock> MINE_PORTAL_BLOCK = BLOCKS.register("mine_portal_block", PortalBlock::new);
    public static final RegistrySupplier<Item> MINE_PORTAL_BLOCK_ITEM = ITEMS.register("mine_portal_block", () -> new BlockItem(MINE_PORTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<BlockEntityType<PortalTileEntity>> PORTAL = TILES.register("portal", () -> BlockEntityType.Builder.of(PortalTileEntity::new, MINE_PORTAL_BLOCK.get()).build(null));

    public static final RegistrySupplier<Codec<CustomFlatLevelSource>> FLAT_LEVEL_SOURCE = CHUNK_GENERATORS.register(JAMD.DIM_ID.getPath(), () -> CustomFlatLevelSource.CODEC);

    private static <T> DeferredRegister<T> register(ResourceKey<Registry<T>> registry) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(JAMD.MOD_ID, registry);
        REGISTRIES.add(deferredRegister);
        return deferredRegister;
    }




}

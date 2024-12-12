package com.unrealdinnerbone.jamd;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.unrealdinnerbone.jamd.block.*;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import com.unrealdinnerbone.jamd.world.CustomFlatLevelSource;
import com.unrealdinnerbone.trenzalore.api.platform.services.ICreativeTabRegister;
import com.unrealdinnerbone.trenzalore.api.platform.services.IRegistry;
import com.unrealdinnerbone.trenzalore.api.registry.BlockRegistryObjects;
import com.unrealdinnerbone.trenzalore.api.registry.ItemRegistryObjects;
import com.unrealdinnerbone.trenzalore.api.registry.Regeneration;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryObjects;
import com.unrealdinnerbone.trenzalore.lib.CreativeTabs;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class JAMDRegistry implements IRegistry {

    private static final BlockRegistryObjects BLOCKS = Regeneration.createBlockRegistry(JAMD.MOD_ID);
    private static final ItemRegistryObjects ITEMS = Regeneration.createItemRegistry(JAMD.MOD_ID);
    private static final RegistryObjects<BlockEntityType<?>> TILES = Regeneration.create(JAMD.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final RegistryObjects<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS = Regeneration.create(JAMD.MOD_ID, Registries.CHUNK_GENERATOR);

    public static final RegistryEntry<MapCodec<? extends ChunkGenerator>> CUSTOM_FLAT_LEVEL_SOURCE = CHUNK_GENERATORS.register("mining", () -> CustomFlatLevelSource.CODEC);

    public static final WorldType OVERWORLD = of("mining", "portal_block", "portal", OverworldPortalBlock::new, OverworldBlockEntity::new, BiomeTags.IS_OVERWORLD);

    public static final WorldType NETHER = of("nether", "nether_portal", "nether_portal", NetherPortalBlock::new, NetherBlockEntity::new, BiomeTags.IS_NETHER);

    public static final WorldType END = of("end", "end_portal", "end_portal", EndPortalBlock::new, EndBlockEntity::new, BiomeTags.IS_END);


    private static WorldType of(String name, String blockName, String tileName, Function<Block.Properties, Block> blockSupplier, Regeneration.BESuppler<PortalTileEntity> tileSupplier, TagKey<Biome> biomeTagKey) {
        RegistryEntry<Block> block = BLOCKS.register(blockName, blockSupplier, UnaryOperator.identity());
        return new WorldType(name, block, ITEMS.registerBlockItem(blockName, block, UnaryOperator.identity()), TILES.register(tileName, () -> Regeneration.createBEType(tileSupplier, block.getHolder().value())), biomeTagKey);
    }

    @Override
    public void afterRegistered(ICreativeTabRegister register) {
        register.addItemToCreativeTab(CreativeTabs.FUNCTIONAL_BLOCKS, List.of(OVERWORLD.getItem(), NETHER.getItem(), END.getItem()));
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

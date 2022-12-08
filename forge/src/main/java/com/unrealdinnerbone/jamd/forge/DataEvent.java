package com.unrealdinnerbone.jamd.forge;

import com.mojang.datafixers.util.Pair;
import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class DataEvent {

    public static void onData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, new Recipe(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(true, new BlockState(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new Item(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new LootTable(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new Tag(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
//        event.getGenerator().addProvider(true, new CodecTypedGenerator<>(event.getGenerator(), JAMD.MOD_ID, ForgeRegistries.Keys.BIOMES, Biome.DIRECT_CODEC));
//        event.getGenerator().addProvider(true, new CodecTypedGenerator<>(event.getGenerator(), JAMD.MOD_ID, Registry.DIMENSION_TYPE_REGISTRY, DimensionType.DIRECT_CODEC));
    }

    public static class Tag extends BlockTagsProvider {

        public Tag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
            super(output, lookupProvider, JAMD.MOD_ID, fileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(JAMDRegistry.MINE_PORTAL_BLOCK.get());
            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(JAMDRegistry.MINE_PORTAL_BLOCK.get());
        }
    }

    public static class LootTable extends LootTableProvider {

        public LootTable(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
            super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(BlockLootTable::new, LootContextParamSets.BLOCK)));
        }

        public static class BlockLootTable extends BlockLootSubProvider {


            protected BlockLootTable() {
                super(Set.of(), FeatureFlags.REGISTRY.allFlags());
            }


            @Override
            protected void generate() {
                dropSelf(JAMDRegistry.MINE_PORTAL_BLOCK.get());
            }

            protected Iterable<Block> getKnownBlocks() {
                return Collections.singleton(JAMDRegistry.MINE_PORTAL_BLOCK.get());
            }
        }

        @Override
        protected void validate(Map<ResourceLocation, net.minecraft.world.level.storage.loot.LootTable> map, ValidationContext validationtracker) {

        }
    }

    public static class Item extends net.minecraftforge.client.model.generators.ItemModelProvider {

        public Item(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, JAMD.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(JAMDRegistry.MINE_PORTAL_BLOCK.get());
            cubeAll(id.getPath(),new ResourceLocation(JAMD.MOD_ID, "block/mine_portal_block"));
        }


    }

    public static class BlockState extends BlockStateProvider {

        public BlockState(DataGenerator gen, ExistingFileHelper exFileHelper) {
            super(gen, JAMD.MOD_ID, exFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(JAMDRegistry.MINE_PORTAL_BLOCK.get());
        }
    }

    public static class Recipe extends RecipeProvider {

        Recipe(PackOutput packOutput) {
            super(packOutput);
        }


        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.MINE_PORTAL_BLOCK_ITEM::get)
                    .pattern("OOO")
                    .pattern("OEO")
                    .pattern("OOO")
                    .define('O', Tags.Items.OBSIDIAN)
                    .define('E', Items.DIAMOND_PICKAXE)
                    .unlockedBy("has_pick", has(Items.DIAMOND_PICKAXE))
                    .unlockedBy("has_obsidian", has(Items.OBSIDIAN))
                    .save(consumer);
        }

    }
}

package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.trenzalore.lib.RLUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new Provider(registryLookup, exporter);
    }

    @Override
    public String getName() {
        return "";
    }


    public static class Provider extends net.minecraft.data.recipes.RecipeProvider {

        protected Provider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        public void buildRecipes() {
            shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.OVERWORLD.getBlock().get())
                    .pattern("OOO")
                    .pattern("OPO")
                    .pattern("OOO")
                    .define('O', Blocks.OBSIDIAN)
                    .define('P', Items.DIAMOND_PICKAXE)
                    .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                    .save(output, createRecipeKey("portal_block"));
            shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.NETHER.getBlock().get())
                    .pattern("OOO")
                    .pattern("OPO")
                    .pattern("OOO")
                    .define('O', Blocks.NETHER_BRICKS)
                    .define('P', Items.NETHERITE_PICKAXE)
                    .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                    .save(output, createRecipeKey("nether_portal_block"));
            shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.END.getBlock().get())
                    .pattern("OOO")
                    .pattern("OPO")
                    .pattern("OOO")
                    .define('O', Blocks.END_STONE)
                    .define('P', Items.DIAMOND_PICKAXE)
                    .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                    .save(output, createRecipeKey("end_portal_block"));
        }
    }

    public static ResourceKey<Recipe<?>> createRecipeKey(String name) {
        return ResourceKey.create(Registries.RECIPE, JAMD.rl(name));
    }
}

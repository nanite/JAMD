package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.trenzalore.lib.IDUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new net.minecraft.data.recipes.RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.OVERWORLD.getBlock().getEntry().get())
                        .pattern("OOO")
                        .pattern("OPO")
                        .pattern("OOO")
                        .define('O', Blocks.OBSIDIAN)
                        .define('P', Items.DIAMOND_PICKAXE)
                        .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                        .save(output, ResourceKey.create(Registries.RECIPE, IDUtils.id(JAMD.MOD_ID, "portal_block")));
                shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.NETHER.getBlock().getEntry().get())
                        .pattern("OOO")
                        .pattern("OPO")
                        .pattern("OOO")
                        .define('O', Blocks.NETHER_BRICKS)
                        .define('P', Items.NETHERITE_PICKAXE)
                        .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                        .save(output, ResourceKey.create(Registries.RECIPE, IDUtils.id(JAMD.MOD_ID, "nether_portal_block")));
                shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.END.getBlock().getEntry().get())
                        .pattern("OOO")
                        .pattern("OPO")
                        .pattern("OOO")
                        .define('O', Blocks.END_STONE)
                        .define('P', Items.DIAMOND_PICKAXE)
                        .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                        .save(output, ResourceKey.create(Registries.RECIPE, IDUtils.id(JAMD.MOD_ID, "end_portal_block")));
            }
        };
    }

    @Override
    public String getName() {
        return "RECIPES GO BRR";
    }
}

package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.PORTAL_BLOCK_ITEM.get())
                .pattern("OOO")
                .pattern("OPO")
                .pattern("OOO")
                .define('O', Blocks.OBSIDIAN)
                .define('P', Items.DIAMOND_PICKAXE)
                .unlockedBy("has_diamond_pick", has(Items.ENDER_PEARL))
                .save(exporter, new ResourceLocation(JAMD.MOD_ID, "portal_block"));
    }
}

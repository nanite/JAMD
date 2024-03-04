package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
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
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.OVERWORLD.getBlock().get())
                .pattern("OOO")
                .pattern("OPO")
                .pattern("OOO")
                .define('O', Blocks.OBSIDIAN)
                .define('P', Items.DIAMOND_PICKAXE)
                .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                .save(exporter, new ResourceLocation(JAMD.MOD_ID, "portal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.NETHER.getBlock().get())
                .pattern("OOO")
                .pattern("OPO")
                .pattern("OOO")
                .define('O', Blocks.NETHER_BRICKS)
                .define('P', Items.NETHERITE_PICKAXE)
                .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                .save(exporter, new ResourceLocation(JAMD.MOD_ID, "nether_portal_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, JAMDRegistry.END.getBlock().get())
                .pattern("OOO")
                .pattern("OPO")
                .pattern("OOO")
                .define('O', Blocks.END_STONE)
                .define('P', Items.DIAMOND_PICKAXE)
                .unlockedBy("has_diamond_pick", has(Items.DIAMOND_PICKAXE))
                .save(exporter, new ResourceLocation(JAMD.MOD_ID, "end_portal_block"));
    }
}

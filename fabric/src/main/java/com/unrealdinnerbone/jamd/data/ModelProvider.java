package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.createTrivialCube(JAMDRegistry.PORTAL_BLOCK.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
//        itemModelGenerator.generateFlatItem(JAGSRegistry.GRASS_SEED.get(), ModelTemplates.FLAT_ITEM);
    }
}

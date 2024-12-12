package com.unrealdinnerbone.jamd.client;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.createTrivialCube(JAMDRegistry.OVERWORLD.getBlock().get());
        generator.createTrivialCube(JAMDRegistry.NETHER.getBlock().get());
        generator.createTrivialCube(JAMDRegistry.END.getBlock().get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
//        itemModelGenerator.generateFlatItem(JAGSRegistry.GRASS_SEED.get(), ModelTemplates.FLAT_ITEM);
    }
}

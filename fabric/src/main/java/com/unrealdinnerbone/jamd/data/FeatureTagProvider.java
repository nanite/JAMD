package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class FeatureTagProvider extends FabricTagsProvider<ConfiguredFeature<?, ?>> {

    public FeatureTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.CONFIGURED_FEATURE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        builder(JAMDRegistry.OVERWORLD.getIgnoredFeatures())
                .add(OreFeatures.ORE_INFESTED);

    }
}

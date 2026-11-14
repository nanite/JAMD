package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class PlaceTagProvider extends FabricTagsProvider<PlacedFeature> {

    public PlaceTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.PLACED_FEATURE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        builder(JAMDRegistry.OVERWORLD.getIgnoredPlaceFeatures())
                .add(OrePlacements.ORE_CLAY)
                .add(OrePlacements.ORE_GRAVEL)
                .add(OrePlacements.ORE_DIRT);

    }
}

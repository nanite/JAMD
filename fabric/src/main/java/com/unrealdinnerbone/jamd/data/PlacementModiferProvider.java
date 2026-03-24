package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.concurrent.CompletableFuture;

public class PlacementModiferProvider extends FabricTagsProvider<PlacementModifierType<?>> {

    public PlacementModiferProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.PLACEMENT_MODIFIER_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        builder(JAMDRegistry.OVERWORLD.getIngoredPlacementModifier())
                .addOptional(ResourceKey.create(Registries.PLACEMENT_MODIFIER_TYPE, Identifier.fromNamespaceAndPath("create", "config_filter")));

    }
}

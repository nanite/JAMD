package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootSubProvider {

    public LootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(JAMDRegistry.OVERWORLD.getBlock().get());
        dropSelf(JAMDRegistry.NETHER.getBlock().get());
        dropSelf(JAMDRegistry.END.getBlock().get());
    }
}

package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {


    public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(JAMDRegistry.OVERWORLD.getBlock().get())
                .add(JAMDRegistry.NETHER.getBlock().get())
                .add(JAMDRegistry.END.getBlock().get());
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(JAMDRegistry.OVERWORLD.getBlock().get())
                .add(JAMDRegistry.NETHER.getBlock().get())
                .add(JAMDRegistry.END.getBlock().get());

    }
}

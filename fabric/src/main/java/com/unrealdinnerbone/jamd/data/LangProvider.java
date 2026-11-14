package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LangProvider extends FabricLanguageProvider {

    public LangProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(JAMDRegistry.OVERWORLD.getBlock().getEntry().get(), "Mining Portal");
        translationBuilder.add(JAMDRegistry.NETHER.getBlock().getEntry().get(), "Nether Mining Portal");
        translationBuilder.add(JAMDRegistry.END.getBlock().getEntry().get(), "End Mining Portal");
        translationBuilder.add(JAMDRegistry.OVERWORLD.getItem().getEntry().get(), "Mining Portal");
        translationBuilder.add(JAMDRegistry.NETHER.getItem().getEntry().get(), "Nether Mining Portal");
        translationBuilder.add(JAMDRegistry.END.getItem().getEntry().get(), "End Mining Portal");
        translationBuilder.add("jamd.invalid.world", "Unable to find world '%s'");
        translationBuilder.add("jamd.invalid.pos", "Unable to find valid portal location");
        translationBuilder.add("biome.jamd.mining", "Mining");
        translationBuilder.add("biome.jamd.nether", "Nether Mining");
        translationBuilder.add("biome.jamd.end", "End Mining");
        translationBuilder.add("dimension.jamd.mining", "Mining");
        translationBuilder.add("dimension.jamd.nether", "Nether Mining");
        translationBuilder.add("dimension.jamd.end", "End Mining");
        translationBuilder.add("jamd.argument.world_type.invalid", "Invalid World Type '%s'");
        translationBuilder.add(JAMDRegistry.OVERWORLD.getAdvancementTitleKey(), "To Infinity and Underground");
        translationBuilder.add(JAMDRegistry.OVERWORLD.getAdvancementDescriptionKey(), "Enter the Mining Dimension");
    }
}

package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class LangProvider extends FabricLanguageProvider {

    protected LangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(JAMDRegistry.OVERWORLD.block().get(), "Mining Portal");
        translationBuilder.add(JAMDRegistry.NETHER.block().get(), "Nether Mining Portal");
        translationBuilder.add(JAMDRegistry.END.block().get(), "End Mining Portal");
        translationBuilder.add("jamd.invalid.world", "Unable to find world '%s'");
        translationBuilder.add("jamd.invalid.pos", "Unable to find valid portal location");
        translationBuilder.add("biome.jamd.mining", "Mining");
        translationBuilder.add("biome.jamd.nether_mining", "Nether Mining");
    }
}

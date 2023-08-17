package com.unrealdinnerbone.jamd.biome;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDForgeRegistry;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.JamdConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import org.slf4j.Logger;

import java.util.List;

public class JAMDBiomeModifier implements BiomeModifier
{
    public static final JAMDBiomeModifier INSTANCE = new JAMDBiomeModifier();

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.MODIFY && biome.is(JAMDRegistry.Keys.BIOME)) {
            JamdConfig config = JAMD.CONFIG.get();
            if(config.dynamicOreAddition()) {
                HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = VanillaRegistries.createLookup().lookup(Registries.PLACED_FEATURE).orElseThrow();
                placedFeatureRegistryLookup.listElements().forEach(placedFeature -> {
                    PlacedFeature s = placedFeature.get();
                    boolean isOreFeature = s.feature().get().feature() instanceof OreFeature;
                    boolean isBlacklisted = config.blackListedOres().contains(placedFeature.key().location().toString());
                    boolean isWhiteListed = config.addValues().contains(placedFeature.key().location().toString());
                    if(isBlacklisted && isWhiteListed) {
                        LOGGER.warn("Ore Feature: " + placedFeature.key() + " is both blacklisted and whitelisted. This is not allowed");
                    }else {
                        if (isWhiteListed || (isOreFeature && !isBlacklisted)) {
                            List<Holder<PlacedFeature>> features = builder.getGenerationSettings().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
                            if (features.stream().noneMatch(holder -> holder.is(placedFeature.key()))) {
                                LOGGER.debug("Adding Ore Feature: " + placedFeature.key());
                                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
                            } else {
                                LOGGER.debug("Skipping ore feature as it already exists: " + placedFeature.key());
                            }
                        }

                    }
                });
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return JAMDForgeRegistry.BIOME_MODIFER.get();
    }
}
package com.unrealdinnerbone.jamd.world;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unrealdinnerbone.jamd.JAMD;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CustomFlatLevelGeneratorSettings extends FlatLevelGeneratorSettings {

    public static final Codec<CustomFlatLevelGeneratorSettings> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Biome.CODEC
                            .fieldOf("biome")
                            .forGetter(FlatLevelGeneratorSettings::getBiome),
                    FlatLayerInfo.CODEC.listOf().fieldOf("layers").forGetter(CustomFlatLevelGeneratorSettings::getLayersInfo))
                    .apply(instance, instance.stable(CustomFlatLevelGeneratorSettings::create)));

    public static CustomFlatLevelGeneratorSettings create(Holder<Biome> holder, List<FlatLayerInfo> layersInfo) {
          return new CustomFlatLevelGeneratorSettings(holder, layersInfo);
    }

    @Override
    public BiomeGenerationSettings adjustGenerationSettings(Holder<Biome> holder) {
        if(!holder.equals(this.biome)) {
            return holder.value().getGenerationSettings();
        }
        BiomeGenerationSettings.PlainBuilder plainBuilder = new BiomeGenerationSettings.PlainBuilder();
        Optional<ResourceKey<Biome>> biomeResourceKey = this.biome.unwrapKey();
        if(biomeResourceKey.isPresent()) {
            for (PlacedFeature registeredFeature : JAMD.REGISTERED_FEATURES.get(biomeResourceKey.get()).values()) {
                plainBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(registeredFeature));
            }
        }
        return plainBuilder.build();
    }

    public CustomFlatLevelGeneratorSettings( Holder<Biome> holder, List<FlatLayerInfo> infos) {
        super(Optional.empty(), holder, List.of());
        getLayersInfo().clear();
        getLayersInfo().addAll(infos);
        updateLayers();
    }

}

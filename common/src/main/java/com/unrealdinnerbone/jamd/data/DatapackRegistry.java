package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.OptionalLong;

public class DatapackRegistry {
    public static void bootstrapDimTypes(BootstapContext<DimensionType> context) {
        context.register(ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(JAMD.MOD_ID, "mining")), createMiningDimType());

    }

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> holderGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(ResourceKey.create(Registries.BIOME, new ResourceLocation(JAMD.MOD_ID, "mining")), createBiome(holderGetter, holderGetter2));
    }

    public static void bootstrapDimension(BootstapContext<Level> context) {

    }

    private static Biome createBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers);
        BiomeDefaultFeatures.addDefaultCrystalFormations(settings);
        BiomeDefaultFeatures.addDefaultOres(settings);
        BiomeDefaultFeatures.addExtraEmeralds(settings);
        settings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
        return new Biome.BiomeBuilder()
                .temperature(1)
                .downfall(0.4f)
                .precipitation(Biome.Precipitation.NONE)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(8103167)
                        .fogColor(12638463)
                        .waterColor(4445678)
                        .waterFogColor(270131)
                        .build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .generationSettings(settings.build())
                .build();
    }

    private static DimensionType createMiningDimType() {
        return new DimensionType(OptionalLong.of(6000),
                true,
                false,
                false,
                true,
                1.0D,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0F,
                new DimensionType.MonsterSettings(false,
                        false,
                        UniformInt.of(0, 7), 0));
    }
}

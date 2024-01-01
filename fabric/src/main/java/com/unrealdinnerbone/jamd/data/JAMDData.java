package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.OptionalLong;

public class JAMDData implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(DRP::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(LangProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(LootTableProvider::new);
        pack.addProvider(AdvancementProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DIMENSION_TYPE, this::bootstrapDimensionTypes);
        registryBuilder.add(Registries.BIOME, this::bootstrapBiomes);
    }

    private void bootstrapBiomes(BootstapContext<Biome> context) {
        Overworld.bootstrapBiome(context);
        Nether.bootstrapBiome(context);
        End.bootstrapBiome(context);
    }

    private void bootstrapDimensionTypes(BootstapContext<DimensionType> context) {
        Overworld.bootstrapDimensionType(context);
        Nether.bootstrapDimensionType(context);
        End.bootstrapDimensionType(context);
    }

    public static class End {
        static void bootstrapBiome(BootstapContext<Biome> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredWorldCarvers);
            context.register(JAMDRegistry.END.getKey().biome(), new Biome.BiomeBuilder()
                    .temperature(0.5f)
                    .downfall(0.5f)
                    .hasPrecipitation(false)
                    .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                    .specialEffects(new BiomeSpecialEffects.Builder()
                            .skyColor(0)
                            .fogColor(10518688)
                            .waterColor(4159204)
                            .waterFogColor(329011)
                            .build())
                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
            context.register(JAMDRegistry.END.getKey().dimensionType(), new DimensionType(OptionalLong.of(6000),
                    false,
                    false,
                    false,
                    false,
                    1.0D,
                    false,
                    false,
                    0,
                    256,
                    256,
                    BlockTags.INFINIBURN_END,
                    BuiltinDimensionTypes.END_EFFECTS,
                    1.0F,
                    new DimensionType.MonsterSettings(false,
                            false,
                            UniformInt.of(0, 7), 0)));
        }
    }

    public static class Overworld {
        static void bootstrapBiome(BootstapContext<Biome> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredWorldCarvers);
            BiomeDefaultFeatures.addDefaultOres(builder);
            BiomeDefaultFeatures.addExtraEmeralds(builder);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
            context.register(JAMDRegistry.OVERWORLD.getKey().biome(), new Biome.BiomeBuilder()
                    .temperature(1)
                    .downfall(0.4f)
                    .hasPrecipitation(false)
                    .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                    .specialEffects(new BiomeSpecialEffects.Builder()
                            .skyColor(8103167)
                            .fogColor(12638463)
                            .waterColor(4445678)
                            .waterFogColor(270131)
                            .build())
                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
            context.register(JAMDRegistry.OVERWORLD.getKey().dimensionType(), new DimensionType(OptionalLong.of(6000),
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
                            UniformInt.of(0, 7), 0)));
        }
    }

    public static class Nether {
        static void bootstrapBiome(BootstapContext<Biome> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredWorldCarvers);
            BiomeDefaultFeatures.addNetherDefaultOres(builder);
            context.register(JAMDRegistry.NETHER.getKey().biome(), new Biome.BiomeBuilder()
                    .hasPrecipitation(false)
                    .temperature(2.0F)
                    .downfall(0.0F)
                    .specialEffects(new BiomeSpecialEffects.Builder()
                            .waterColor(4159204)
                            .waterFogColor(329011)
                            .fogColor(3344392)
                            .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
                            .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                            .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
                            .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
                            .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES)).build())
                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
            context.register(JAMDRegistry.NETHER.getKey().dimensionType(), new DimensionType(OptionalLong.of(18000),
                    false,
                    false,
                    true,
                    false,
                    1.0D,
                    false,
                    true,
                    0,
                    256,
                    128,
                    BlockTags.INFINIBURN_NETHER,
                    BuiltinDimensionTypes.NETHER_EFFECTS,
                    1.0F,
                    new DimensionType.MonsterSettings(false,
                            false,
                            UniformInt.of(0, 7), 0)));
        }
    }
}

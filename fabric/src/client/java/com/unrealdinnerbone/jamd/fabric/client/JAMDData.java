package com.unrealdinnerbone.jamd.fabric.client;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.data.AdvancementProvider;
import com.unrealdinnerbone.jamd.data.BlockTagProvider;
import com.unrealdinnerbone.jamd.data.DRP;
import com.unrealdinnerbone.jamd.data.FeatureTagProvider;
import com.unrealdinnerbone.jamd.data.LangProvider;
import com.unrealdinnerbone.jamd.data.LootTableProvider;
import com.unrealdinnerbone.jamd.data.PlaceTagProvider;
import com.unrealdinnerbone.jamd.data.PlacementModiferProvider;
import com.unrealdinnerbone.jamd.data.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.CommonColors;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.AmbientAdditionsSettings;
import net.minecraft.world.attribute.AmbientMoodSettings;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

import java.util.Optional;
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
        pack.addProvider(FeatureTagProvider::new);
        pack.addProvider(PlaceTagProvider::new);
        pack.addProvider(PlacementModiferProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DIMENSION_TYPE, this::bootstrapDimensionTypes);
        registryBuilder.add(Registries.BIOME, this::bootstrapBiomes);
    }

    private void bootstrapBiomes(BootstrapContext<Biome> context) {
        Overworld.bootstrapBiome(context);
        Nether.bootstrapBiome(context);
        End.bootstrapBiome(context);
    }

    private void bootstrapDimensionTypes(BootstrapContext<DimensionType> context) {
        Overworld.bootstrapDimensionType(context);
        Nether.bootstrapDimensionType(context);
        End.bootstrapDimensionType(context);
    }

    public static class End {
        static void bootstrapBiome(BootstrapContext<Biome> context) {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> configuredWorldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredWorldCarvers);
            context.register(JAMDRegistry.END.getKey().biome(), new Biome.BiomeBuilder()
                    .temperature(0.5f)
                    .downfall(0.5f)
                    .hasPrecipitation(false)
                    .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                    .specialEffects(new BiomeSpecialEffects.Builder()
//                            .skyColor(0)
//                            .fogColor(10518688)
                            .waterColor(4159204)
//                            .waterFogColor(329011)
                            .build())
                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
            HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
            HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);

            context.register(JAMDRegistry.END.getKey().dimensionType(), new DimensionType(
                    true,
                    true,
                    false,
                    false,
                    1.0D,
                    0,
                    256,
                    256,
                    BlockTags.INFINIBURN_END,
                    0.25F,
                    new DimensionType.MonsterSettings(ConstantInt.of(15), 0),
                    DimensionType.Skybox.END,
                    CardinalLighting.Type.DEFAULT,
                    EnvironmentAttributeMap.builder()
                            .set(EnvironmentAttributes.FOG_COLOR, -15199464)
                            .set(EnvironmentAttributes.SKY_LIGHT_COLOR, -5480243)
                            .set(EnvironmentAttributes.SKY_COLOR, -16777216)
                            .set(EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.0F)
                            .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, CommonColors.WHITE)
                            .set(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(Musics.END))
                            .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
                            .set(EnvironmentAttributes.BED_RULE, BedRule.EXPLODES)
                            .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                            .build(),
                    timelines.getOrThrow(TimelineTags.IN_END),
                    Optional.of(clocks.getOrThrow(WorldClocks.THE_END))));
        }
    }

    public static class Overworld {
        static void bootstrapBiome(BootstrapContext<Biome> context) {
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
                            .grassColorOverride(11983713)
                            .waterColor(4445678)
                            .build())
                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
            HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
            HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);
            EnvironmentAttributeMap overworldAttributes = EnvironmentAttributeMap.builder()
                    .set(EnvironmentAttributes.FOG_COLOR, -4138753)
                    .set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(0.8F))
                    .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, CommonColors.WHITE)
                    .set(EnvironmentAttributes.BED_RULE, new BedRule(BedRule.Rule.NEVER, BedRule.Rule.ALWAYS, false, Optional.empty()))
                    .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                    .set(EnvironmentAttributes.SKY_LIGHT_FACTOR, 1.0F)
                    .set(EnvironmentAttributes.FOG_START_DISTANCE, 256F)
                    .set(EnvironmentAttributes.FOG_END_DISTANCE, 256F)
                    .build();

            context.register(JAMDRegistry.OVERWORLD.getKey().dimensionType(), new DimensionType(
                    true,
                    true,
                    false,
                    false,
                    1.0D,
                    -64,
                    384,
                    384,
                    BlockTags.INFINIBURN_OVERWORLD,
                    16F,
                    new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                    DimensionType.Skybox.OVERWORLD,
                    CardinalLighting.Type.DEFAULT,
                    overworldAttributes,
                    timelines.getOrThrow(TimelineTags.UNIVERSAL),
                    Optional.of(clocks.getOrThrow(WorldClocks.OVERWORLD))));
        }
    }

    public static class Nether {
        static void bootstrapBiome(BootstrapContext<Biome> context) {
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
                                    .build()
//                            .waterFogColor(329011)
//                            .fogColor(3344392)
//                            .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
//                            .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
//                            .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
//                            .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
//                            .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES)).build())
//                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    )
                            .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                    .generationSettings(builder.build())
                    .build());
        }

        static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
            HolderGetter<Timeline> timelines = context.lookup(Registries.TIMELINE);
            HolderGetter<WorldClock> clocks = context.lookup(Registries.WORLD_CLOCK);

            context.register(JAMDRegistry.NETHER.getKey().dimensionType(), new DimensionType(
                    false,
                    false,
                    true,
                    false,
                    1.0D,
                    0,
                    256,
                    128,
                    BlockTags.INFINIBURN_NETHER,
                    0.1F,
                    new DimensionType.MonsterSettings(ConstantInt.of(7), 15),
                    DimensionType.Skybox.NONE,
                    CardinalLighting.Type.NETHER,
                    EnvironmentAttributeMap.builder()
                            .set(EnvironmentAttributes.FOG_START_DISTANCE, 10.0F)
                            .set(EnvironmentAttributes.FOG_END_DISTANCE, 96.0F)
                            .set(EnvironmentAttributes.SKY_LIGHT_COLOR, Timelines.NIGHT_SKY_LIGHT_COLOR)
                            .set(EnvironmentAttributes.SKY_LIGHT_LEVEL, 4.0F)
                            .set(EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.0F)
                            .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, CommonColors.WHITE)
                            .set(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, ParticleTypes.DRIPPING_DRIPSTONE_LAVA)
                            .set(EnvironmentAttributes.BED_RULE, BedRule.EXPLODES)
                            .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, true)
                            .set(EnvironmentAttributes.WATER_EVAPORATES, true)
                            .set(EnvironmentAttributes.FAST_LAVA, true)
                            .set(EnvironmentAttributes.PIGLINS_ZOMBIFY, false)
                            .set(EnvironmentAttributes.CAN_START_RAID, false)
                            .set(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
                            .build(),
                    timelines.getOrThrow(TimelineTags.IN_NETHER),
                    Optional.empty()));
        }
    }
}

//package com.unrealdinnerbone.jamd;
//
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.levelgen.GenerationStep;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.function.Consumer;
//
//public record ModConfig(String modid, List<DimensionSettings> dimensions) {
//    public record DimensionSettings(String biome, List<GenerationSettings> settings) {
//
//        public record GenerationSettings(String decoration, List<String> values) {
//
//            public static GenerationSettings of(GenerationStep.Decoration decoration, List<String> values) {
//                return new GenerationSettings(decoration.name().toLowerCase(), values);
//            }
//        }
//    }
//
//    //create builder
//    public static class Builder {
//        private final String modid;
//        private final List<DimensionSettings> dimensions;
//
//        public Builder(String modid) {
//            this.modid = modid;
//            this.dimensions = new ArrayList<>();
//        }
//
//        public Builder biome(ResourceKey<Biome> biomeResourceKey, Consumer<BiomeSettingsBuilder> builder) {
//            BiomeSettingsBuilder dimensionSettingsBuilder = new BiomeSettingsBuilder(biomeResourceKey);
//            builder.accept(dimensionSettingsBuilder);
//            dimensions.add(dimensionSettingsBuilder.build());
//            return this;
//        }
//
//        public ModConfig build() {
//            return new ModConfig(modid, dimensions);
//        }
//
//    }
//    public static Builder builder(String modid) {
//        return new Builder(modid);
//    }
//
//    public static class BiomeSettingsBuilder {
//        private final String biome;
//        private final List<DimensionSettings.GenerationSettings> settings;
//
//        public BiomeSettingsBuilder(ResourceKey<Biome> biomeResourceKey) {
//            this.biome = biomeResourceKey.location().toString();
//            this.settings = new ArrayList<>();
//        }
//
//        public BiomeSettingsBuilder settings(GenerationStep.Decoration decoration, Consumer<GenerationSettingsBuilder> builder) {
//            GenerationSettingsBuilder generationSettingsBuilder = new GenerationSettingsBuilder(decoration);
//            builder.accept(generationSettingsBuilder);
//            settings.add(generationSettingsBuilder.build());
//            return this;
//        }
//
//        public DimensionSettings build() {
//            return new DimensionSettings(biome, settings);
//        }
//
//    }
//    public static class GenerationSettingsBuilder {
//        private final String decoration;
//        private final List<String> values;
//
//        public GenerationSettingsBuilder(GenerationStep.Decoration decoration) {
//            this.decoration = decoration.getSerializedName();
//            this.values = new ArrayList<>();
//        }
//
//        public GenerationSettingsBuilder values(List<String> values) {
//            this.values.addAll(values);
//            return this;
//        }
//
//        public GenerationSettingsBuilder values(String... values) {
//            Collections.addAll(this.values, values);
//            return this;
//        }
//
//        public DimensionSettings.GenerationSettings build() {
//            return new DimensionSettings.GenerationSettings(decoration, values);
//        }
//
//    }
//}

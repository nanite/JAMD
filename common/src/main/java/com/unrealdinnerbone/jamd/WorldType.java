package com.unrealdinnerbone.jamd;

import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.jamd.api.FeatureTypeRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WorldType {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldType.class);

    public static final List<WorldType> TYPES = new ArrayList<>();
    private final String name;
    private final KeySet key;
    private final RegistryEntry<Block> block;
    private final RegistryEntry<BlockItem> item;
    private final RegistryEntry<BlockEntityType<PortalTileEntity>> blockEntity;

    private final TagKey<ConfiguredFeature<?, ?>> ingoredFeatures;
    private final Path configPath;

    private final TagKey<Biome> biomeTag;

    public WorldType(String name, RegistryEntry<Block> block, RegistryEntry<BlockItem> item, RegistryEntry<BlockEntityType<PortalTileEntity>> blockEntity, TagKey<Biome> biomeTag) {
        this.name = name;
        this.key = KeySet.of(new ResourceLocation(JAMD.MOD_ID, name));
        this.block = block;
        this.item = item;
        this.blockEntity = blockEntity;
        this.biomeTag = biomeTag;
        this.ingoredFeatures = TagKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(JAMD.MOD_ID, name));
        this.configPath = JAMD.CONFIG_FOLDER.resolve(name + ".json");
        TYPES.add(this);
    }

    public RegistryEntry<Block> getBlock() {
        return block;
    }

    public RegistryEntry<BlockEntityType<PortalTileEntity>> getBlockEntity() {
        return blockEntity;
    }

    public RegistryEntry<BlockItem> getItem() {
        return item;
    }

    public KeySet getKey() {
        return key;
    }

    public TagKey<ConfiguredFeature<?, ?>> getIgnoredFeatures() {
        return ingoredFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldType worldType = (WorldType) o;
        return Objects.equals(name, worldType.name);
    }

    public String getName() {
        return name;
    }

    public TagKey<Biome> getBiomeTag() {
        return biomeTag;
    }

    public Path getConfigPath() {
        return configPath;
    }

    public void exportIfNotExist(MinecraftServer server) throws IllegalStateException, IOException {
        if (!Files.exists(configPath)) {
            export(server);
        }
    }

    public void export(MinecraftServer server) throws IllegalStateException, IOException {
        List<String> loggedTypes = new ArrayList<>();
        List<OresCodec> oresCodecs = new ArrayList<>();
        List<PlacedFeature> placedFeatures = getFeatures(server);
        for (PlacedFeature placedFeature : placedFeatures) {
            if (!placedFeature.feature().is(ingoredFeatures)) {
                RegistryAccess.Frozen frozen = server.registryAccess();
                ConfiguredFeature<?, ?> configuredFeatureReference = placedFeature.feature().value();
                Feature<?> feature1 = configuredFeatureReference.feature();
                ResourceLocation key1 = frozen.registryOrThrow(Registries.FEATURE).getKey(feature1);
                FeatureTypeRegistry.getFeatureType(key1).ifPresentOrElse(iFeatureTypeCompact -> {
                    try {
                        oresCodecs.add(iFeatureTypeCompact.getOreCodec(configuredFeatureReference.config(), placedFeature.placement()));
                    } catch (ClassCastException e) {
                        LOGGER.error("Failed to parse ore Wrong Ore Config?", e);
                    } catch (Exception e) {
                        LOGGER.error("Failed to parse ore", e);
                    }

                }, () -> {
                    if (!loggedTypes.contains(key1.toString())) {
                        loggedTypes.add(key1.toString());
                        LOGGER.debug("No Feature Mapping for: {}", key1);
                    }
                });
            }

            DataResult<JsonElement> result = ConfigCodec.CODEC.encodeStart(JsonOps.INSTANCE, new ConfigCodec(1, false, oresCodecs));
            if (result.result().isPresent()) {
                if (!Files.exists(JAMD.CONFIG_FOLDER)) {
                    Files.createDirectories(JAMD.CONFIG_FOLDER);
                }
                Files.writeString(configPath, GSON.toJson(result.result().get()));
            } else {
                throw new IllegalStateException(result.error().get().message());
            }
        }
    }

    public String getAdvancementTitleKey() {
        return "advancements." + JAMD.MOD_ID + "." + name + ".title";
    }

    public String getAdvancementDescriptionKey() {
        return "advancements." + JAMD.MOD_ID + "." + name + ".description";
    }


    public List<PlacedFeature> getFeatures(MinecraftServer server) {
        List<PlacedFeature> features = new ArrayList<>();
        HolderLookup.RegistryLookup<Biome> biomeRegistryLookup = server.registryAccess().lookup(Registries.BIOME).orElseThrow();
        for (Holder.Reference<Biome> biomeReference : biomeRegistryLookup.listElements().toList()) {
            Set<PlacedFeature> placedFeatures = biomeReference.value().getGenerationSettings().featureSet.get();
            for (PlacedFeature placedFeature : placedFeatures) {
                if (biomeReference.is(biomeTag)) {
                    if (!features.contains(placedFeature)) {
                        features.add(placedFeature);
                    }
                }
            }
        }
        return features;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    public record KeySet(ResourceKey<Level> level, ResourceKey<DimensionType> dimensionType, ResourceKey<Biome> biome) {

        private static KeySet of(ResourceLocation id) {
            return new KeySet(ResourceKey.create(Registries.DIMENSION, id), ResourceKey.create(Registries.DIMENSION_TYPE, id), ResourceKey.create(Registries.BIOME, id));
        }
    }
}

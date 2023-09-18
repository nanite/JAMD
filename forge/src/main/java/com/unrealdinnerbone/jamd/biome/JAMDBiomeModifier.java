package com.unrealdinnerbone.jamd.biome;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.unrealdinnerbone.config.config.ListConfig;
import com.unrealdinnerbone.config.exception.ConfigException;
import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDForge;
import com.unrealdinnerbone.jamd.JAMDForgeRegistry;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.trenzalore.api.config.ConfigManger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.*;
import java.util.regex.Pattern;

public class JAMDBiomeModifier implements BiomeModifier
{

    public static final JAMDBiomeModifier INSTANCE = new JAMDBiomeModifier();

    private static final Logger LOGGER = LogUtils.getLogger();
    private final List<Pattern> matches;
    private final boolean dynamicOreAddition;
    public JAMDBiomeModifier() {
        try {
            dynamicOreAddition = JAMD.CONFIG.getDynamicOreAdditionConfig().getExceptionally();
            matches = Arrays.stream(JAMD.CONFIG.getBlackListedOresConfig().getExceptionally())
                    .filter(Objects::nonNull)
                    .map(Pattern::compile)
                    .toList();
        } catch (ConfigException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(dynamicOreAddition) {
            if (phase == Phase.ADD) {
                if(biome.is(JAMDRegistry.Keys.OVERWORLD.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
                    handle(placedFeatureRegistryLookup, builder);
                }else if(biome.is(JAMDRegistry.Keys.NETHER.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
//                    List<String> strings = JAMD.CONFIG.get().blackListNether();
                    handle(placedFeatureRegistryLookup, builder);
                }else if(biome.is(JAMDRegistry.Keys.END.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
//                    List<String> strings = JAMD.CONFIG.get().blackListEnd();
                    handle(placedFeatureRegistryLookup, builder);
                }
            }
        }
    }

    private void handle(HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        placedFeatureRegistryLookup.listElements().forEach(placedFeature -> {
            if (!matches(placedFeature.key().location().toString())) {
                PlacedFeature s = placedFeature.get();
                boolean isOreFeature = s.feature().get().feature() instanceof OreFeature;
                if (isOreFeature) {
                    LOGGER.debug("Adding: " + placedFeature.key().location());
                    List<Holder<PlacedFeature>> features = builder.getGenerationSettings().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
                    if (features.stream().noneMatch(holder -> holder.is(placedFeature.key()))) {
                        builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
                    }
                }
            }else {
                LOGGER.debug("Blacklisted: " + placedFeature.key().location());
            }
        });
    }

    private boolean matches(String value) {
        return matches.stream().anyMatch(pattern -> pattern.matcher(value).matches());

    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return JAMDForgeRegistry.BIOME_MODIFER.get();
    }
}
package com.unrealdinnerbone.jamd.biome;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDForge;
import com.unrealdinnerbone.jamd.JAMDForgeRegistry;
import com.unrealdinnerbone.jamd.JAMDRegistry;
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

import java.util.List;
import java.util.Map;

public record JAMDBiomeModifier() implements BiomeModifier
{

    public static final JAMDBiomeModifier INSTANCE = new JAMDBiomeModifier();

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(JAMD.CONFIG.get().dynamicOreAddition()) {
            if (phase == Phase.ADD) {
                List<String> strings = JAMD.CONFIG.get().blackListedOres();
                if(biome.is(JAMDRegistry.Keys.OVERWORLD.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
                    handle(strings, placedFeatureRegistryLookup, builder);
                }else if(biome.is(JAMDRegistry.Keys.NETHER.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
//                    List<String> strings = JAMD.CONFIG.get().blackListNether();
                    handle(strings, placedFeatureRegistryLookup, builder);
                }else if(biome.is(JAMDRegistry.Keys.END.biome())) {
                    HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
//                    List<String> strings = JAMD.CONFIG.get().blackListEnd();
                    handle(strings, placedFeatureRegistryLookup, builder);
                }
            }
        }
    }

    private static void handle(List<String> strings, HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        placedFeatureRegistryLookup.listElements().forEach(placedFeature -> {
            if(!placedFeature.key().location().getNamespace().equalsIgnoreCase("enlightened_end")) {
                if(!contains(strings, placedFeature.key().location().toString())) {
                    PlacedFeature s = placedFeature.get();
                    boolean isOreFeature = s.feature().get().feature() instanceof OreFeature;
                    if (isOreFeature) {
                        List<Holder<PlacedFeature>> features = builder.getGenerationSettings().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
                        if (features.stream().noneMatch(holder -> holder.is(placedFeature.key()))) {
                            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
                        }
                    }
                }
            }
        });
    }

    private static boolean contains(List<String> values, String value) {
        if(value.endsWith("*")) {
            String substring = value.substring(0, value.length() - 1);
            return values.stream().anyMatch(s -> s.toLowerCase().startsWith(substring.toLowerCase()));
        }else {
            return values.stream().anyMatch(s -> s.equalsIgnoreCase(value));
        }

    }

    public static void main(String[] args) {
        System.out.println(contains(List.of("enlightened_end:adamantite_node"), "enlightened_end:*"));
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return JAMDForgeRegistry.BIOME_MODIFER.get();
    }
}
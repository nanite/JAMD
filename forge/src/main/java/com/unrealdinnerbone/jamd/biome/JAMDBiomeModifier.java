package com.unrealdinnerbone.jamd.biome;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.JAMD;
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

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && biome.is(JAMDRegistry.Keys.BIOME)) {
            HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistryLookup = ServerLifecycleHooks.getCurrentServer().registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow();
            List<String> strings = JAMD.CONFIG.get().blackListedOres();
            placedFeatureRegistryLookup.listElements().forEach(placedFeature -> {
                if(!strings.contains(placedFeature.key().location().toString())) {
                    PlacedFeature s = placedFeature.get();
                    boolean isOreFeature = s.feature().get().feature() instanceof OreFeature;
                    if (isOreFeature) {
                        List<Holder<PlacedFeature>> features = builder.getGenerationSettings().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
                        if (features.stream().noneMatch(holder -> holder.is(placedFeature.key()))) {
                            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature);
                        }
                    }
                }
            });
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return JAMDForgeRegistry.BIOME_MODIFER.get();
    }
}
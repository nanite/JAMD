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
import net.minecraft.resources.ResourceLocation;
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

    public JAMDBiomeModifier() {

    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {

    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return JAMDForgeRegistry.BIOME_MODIFER.get();
    }
}
package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.jamd.biome.JAMDBiomeModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JAMDDataForge
{

    private static final ResourceKey<BiomeModifier> ADD_SELF = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(JAMD.MOD_ID, "add_self"));

    private static final RegistrySetBuilder builder = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, JAMDDataForge::createBiomeModifiers);

    public static void onData(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<BiomeModifiers>) output -> new BiomeModifiers(output, event.getLookupProvider()));

    }

    private static void createBiomeModifiers(BootstapContext<BiomeModifier> bootstapContext) {
        bootstapContext.register(ADD_SELF, JAMDBiomeModifier.INSTANCE);
    }

    private static class BiomeModifiers extends DatapackBuiltinEntriesProvider
    {

        public BiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries, builder, Set.of(JAMD.MOD_ID));
        }
    }



}

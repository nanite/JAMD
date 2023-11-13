package com.unrealdinnerbone.jamd.biome;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.JAMDForgeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class JAMDBiomeModifier implements BiomeModifier {
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
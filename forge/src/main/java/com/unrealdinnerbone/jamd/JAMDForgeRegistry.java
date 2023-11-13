package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.biome.JAMDBiomeModifier;
import com.unrealdinnerbone.trenzalore.api.platform.services.IRegistry;
import com.unrealdinnerbone.trenzalore.api.registry.Regeneration;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryObjects;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class JAMDForgeRegistry implements IRegistry {
    private static final RegistryObjects<Codec<? extends BiomeModifier>> BIOME_MODIFERS = Regeneration.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<Codec<JAMDBiomeModifier>> BIOME_MODIFER = BIOME_MODIFERS.register(JAMD.MOD_ID, () -> Codec.unit(JAMDBiomeModifier.INSTANCE));

    @Override
    public List<RegistryObjects<?>> getRegistryObjects() {
        return List.of(BIOME_MODIFERS);
    }

    @Override
    public String getModID() {
        return JAMD.MOD_ID;
    }
}

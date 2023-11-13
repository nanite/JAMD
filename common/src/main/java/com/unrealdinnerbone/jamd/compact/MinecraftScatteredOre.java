package com.unrealdinnerbone.jamd.compact;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class MinecraftScatteredOre implements IFeatureTypeCompact<OreConfiguration> {

    private static final ResourceLocation FEATURE_TYPE = new ResourceLocation("minecraft", "scattered_ore");

    @Override
    public OresCodec parse(OreConfiguration value, List<PlacementModifier> placementModifiers) {
        return new OresCodec(value.size, value.discardChanceOnAirExposure, placementModifiers, value.targetStates);
    }

    @Override
    public Codec<OreConfiguration> getCodec() {
        return OreConfiguration.CODEC;
    }

    @Override
    public ResourceLocation getFeatureType() {
        return FEATURE_TYPE;
    }
}

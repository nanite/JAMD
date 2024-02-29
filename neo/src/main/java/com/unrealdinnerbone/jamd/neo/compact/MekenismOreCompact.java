package com.unrealdinnerbone.jamd.neo.compact;

import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import mekanism.common.world.ResizableOreFeatureConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class MekenismOreCompact implements IFeatureTypeCompact<ResizableOreFeatureConfig> {

    private static final ResourceLocation FEATURE_TYPE = new ResourceLocation("mekanism", "ore");

    @Override
    public OresCodec parse(ResizableOreFeatureConfig config, List<PlacementModifier> placementModifiers) {
        return new OresCodec(config.size().getAsInt(), config.discardChanceOnAirExposure().getAsFloat(), placementModifiers, config.targetStates());
    }

    @Override
    public Codec<ResizableOreFeatureConfig> getCodec() {
        return ResizableOreFeatureConfig.CODEC;
    }

    @Override
    public ResourceLocation getFeatureType() {
        return FEATURE_TYPE;
    }
}

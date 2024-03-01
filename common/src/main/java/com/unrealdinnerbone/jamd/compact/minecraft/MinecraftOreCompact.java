package com.unrealdinnerbone.jamd.compact.minecraft;

import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class MinecraftOreCompact implements IFeatureTypeCompact<OreConfiguration> {
    @Override
    public OresCodec parse(OreConfiguration value, List<PlacementModifier> placementModifiers) {
        return new OresCodec(value.size, value.discardChanceOnAirExposure, placementModifiers, value.targetStates);
    }

}

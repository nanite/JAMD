package com.unrealdinnerbone.jamd.neo.compact;

import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import mekanism.common.world.ResizableOreFeatureConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class MekenismOreCompact implements IFeatureTypeCompact<ResizableOreFeatureConfig> {

    @Override
    public OresCodec parse(ResizableOreFeatureConfig config, List<PlacementModifier> placementModifiers) {
        return new OresCodec(config.size().getAsInt(), config.discardChanceOnAirExposure().getAsFloat(), placementModifiers, config.targetStates());
    }

}

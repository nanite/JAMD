package com.unrealdinnerbone.jamd.neo.compact;

import blusunrize.immersiveengineering.common.world.IEOreFeature;
import com.mojang.serialization.Codec;
import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class IECompact implements IFeatureTypeCompact<IEOreFeature.IEOreFeatureConfig> {

    public static final ResourceLocation ID = new ResourceLocation("immersiveengineering", "ie_ore");

    @Override
    public OresCodec parse(IEOreFeature.IEOreFeatureConfig value, List<PlacementModifier> placementModifiers) {
        return new OresCodec(value.getSize(), (float) value.getAirExposure(), placementModifiers, value.targetList());
    }


}

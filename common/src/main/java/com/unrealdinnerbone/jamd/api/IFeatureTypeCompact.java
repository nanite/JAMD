package com.unrealdinnerbone.jamd.api;

import com.unrealdinnerbone.jamd.OresCodec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public interface IFeatureTypeCompact<T extends Feature> {

    default OresCodec getOreCodec(Feature feature, List<PlacementModifier> modifiers) throws IllegalArgumentException {
        return parse((T) feature, modifiers);
    }

    /**
     * @param value              the parsed ore config codec
     * @param placementModifiers the placement modifiers to create ore codec
     * @return the ore codec
     */
    OresCodec parse(T value, List<PlacementModifier> placementModifiers);

}

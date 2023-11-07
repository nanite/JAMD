package com.unrealdinnerbone.jamd.api;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.jamd.OresCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public interface IFeatureTypeCompact<T>
{

    default OresCodec getOreCodec(JsonObject config, List<PlacementModifier> modifiers) throws IllegalArgumentException {
        DataResult<T> parse = getCodec().parse(JsonOps.INSTANCE, config);
        if(parse.error().isPresent()) {
            throw new IllegalArgumentException(parse.error().get().message());
        } else {
            return parse(parse.result().get(), modifiers);
        }
    }

    /**
     * @return The ore config codec
     */
    Codec<T> getCodec();

    /**
     * @param value the parsed ore config codec
     * @param placementModifiers the placement modifiers to create ore codec
     * @return the ore codec
     */
    OresCodec parse(T value, List<PlacementModifier> placementModifiers);

    /**
     * @return feature type id
     */
    ResourceLocation getFeatureType();
}

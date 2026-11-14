package com.unrealdinnerbone.jamd.util;

import com.unrealdinnerbone.jamd.OresCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class Transformers {

    public static PlacedFeature fromConfigCodec(OresCodec oresCodec, boolean ignoreAirChance) {
        OreFeature oreFeatureType = new OreFeature(oresCodec.targets(), oresCodec.oreSize(), ignoreAirChance ? 0 : oresCodec.discardChance());
        return new PlacedFeature(Holder.direct(oreFeatureType), oresCodec.modifiers());
    }
}

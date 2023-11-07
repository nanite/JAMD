package com.unrealdinnerbone.jamd.util;

import com.unrealdinnerbone.jamd.OresCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.List;

public class Transformers {

    public static PlacedFeature fromConfigCodec(OresCodec oresCodec) {
        ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> oreFeature = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(oresCodec.targets(), oresCodec.oreSize(), oresCodec.discardChance()));
        return new PlacedFeature(Holder.direct(oreFeature), oresCodec.modifiers());
    }
}

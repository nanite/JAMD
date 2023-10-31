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
        List<PlacementModifier> placementModifiers = new ArrayList<>();
        placementModifiers.add(CountPlacement.of(oresCodec.oreAmount()));
        placementModifiers.add(InSquarePlacement.spread());
        VerticalAnchor min = oresCodec.minY().isPresent() ? VerticalAnchor.absolute(oresCodec.minY().get()) : VerticalAnchor.bottom();
        VerticalAnchor max = oresCodec.maxY().isPresent() ? VerticalAnchor.absolute(oresCodec.maxY().get()) : VerticalAnchor.top();
        HeightRangePlacement modifier = switch (oresCodec.placementType()) {
            case UNIFORM -> HeightRangePlacement.uniform(min, max);
            case TRIANGLE -> HeightRangePlacement.triangle(min, max);
        };
        placementModifiers.add(modifier);

        placementModifiers.add(BiomeFilter.biome());
        return new PlacedFeature(Holder.direct(oreFeature), placementModifiers);
    }
}

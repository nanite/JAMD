package com.unrealdinnerbone.jamd.neo.compact;

import com.unrealdinnerbone.jamd.OresCodec;
import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public class PBCompact implements IFeatureTypeCompact<ReplaceBlockConfiguration> {

    @Override
    public OresCodec parse(ReplaceBlockConfiguration value, List<PlacementModifier> placementModifiers) {
        List<PlacementModifier> newPlacementModifiers = new ArrayList<>(placementModifiers);
        newPlacementModifiers.add(CountPlacement.of(2));
        newPlacementModifiers.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60)));
        newPlacementModifiers.add(InSquarePlacement.spread());
        newPlacementModifiers.add(BiomeFilter.biome());
        return new OresCodec(4, 0, newPlacementModifiers, value.targetStates);
    }
}

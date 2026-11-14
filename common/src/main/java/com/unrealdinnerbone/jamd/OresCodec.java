package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public record OresCodec(int oreSize, float discardChance, List<PlacementModifier> modifiers,
                        List<BlockReplacement> targets) {
    public static final Codec<OresCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("ore_size").forGetter(OresCodec::oreSize),
            Codec.FLOAT.fieldOf("discard_on_air_chance").forGetter(OresCodec::discardChance),
            Codec.list(PlacementModifier.CODEC).fieldOf("modifiers").forGetter(OresCodec::modifiers),
            Codec.list(BlockReplacement.CODEC).fieldOf("targets").forGetter(OresCodec::targets)
    ).apply(instance, OresCodec::new));

}
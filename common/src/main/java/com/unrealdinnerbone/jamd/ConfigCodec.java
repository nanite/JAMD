package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record ConfigCodec(int oreMultiplier, Boolean ignoreAirChance, List<OresCodec> ores) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("oreMultiplier").forGetter(ConfigCodec::oreMultiplier),
            Codec.BOOL.optionalFieldOf("ignoreAirChance", false).forGetter(ConfigCodec::ignoreAirChance),
            Codec.list(OresCodec.CODEC).fieldOf("ores").forGetter(ConfigCodec::ores)
    ).apply(instance, ConfigCodec::new));
}

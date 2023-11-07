package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record ConfigCodec(int oreMultiplier, List<OresCodec> ores) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("oreMultiplier").forGetter(ConfigCodec::oreMultiplier),
            Codec.list(OresCodec.CODEC).fieldOf("ores").forGetter(ConfigCodec::ores)
    ).apply(instance, ConfigCodec::new));
}

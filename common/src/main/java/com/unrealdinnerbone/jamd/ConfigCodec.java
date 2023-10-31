package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record ConfigCodec(List<OresCodec> ores) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(OresCodec.CODEC).fieldOf("ores").forGetter(ConfigCodec::ores)
    ).apply(instance, ConfigCodec::new));
}

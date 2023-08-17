package com.unrealdinnerbone.jamd.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class CustomFlatLevelSource extends FlatLevelSource {

    public static final Codec<CustomFlatLevelSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(FlatLevelGeneratorSettings.CODEC
                            .fieldOf("settings")
                            .forGetter(CustomFlatLevelSource::settings))
                    .apply(instance, instance.stable(CustomFlatLevelSource::new)));

    public CustomFlatLevelSource(FlatLevelGeneratorSettings settings) {
        super(settings);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    public int getMinY() {
        return -64;
    }


}

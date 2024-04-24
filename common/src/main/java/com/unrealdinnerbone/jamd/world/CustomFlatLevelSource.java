package com.unrealdinnerbone.jamd.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import org.jetbrains.annotations.NotNull;

public class CustomFlatLevelSource extends FlatLevelSource {

    private final CustomFlatLevelGeneratorSettings mySettings;

    public static final MapCodec<CustomFlatLevelSource> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(CustomFlatLevelGeneratorSettings.CODEC
                            .fieldOf("settings")
                            .forGetter(CustomFlatLevelSource::settings))
                    .apply(instance, instance.stable(CustomFlatLevelSource::new)));

    public CustomFlatLevelSource(CustomFlatLevelGeneratorSettings settings) {
        super(settings);
        this.mySettings = settings;
    }

    @Override
    @NotNull
    public CustomFlatLevelGeneratorSettings settings() {
        return mySettings;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    public int getMinY() {
        return -64;
    }


}

package com.unrealdinnerbone.jamd.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.jetbrains.annotations.NotNull;

public class CustomFlatLevelSource extends FlatLevelSource {

    private final CustomFlatLevelGeneratorSettings mySettings;

    public static final Codec<CustomFlatLevelSource> CODEC = RecordCodecBuilder.create((instance) ->
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
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    public int getMinY() {
        return -64;
    }


}

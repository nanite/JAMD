package com.unrealdinnerbone.jamd;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record OresCodec(
        String id,
        int oreSize,
        int oreAmount,
        float discardChance,
        Optional<Integer> minY,
        Optional<Integer> maxY,
        PlacementType placementType,
        List<OreConfiguration.TargetBlockState> targets) {
    public static final Codec<OresCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(OresCodec::id),
            Codec.INT.fieldOf("ore_size").forGetter(OresCodec::oreSize),
            Codec.INT.fieldOf("ore_amount").forGetter(OresCodec::oreAmount),
            Codec.FLOAT.fieldOf("discard_on_air_chance").forGetter(OresCodec::discardChance),
            Codec.INT.optionalFieldOf("min_y").forGetter(OresCodec::minY),
            Codec.INT.optionalFieldOf("max_y").forGetter(OresCodec::maxY),
            PlacementType.CODEC.fieldOf("placement_type").forGetter(OresCodec::placementType),
            Codec.list(OreConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter(OresCodec::targets)
    ).apply(instance, OresCodec::new));


    public static enum PlacementType implements StringRepresentable {
        UNIFORM("uniform"),
        TRIANGLE("triangle"),
        ;

        public static final Codec<PlacementType> CODEC = StringRepresentable.fromEnum(PlacementType::values);
        private final String name;

        PlacementType(String name) {
            this.name = name;
        }


        @Override
        @NotNull
        public String getSerializedName() {
            return name;
        }
    }
}

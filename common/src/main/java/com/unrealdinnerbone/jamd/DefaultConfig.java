//package com.unrealdinnerbone.jamd;
//
//import net.minecraft.world.level.levelgen.GenerationStep;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DefaultConfig
//{
//    public static final List<ModConfig> CONFIGS = new ArrayList<>();
//
//    static {
//        CONFIGS.add(ModConfig.builder("minecraft")
//                .biome(JAMDRegistry.Keys.BIOME, builder -> builder
//                        .settings(GenerationStep.Decoration.UNDERGROUND_ORES,
//                                generation -> generation.values(
//                                        "minecraft:ore_coal_upper",
//                                        "minecraft:ore_coal_lower",
//                                        "minecraft:ore_iron_upper",
//                                        "minecraft:ore_iron_middle",
//                                        "minecraft:ore_iron_small",
//                                        "minecraft:ore_gold",
//                                        "minecraft:ore_gold_lower",
//                                        "minecraft:ore_redstone",
//                                        "minecraft:ore_redstone_lower",
//                                        "minecraft:ore_diamond",
//                                        "minecraft:ore_diamond_large",
//                                        "minecraft:ore_diamond_buried",
//                                        "minecraft:ore_lapis",
//                                        "minecraft:ore_lapis_buried",
//                                        "minecraft:ore_copper",
//                                        "minecraft:underwater_magma",
//                                        "minecraft:ore_emerald",
//                                        "minecraft:ore_copper_large",
//                                        "minecraft:ore_tuff"))
//                        .settings(GenerationStep.Decoration.LOCAL_MODIFICATIONS, generation ->
//                                generation.values("minecraft:amethyst_geode"))
//                )
//                .build());
//    }
//}

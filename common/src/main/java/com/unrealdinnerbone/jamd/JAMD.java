package com.unrealdinnerbone.jamd;

import com.google.common.base.Suppliers;
import com.unrealdinnerbone.trenzalore.api.config.ConfigManger;

import java.util.List;
import java.util.function.Supplier;

public class JAMD {
    public static final String MOD_ID = "jamd";

    private static final List<String> DEFAULT_BLACKLIST = List.of(
            "minecraft:ore_gold_deltas",
            "minecraft:ore_infested",
            "minecraft:ore_andesite_lower",
            "minecraft:ore_magma",
            "minecraft:ore_gravel",
            "minecraft:ore_granite_lower",
            "minecraft:ore_diorite_lower",
            "minecraft:ore_diorite_upper",
            "minecraft:ore_soul_sand",
            "minecraft:ore_granite_upper",
            "minecraft:ore_gold_extra",
            "minecraft:ore_quartz_nether",
            "minecraft:ore_gold_nether",
            "minecraft:ore_quartz_deltas",
            "minecraft:ore_gravel_nether",
            "minecraft:ore_dirt",
            "minecraft:ore_blackstone",
            "minecraft:ore_andesite_upper",
            "minecraft:ore_clay"

    );
    public static final Supplier<JamdConfig> CONFIG = Suppliers.memoize(() -> ConfigManger.getOrCreateConfig(JAMD.MOD_ID, JamdConfig.class, () -> new JamdConfig(true, DEFAULT_BLACKLIST, List.of())))::get;


    public static void init() {

    }

}
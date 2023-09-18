package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.config.ConfigCreator;
import com.unrealdinnerbone.config.config.BooleanConfig;
import com.unrealdinnerbone.config.config.ListConfig;

public class JamdConfig {

    private final BooleanConfig dynamicOreAdditionConfig;
    private final ListConfig<String> blackListedOresConfig;
    public JamdConfig(ConfigCreator configCreator) {
        dynamicOreAdditionConfig = configCreator.createBoolean("dynamicOreAddition", true);
        blackListedOresConfig = configCreator.createList("blackListedOres", new String[]{
                        "minecraft:ore_gold_deltas",
                        "minecraft:ore_infested",
                        "minecraft:ore_magma",
                        "minecraft:ore_gravel",
                        "minecraft:ore_soul_sand",
                        "minecraft:ore_gold_extra",
                        "minecraft:ore_quartz_nether",
                        "minecraft:ore_gold_nether",
                        "minecraft:ore_quartz_deltas",
                        "minecraft:ore_gravel_nether",
                        "minecraft:ore_dirt",
                        "minecraft:ore_blackstone",
                        "minecraft:ore_andesite_upper",
                        "minecraft:ore_clay",
                        "enlightened_end:.*"
                }, String[].class);
    }

    public BooleanConfig getDynamicOreAdditionConfig() {
        return dynamicOreAdditionConfig;
    }

    public ListConfig<String> getBlackListedOresConfig() {
        return blackListedOresConfig;
    }
}

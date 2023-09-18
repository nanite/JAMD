package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.config.ConfigManager;
import com.unrealdinnerbone.trenzalore.api.config.ConfigManger;

public class JAMD {

    public static final String MOD_ID = "jamd";
    private static final ConfigManager CONFIG_MANAGER = ConfigManger.createConfigManager(MOD_ID);
    public static final JamdConfig CONFIG = CONFIG_MANAGER.loadConfig("general", JamdConfig::new);

    public static void init() {

    }

}
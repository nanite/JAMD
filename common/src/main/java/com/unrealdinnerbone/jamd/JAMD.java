package com.unrealdinnerbone.jamd;

import com.mojang.logging.LogUtils;
import com.unrealdinnerbone.jamd.api.FeatureTypeRegistry;
import com.unrealdinnerbone.jamd.compact.MinecraftOreCompact;
import com.unrealdinnerbone.jamd.compact.MinecraftScatteredOre;
import com.unrealdinnerbone.jamd.util.OreRegistry;
import com.unrealdinnerbone.trenzalore.api.platform.Services;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class JAMD {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Path CONFIG_FOLDER = Services.PLATFORM.getConfigPath().resolve("jamd");
    public static final String MOD_ID = "jamd";

    public static void init() {
        FeatureTypeRegistry.register("minecraft", MinecraftOreCompact::new);
        FeatureTypeRegistry.register("minecraft", MinecraftScatteredOre::new);
    }

    public static void onServerStart(MinecraftServer server) {
        OreRegistry.REGISTERED_FEATURES.clear();
        for (WorldType type : WorldType.TYPES) {
            try {
                type.exportIfNotExist(server);
            } catch (IOException e) {
                LOGGER.error("Failed to export config for world type: {}", type.getName(), e);
            }
        }
    }

}
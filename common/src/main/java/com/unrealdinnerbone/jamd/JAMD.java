package com.unrealdinnerbone.jamd;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.jamd.util.Transformers;
import com.unrealdinnerbone.trenzalore.api.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JAMD {

    private static final Logger LOGGER = LoggerFactory.getLogger(JAMD.class);

    public static final Path CONFIG_FOLDER = Services.PLATFORM.getConfigPath().resolve("jamd");
    public static final String MOD_ID = "jamd";
    public static final Map<ResourceKey<Biome>, Map<String, PlacedFeature>> REGISTERED_FEATURES = new HashMap<>();


    private static final List<String> DIM_TYPES = Arrays.asList("mining", "nether", "end");

    public static void init() {
        try {
            createFiles();
        }catch (IOException e) {
            LOGGER.error("Failed to create config files", e);
            return;
        }
        for (String dimType : DIM_TYPES) {
            try {
                String jsonString = Files.readString(CONFIG_FOLDER.resolve(dimType + ".json"));
                JsonElement parse = new JsonParser().parse(jsonString);
                DataResult<ConfigCodec> data = ConfigCodec.CODEC.parse(JsonOps.INSTANCE, parse);
                if(data.error().isPresent()) {
                    LOGGER.error("Failed to parse config: {}", data.error().get().message());
                    return;
                }
                Optional<ConfigCodec> result = data.result();
                if(result.isPresent()) {
                    Map<String, PlacedFeature> placedFeatureMap = new HashMap<>();
                    ConfigCodec configCodec = result.get();
                    for (OresCodec ore : configCodec.ores()) {
                        if(placedFeatureMap.containsKey(ore.id())) {
                            LOGGER.error("Duplicate ore: {} found in config {}", ore.id(), dimType);
                            continue;
                        }
                        placedFeatureMap.put(ore.id(), Transformers.fromConfigCodec(ore));
                        LOGGER.debug("Registered ore: {} for dim type: {}", ore.id(), dimType);
                    }
                    REGISTERED_FEATURES.put(ResourceKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, dimType)), placedFeatureMap);
                }
            }catch (IOException e) {
                LOGGER.error("Failed to load config for dim type: {}", dimType, e);
            }

        }
    }

    private static void createFiles() throws IOException {
        if(!Files.exists(CONFIG_FOLDER)) {
            Files.createDirectories(CONFIG_FOLDER);
        }
        for (String dimType : DIM_TYPES) {
            Path path = CONFIG_FOLDER.resolve(dimType   + ".json");
            if(!Files.exists(path)) {
                InputStream defaultConfig = JAMD.class.getResourceAsStream("/defaults/" + dimType + ".json");
                if(defaultConfig != null) {
                    Files.copy(defaultConfig, path);
                }else {
                    throw new IOException("Failed to find default config for: " + dimType);
                }
            }
        }
    }


}
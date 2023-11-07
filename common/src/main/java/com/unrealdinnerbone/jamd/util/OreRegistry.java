package com.unrealdinnerbone.jamd.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.jamd.*;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class OreRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<WorldType, List<PlacedFeature>> REGISTERED_FEATURES = new HashMap<>();

    public static List<PlacedFeature> getFeatures(Holder<Biome> biomeHolder) {
        if(biomeHolder.is(JAMDRegistry.OVERWORLD.getKey().biome())) {
            return getFeatures(JAMDRegistry.OVERWORLD);
        }else if(biomeHolder.is(JAMDRegistry.NETHER.getKey().biome())) {
            return getFeatures(JAMDRegistry.NETHER);
        }else if(biomeHolder.is(JAMDRegistry.END.getKey().biome())) {
            return getFeatures(JAMDRegistry.END);
        }
        return Collections.emptyList();
    }
    public static List<PlacedFeature> getFeatures(WorldType type) {
        if(!REGISTERED_FEATURES.containsKey(type)) {
            try {
                Path resolve = JAMD.CONFIG_FOLDER.resolve(type.getName() + ".json");
                if(!Files.exists(resolve)) {
                    return Collections.emptyList();
                }
                String jsonString = Files.readString(resolve);
                JsonElement parse = new JsonParser().parse(jsonString);
                DataResult<ConfigCodec> data = ConfigCodec.CODEC.parse(JsonOps.INSTANCE, parse);
                if(data.error().isPresent()) {
                    LOGGER.error("Failed to parse config: {}", data.error().get().message());
                    return Collections.emptyList();
                }
                Optional<ConfigCodec> result = data.result();
                if(result.isPresent()) {
                    List<PlacedFeature> features = new ArrayList<>();
                    ConfigCodec configCodec = result.get();
                    for (int i = 0; i < configCodec.oreMultiplier(); i++) {
                        for (OresCodec ore : configCodec.ores()) {
                            features.add(Transformers.fromConfigCodec(ore));
                        }
                    }
                    REGISTERED_FEATURES.put(type, features);
                }else {
                    LOGGER.error("Failed to parse config: {}", "No result");
                    return Collections.emptyList();
                }
            }catch (IOException e) {
                LOGGER.error("Failed to read config file", e);
            }
        }
        return REGISTERED_FEATURES.get(type);
    }

}


package com.unrealdinnerbone.jamd.api;

import com.unrealdinnerbone.trenzalore.api.platform.Services;
import com.unrealdinnerbone.trenzalore.lib.IDUtils;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class FeatureTypeRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<Identifier, IFeatureTypeCompact<?>> FEATURES = new HashMap<>();

    public static void register(String modId, List<String> ids, Supplier<IFeatureTypeCompact<?>> featureTypeCompact) {
        ids.forEach(id -> register(modId, id, featureTypeCompact));
    }

    public static void register(String modId, String id, Supplier<IFeatureTypeCompact<?>> featureTypeCompact) {
        register(modId, IDUtils.id(modId, id), featureTypeCompact);
    }

    public static void register(String modId, Identifier id, Supplier<IFeatureTypeCompact<?>> featureTypeCompact) {
        if (Services.PLATFORM.isModLoaded(modId)) {
            IFeatureTypeCompact<?> iFeatureTypeCompact = featureTypeCompact.get();
            LOGGER.debug("Registering Feature {} for {}", id, modId);
            FEATURES.put(id, iFeatureTypeCompact);
        } else {
            LOGGER.debug("Skipping Feature for {} as mod is not loaded", modId);
        }
    }

    public static Optional<IFeatureTypeCompact<?>> getFeatureType(Identifier id) {
        return Optional.ofNullable(FEATURES.get(id));
    }

}

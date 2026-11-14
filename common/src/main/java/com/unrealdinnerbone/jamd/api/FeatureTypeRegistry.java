package com.unrealdinnerbone.jamd.api;

import com.unrealdinnerbone.trenzalore.api.platform.Services;
import com.unrealdinnerbone.trenzalore.lib.IDUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class FeatureTypeRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<Class<? extends Feature>, IFeatureTypeCompact<?>> FEATURES = new HashMap<>();

    public static void register(Class<? extends Feature> type, Supplier<IFeatureTypeCompact<?>> featureTypeCompact) {
        IFeatureTypeCompact<?> iFeatureTypeCompact = featureTypeCompact.get();
        FEATURES.put(type, iFeatureTypeCompact);
    }

    public static Optional<IFeatureTypeCompact<?>> getFeatureType(Class<? extends Feature> id) {
        return Optional.ofNullable(FEATURES.get(id));
    }

}

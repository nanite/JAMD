package com.unrealdinnerbone.jamd.api;

import com.unrealdinnerbone.jamd.api.IFeatureTypeCompact;
import com.unrealdinnerbone.jamd.compact.MinecraftOreCompact;
import com.unrealdinnerbone.trenzalore.api.platform.Services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FeatureTypeRegistry
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final List<IFeatureTypeCompact<?>> FEATURES = new ArrayList<>();

    public static void register(String modId, Supplier<IFeatureTypeCompact<?>> featureTypeCompact) {
        if(Services.PLATFORM.isModLoaded(modId)) {
            IFeatureTypeCompact<?> iFeatureTypeCompact = featureTypeCompact.get();
            LOGGER.debug("Registering Feature {} for {}", iFeatureTypeCompact.getFeatureType(), modId);
            FEATURES.add(iFeatureTypeCompact);
        }else {
            LOGGER.debug("Skipping Feature for {} as mod is not loaded", modId);
        }
    }

    public static Optional<IFeatureTypeCompact<?>> getFeatureType(String id) {
        return FEATURES.stream().filter(featureTypeCompact -> featureTypeCompact.getFeatureType().toString().equalsIgnoreCase(id)).findFirst();
    }

}

package com.unrealdinnerbone.jamd;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class JAMD
{
    public static final String MOD_ID = "jamd";

    public static final ResourceLocation DIM_ID = new ResourceLocation(MOD_ID, "mining");

    public static final ResourceKey<Level> MINING_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, DIM_ID);

    public static void init() {
        JAMDRegistry.REGISTRIES.forEach(DeferredRegister::register);
    }
}

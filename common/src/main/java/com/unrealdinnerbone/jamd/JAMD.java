package com.unrealdinnerbone.jamd;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;

public class JAMD
{
    public static final String MOD_ID = "jamd";

    public static final ResourceLocation DIM_ID = new ResourceLocation(MOD_ID, "mining");

    public static ResourceKey<Level> MINING_KEY;

    public static void init() {
        JAMDRegistry.REGISTRIES.forEach(DeferredRegister::register);
        MINING_KEY = ResourceKey.create(Registries.DIMENSION, DIM_ID);
    }
}

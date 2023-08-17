package com.unrealdinnerbone.jamd;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JAMD.MOD_ID)
public class JAMDForge {
    
    public JAMDForge() {
        JAMD.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(JAMDDataForge::onData);
    }
}
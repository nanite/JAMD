package com.unrealdinnerbone.jamd;

import net.fabricmc.api.ModInitializer;

public class JAMDFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        JAMD.init();
    }
}

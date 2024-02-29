package com.unrealdinnerbone.jamd.fabric;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.command.JamdCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class JAMDFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        JAMD.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> JamdCommand.register(dispatcher));
        ServerLifecycleEvents.SERVER_STARTING.register(JAMD::onServerStart);
    }

}

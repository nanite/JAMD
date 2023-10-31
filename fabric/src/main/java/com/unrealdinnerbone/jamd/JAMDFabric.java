package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.jamd.command.JamdCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class JAMDFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> JamdCommand.register(dispatcher));
    }

}

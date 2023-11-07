package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.jamd.api.FeatureTypeRegistry;
import com.unrealdinnerbone.jamd.command.JamdCommand;
import com.unrealdinnerbone.jamd.compact.MekenismOreCompact;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(JAMD.MOD_ID)
public class JAMDForge {

    public JAMDForge() {
        JAMD.init();
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addListener(this::onStart);
        registerCompact();
    }

    public static void registerCompact() {
        FeatureTypeRegistry.register("mekanism", MekenismOreCompact::new);
    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

    public void onStart(ServerStartingEvent event) {
        JAMD.onServerStart(event.getServer());
    }

}
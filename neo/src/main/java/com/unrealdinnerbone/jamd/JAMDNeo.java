package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.jamd.command.JamdCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(JAMD.MOD_ID)
public class JAMDNeo {

    public JAMDNeo() {
        JAMD.init();
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onStart);
        registerCompact();
    }

    public static void registerCompact() {
        //Todo re add compact
//        FeatureTypeRegistry.register("mekanism", MekenismOreCompact::new);
    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

    public void onStart(ServerStartingEvent event) {
        JAMD.onServerStart(event.getServer());
    }

}
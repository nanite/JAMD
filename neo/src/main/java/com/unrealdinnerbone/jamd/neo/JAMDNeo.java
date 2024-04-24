package com.unrealdinnerbone.jamd.neo;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.command.JamdCommand;
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
//        FeatureTypeRegistry.register("mekanism", "ore", MekenismOreCompact::new);
//        FeatureTypeRegistry.register("immersiveengineering", "ie_ore", IECompact::new);
    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

    public void onStart(ServerStartingEvent event) {
        JAMD.onServerStart(event.getServer());
    }

}
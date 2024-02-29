package com.unrealdinnerbone.jamd.neo;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.api.FeatureTypeRegistry;
import com.unrealdinnerbone.jamd.command.JamdCommand;
import com.unrealdinnerbone.jamd.neo.compact.IECompact;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.unrealdinnerbone.jamd.neo.compact.MekenismOreCompact;

@Mod(JAMD.MOD_ID)
public class JAMDNeo {

    public JAMDNeo() {
        JAMD.init();
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onStart);
        registerCompact();
    }

    public static void registerCompact() {
        FeatureTypeRegistry.register("mekanism", MekenismOreCompact::new);
        FeatureTypeRegistry.register("immersiveengineering", IECompact::new);
    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

    public void onStart(ServerStartingEvent event) {
        JAMD.onServerStart(event.getServer());
    }

}
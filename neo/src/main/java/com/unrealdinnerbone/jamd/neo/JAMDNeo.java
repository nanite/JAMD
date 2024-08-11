package com.unrealdinnerbone.jamd.neo;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.api.FeatureTypeRegistry;
import com.unrealdinnerbone.jamd.command.JamdCommand;
import com.unrealdinnerbone.jamd.neo.compact.MekenismOreCompact;
import com.unrealdinnerbone.jamd.neo.compact.PBCompact;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;

@Mod(JAMD.MOD_ID)
public class JAMDNeo {

    public JAMDNeo() {
        JAMD.init();
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onStart);
        registerCompact();
    }

    public static void registerCompact() {
        FeatureTypeRegistry.register("mekanism", "ore", MekenismOreCompact::new);
        FeatureTypeRegistry.register("productivebees", List.of(
                "sand_nest",
                "snow_nest",
                "stone_nest",
                "coarse_dirt_nest",
                "gravel_nest",
                "slimy_nest",
                "sugar_cane_nest",
                "glowstone_nest",
                "nether_quartz_nest",
                "nether_quartz_nest_high",
                "nether_fortress_nest",
                "soul_sand_nest",
                "end_nest",
                "obsidian_pillar_nest",
                "bumble_bee_nest"
        ), PBCompact::new);
    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

    public void onStart(ServerStartingEvent event) {
        JAMD.onServerStart(event.getServer());
    }

}
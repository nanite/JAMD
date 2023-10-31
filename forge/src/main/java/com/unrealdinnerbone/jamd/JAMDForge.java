package com.unrealdinnerbone.jamd;

import com.unrealdinnerbone.jamd.command.JamdCommand;
import com.unrealdinnerbone.trenzalore.api.config.ConfigManger;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JAMD.MOD_ID)
public class JAMDForge {
    
    public JAMDForge() {
        JAMD.init();
        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
    }

    public void onServerStart(RegisterCommandsEvent event) {
        JamdCommand.register(event.getDispatcher());
    }

}
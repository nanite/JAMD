package com.unrealdinnerbone.jamd.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.unrealdinnerbone.jamd.util.OreRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class JamdCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("jamd")
                .then(Commands.literal("reload")
                        .executes(JamdCommand::reload)));

    }

    private static int reload(CommandContext<CommandSourceStack> stackCommandContext) throws CommandSyntaxException {
        OreRegistry.REGISTERED_FEATURES.clear();
        stackCommandContext.getSource().sendSuccess(() -> Component.literal("Reloaded Biomes Data"), true);
        return 0;
    }
}

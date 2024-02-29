package com.unrealdinnerbone.jamd.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.unrealdinnerbone.jamd.WorldType;
import com.unrealdinnerbone.jamd.util.OreRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.flag.FeatureFlagSet;

import java.io.IOException;
import java.util.Collection;

public class JamdCommand {

    private static final DynamicCommandExceptionType INVALID_WORLD_TYPE = new DynamicCommandExceptionType((object) -> Component.translatable("commands.jamd.invalid_world_type", object));

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("jamd")
                        .then(Commands.literal("export")
                                .then(Commands.argument("type", StringArgumentType.string())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(WorldType.TYPES.stream().map(WorldType::getName), builder))
                                        .executes(JamdCommand::export)
                .then(Commands.literal("reload")
                        .executes(JamdCommand::reload)))));

    }

    private static int export(CommandContext<CommandSourceStack> stackCommandContext) throws CommandSyntaxException {
        String string = StringArgumentType.getString(stackCommandContext, "type");
        WorldType worldType = WorldType.TYPES.stream()
                .filter(theWorldType -> theWorldType.getName().equalsIgnoreCase(string))
                .findFirst()
                .orElseThrow(() -> INVALID_WORLD_TYPE.create(string));
        try {
            worldType.export(stackCommandContext.getSource().getLevel().getServer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stackCommandContext.getSource().sendSuccess(() -> Component.literal("Exported Biomes Data"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int reload(CommandContext<CommandSourceStack> stackCommandContext) throws CommandSyntaxException {
        OreRegistry.REGISTERED_FEATURES.clear();
        stackCommandContext.getSource().sendSuccess(() -> Component.literal("Reloaded Biomes Data"), true);
        return Command.SINGLE_SUCCESS;
    }
}

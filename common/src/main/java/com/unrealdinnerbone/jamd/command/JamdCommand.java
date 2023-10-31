package com.unrealdinnerbone.jamd.command;

import com.google.gson.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.OresCodec;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JamdCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(JamdCommand.class);
    private static final SimpleCommandExceptionType NO_FEATURES = new SimpleCommandExceptionType(() -> "Can't create placed feature lookup");

    private static final DynamicCommandExceptionType INVALID_TARGET = new DynamicCommandExceptionType((s) -> new LiteralMessage("Can't parse targets: " + s));

    private static final DynamicCommandExceptionType CANT_COVERT = new DynamicCommandExceptionType((s) -> new LiteralMessage("Can't convert ores to json: " + s));

    private static final DynamicCommandExceptionType CANT_SAVE_FILE = new DynamicCommandExceptionType((s) -> new LiteralMessage("Can't save command.json: " + s));
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("jamd")
                .then(Commands.literal("export")
                        .executes(JamdCommand::export)));

    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static int export(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
        MinecraftServer server = source.getSource().getServer();
        HolderLookup.RegistryLookup<PlacedFeature> lookup = server.registryAccess().lookup(Registries.PLACED_FEATURE).orElseThrow(NO_FEATURES::create);
        List<OresCodec> oresCodecs = new ArrayList<>();
        for (Holder.Reference<PlacedFeature> placedFeatureReference : lookup.listElements().toList()) {
            String id = placedFeatureReference.key().location().getPath();
            DataResult<JsonElement> json = PlacedFeature.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, placedFeatureReference.value());
            if(json.result().isPresent()) {
                try {
                    JsonElement jsonElement = json.result().get();
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject feature = jsonObject.getAsJsonObject("feature");
                    String featureType = GsonHelper.getAsString(feature, "type");
                    if(featureType.equals("minecraft:ore")) {
                        JsonObject config = GsonHelper.getAsJsonObject(feature, "config");
                        JsonArray targets = GsonHelper.getAsJsonArray(config, "targets");
                        DataResult<List<OreConfiguration.TargetBlockState>> result = Codec.list(OreConfiguration.TargetBlockState.CODEC).parse(JsonOps.INSTANCE, targets);
                        if(result.error().isPresent()) {
                            throw INVALID_TARGET.create(result.error().get().message());
                        }
                        List<OreConfiguration.TargetBlockState> targetBlockState = result.result().get();
                        int size = GsonHelper.getAsInt(config, "size");
                        int discord = GsonHelper.getAsInt(config, "discard_chance_on_air_exposure");
                        JsonArray placement = GsonHelper.getAsJsonArray(jsonObject, "placement");
                        Integer minY = null;
                        Integer maxY = null;
                        Integer count = null;
                        OresCodec.PlacementType placementType = null;
                        for (JsonElement element : placement) {
                            String type = GsonHelper.getAsString(element.getAsJsonObject(), "type");
                            if(type.equals("minecraft:count")) {
                                count = GsonHelper.getAsInt(element.getAsJsonObject(), "count");
                            }
                            if(type.equals("minecraft:height_range")) {
                                JsonObject height = GsonHelper.getAsJsonObject(element.getAsJsonObject(), "height");
                                JsonObject asJsonObject = GsonHelper.getAsJsonObject(height, "min_inclusive");
                                if(asJsonObject.has("absolute")) {
                                    minY = GsonHelper.getAsInt(asJsonObject, "absolute");
                                }else if(asJsonObject.has("above_bottom")) {
                                    minY = GsonHelper.getAsInt(asJsonObject, "above_bottom");
                                }
                                JsonObject asJsonObject1 = GsonHelper.getAsJsonObject(height, "max_inclusive");
                                if(asJsonObject1.has("absolute")) {
                                    maxY = GsonHelper.getAsInt(asJsonObject1, "absolute");
                                } else if(asJsonObject1.has("below_top")) {
                                    maxY = GsonHelper.getAsInt(asJsonObject1, "below_top");
                                }
                                String placementTypeString = GsonHelper.getAsString(height, "type");
                                if(placementTypeString.equals("minecraft:uniform")) {
                                    placementType = OresCodec.PlacementType.UNIFORM;
                                }else if(placementTypeString.equals("minecraft:trapezoid")) {
                                    placementType = OresCodec.PlacementType.TRIANGLE;
                                }
                            }
                        }
                        if(minY != null && maxY != null && count != null && placementType != null) {
                            oresCodecs.add(new OresCodec(id, size, count, discord, Optional.of(minY), Optional.of(maxY), placementType, targetBlockState));
                        }
                    }else {
                        if(featureType.equals("minecraft:scattered_ore")) {
                            LOGGER.info("{}", featureType);
                        }
                    }
                }catch (JsonSyntaxException e) {
                    LOGGER.error("Skipping ore: {} because of error: {}", id, e);
                }

            }
        }
        DataResult<JsonElement> result = Codec.list(OresCodec.CODEC).encodeStart(JsonOps.INSTANCE, oresCodecs);
        if(result.result().isPresent()) {
            try {
                Files.writeString(JAMD.CONFIG_FOLDER.resolve("command.json"), GSON.toJson(result.result().get()));
                source.getSource().sendSuccess(() -> Component.literal("Generated command.json"), true);
            } catch (IOException e) {
                throw CANT_SAVE_FILE.create(e.getMessage());
            }
        }else {
            throw CANT_COVERT.create(result.error().get().message());
        }
        return 0;
    }
}

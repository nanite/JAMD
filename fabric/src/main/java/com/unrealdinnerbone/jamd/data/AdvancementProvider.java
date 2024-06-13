package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.trenzalore.lib.RLUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaStoryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementProvider extends FabricAdvancementProvider {

    protected AdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider lookup, Consumer<AdvancementHolder> consumer) {
        consumer.accept(Advancement.Builder.advancement()
                        .parent(RLUtils.rl("minecraft", "story/mine_diamond"))
                        .display(
                                JAMDRegistry.OVERWORLD.getItem().get(),
                                Component.translatable(JAMDRegistry.OVERWORLD.getAdvancementTitleKey()),
                                Component.translatable(JAMDRegistry.OVERWORLD.getAdvancementDescriptionKey()),
                                null,
                                AdvancementType.TASK,
                                true,
                                true,
                                true
                        )
                        .addCriterion("enter_dimension", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(JAMDRegistry.OVERWORLD.getKey().level()))
                .build(RLUtils.rl(JAMD.MOD_ID, "enter_mining_dimension")));
    }
}

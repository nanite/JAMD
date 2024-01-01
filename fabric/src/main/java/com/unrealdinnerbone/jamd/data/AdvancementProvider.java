package com.unrealdinnerbone.jamd.data;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AdvancementProvider extends FabricAdvancementProvider {

    protected AdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<AdvancementHolder> consumer) {
        consumer.accept(Advancement.Builder.advancement()
                        .display(
                                JAMDRegistry.OVERWORLD.getItem().get(),
                                Component.translatable(JAMDRegistry.OVERWORLD.getAdvancementTitleKey()),
                                Component.translatable(JAMDRegistry.OVERWORLD.getAdvancementDescriptionKey()),
                                null,
                                AdvancementType.TASK,
                                true,
                                true,
                                false
                        )
                        .addCriterion("enter_dimension", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(JAMDRegistry.OVERWORLD.getKey().level()))
                .build(new ResourceLocation(JAMD.MOD_ID, "enter_mining_dimension")));
    }
}

package com.unrealdinnerbone.jamd.block.base;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.util.TelerportUtils;
import com.unrealdinnerbone.trenzalore.api.registry.RegistryEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class PortalBlock extends Block implements EntityBlock {



    //Todo CONFIG
    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    private final ResourceKey<Level> destinationLevel;
    private final JAMDRegistry.RegistrySet registrySet;
    public PortalBlock(ResourceKey<Level> destinationWorld, JAMDRegistry.RegistrySet registrySet) {
        super(Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.STONE).mapColor(MapColor.COLOR_BLUE));
        this.destinationLevel = destinationWorld;
        this.registrySet = registrySet;
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            if(level.dimension().equals(destinationLevel)) {
                TelerportUtils.teleport(player, OVERWORLD, pos, registrySet);
            }else if(level.dimension().equals(OVERWORLD)) {
                TelerportUtils.teleport(player, destinationLevel, pos, registrySet);
            }else {
                player.displayClientMessage(Component.literal("You can't teleport from this dimension"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }else {
            return InteractionResult.PASS;
        }
    }

}

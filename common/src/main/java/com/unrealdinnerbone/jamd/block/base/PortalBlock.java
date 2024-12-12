package com.unrealdinnerbone.jamd.block.base;

import com.unrealdinnerbone.jamd.WorldType;
import com.unrealdinnerbone.jamd.util.TelerportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class PortalBlock extends Block implements EntityBlock {


    //Todo CONFIG
    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    private final WorldType type;

    public PortalBlock(Block.Properties properties, WorldType type) {
        super(properties);
        this.type = type;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return type.getBlockEntity().get().create(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hand) {
        if (!level.isClientSide()) {
            if (level.dimension().equals(type.getKey().level())) {
                TelerportUtils.teleport(player, OVERWORLD, pos, type);
            } else if (level.dimension().equals(OVERWORLD)) {
                TelerportUtils.teleport(player, type.getKey().level(), pos, type);
            } else {
                player.displayClientMessage(Component.literal("You can't teleport from this dimension"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }

}

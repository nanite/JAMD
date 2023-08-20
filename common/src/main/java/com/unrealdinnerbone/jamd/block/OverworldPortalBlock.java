package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OverworldPortalBlock extends PortalBlock {

    public OverworldPortalBlock() {
        super(JAMDRegistry.Keys.OVERWORLD.level(), JAMDRegistry.OVERWORLD);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JAMDRegistry.OVERWORLD.blockEntity().get().create(pos, state);
    }


}

package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class OverworldBlockEntity extends PortalTileEntity {

    public OverworldBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(JAMDRegistry.OVERWORLD, blockPos, blockState);
    }
}

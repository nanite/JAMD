package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EndBlockEntity extends PortalTileEntity {

    public EndBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(JAMDRegistry.END, blockPos, blockState);
    }
}

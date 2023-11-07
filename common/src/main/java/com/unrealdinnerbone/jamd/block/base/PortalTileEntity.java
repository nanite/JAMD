package com.unrealdinnerbone.jamd.block.base;

import com.unrealdinnerbone.jamd.WorldType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PortalTileEntity extends BlockEntity {

    public PortalTileEntity(WorldType worldType, BlockPos blockPos, BlockState blockState) {
        super(worldType.getBlockEntity().get(), blockPos, blockState);
    }

}

package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PortalTileEntity extends BlockEntity {

    public PortalTileEntity(BlockPos blockPos, BlockState blockState) {
        super(JAMDRegistry.PORTAL.get(), blockPos, blockState);
    }

}

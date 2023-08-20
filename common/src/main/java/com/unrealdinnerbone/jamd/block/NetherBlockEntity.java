package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NetherBlockEntity extends PortalTileEntity {

    public NetherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(JAMDRegistry.NETHER, blockPos, blockState);
    }
}

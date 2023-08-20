package com.unrealdinnerbone.jamd.block.base;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PortalTileEntity extends BlockEntity {

    public PortalTileEntity(JAMDRegistry.RegistrySet registrySet, BlockPos blockPos, BlockState blockState) {
        super(registrySet.blockEntity().get(), blockPos, blockState);
    }

}

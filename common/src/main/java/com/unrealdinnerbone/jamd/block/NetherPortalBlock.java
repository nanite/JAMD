package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NetherPortalBlock extends PortalBlock {

    public NetherPortalBlock(BlockBehaviour.Properties properties) {
        super(properties, JAMDRegistry.NETHER);
    }

}

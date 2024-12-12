package com.unrealdinnerbone.jamd.block;

import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.base.PortalBlock;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EndPortalBlock extends PortalBlock {

    public EndPortalBlock(Block.Properties properties) {
        super(properties, JAMDRegistry.END);
    }


}

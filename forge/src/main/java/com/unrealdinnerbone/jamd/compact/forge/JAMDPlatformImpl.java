package com.unrealdinnerbone.jamd.compact.forge;

import com.unrealdinnerbone.jamd.forge.SimpleTeleporter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import org.jetbrains.annotations.Nullable;

public class JAMDPlatformImpl {

    public static @Nullable <T extends Entity> Entity teleport(T entity, ServerLevel level, PortalInfo portalInfo) {
        return entity.changeDimension(level, new SimpleTeleporter(portalInfo));
    }
}

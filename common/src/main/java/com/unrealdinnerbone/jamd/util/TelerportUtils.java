package com.unrealdinnerbone.jamd.util;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.WorldType;
import com.unrealdinnerbone.jamd.block.base.PortalTileEntity;
import com.unrealdinnerbone.trenzalore.api.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Optional;

public class TelerportUtils {
    public static void teleport(Player playerEntity, ResourceKey<Level> toWorldKey, BlockPos blockPos, WorldType registrySet) {
        ServerLevel toWorld = playerEntity.getServer().getLevel(toWorldKey);
        if (toWorld != null) {
            findPortalLocation(toWorld, blockPos, registrySet).ifPresentOrElse(portalLocation -> {
                        if (toWorld.getBlockState(portalLocation).isAir() && !toWorld.getBlockState(portalLocation.below()).is(registrySet.getBlock().get())){
                            toWorld.setBlockAndUpdate(portalLocation, registrySet.getBlock().get().defaultBlockState());
                        }
                        Vec3 portalLocationVec = new Vec3(portalLocation.getX() + 0.5, portalLocation.getY() + 1, portalLocation.getZ() + 0.5);
                        Services.PLATFORM.teleport(playerEntity, toWorld, new PortalInfo(portalLocationVec, playerEntity.getDeltaMovement(), playerEntity.getYRot(), playerEntity.getXRot()));                    },
                    () -> playerEntity.displayClientMessage(Component.translatable(JAMD.MOD_ID + ".invalid.pos"), true));

        } else {
            playerEntity.displayClientMessage(Component.translatable(JAMD.MOD_ID + ".invalid.world", toWorldKey.location().toString()), true);
        }
    }


    private static Optional<BlockPos> findPortalLocation(Level worldTo, BlockPos fromPos, WorldType registrySet) {
        if (worldTo.getBlockState(fromPos).is(registrySet.getBlock().get()) && isSafeSpawnLocation(worldTo, fromPos)) {
            return Optional.of(fromPos.above());
        }

        int range = 3;
        return Optional.ofNullable(ChunkPos.rangeClosed(worldTo.getChunkAt(fromPos).getPos(), range)
                .map(chunkPos -> worldTo.getChunk(chunkPos.x, chunkPos.z).getBlockEntitiesPos())
                .flatMap(Collection::stream).toList().stream()
                .filter(pos -> worldTo.getBlockEntity(pos).getType().equals(registrySet.getBlock().get()))
                .findFirst()
                .orElseGet(() -> {
                    BlockPos heightmapPos = worldTo.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, fromPos);
                    if(forLocationAround(worldTo, heightmapPos.mutable(), fromPos.getX(), fromPos.getZ(), heightmapPos.getY())) {
                        return heightmapPos;
                    }
                    return null;
                }));

    }

    private static boolean forLocationAround(Level levelTo, BlockPos.MutableBlockPos blockPos, int fromX, int fromZ, int y) {
        boolean locationGood = false;
        for (int x = fromX - 4; x < fromX + 4; x++) {
            if(locationGood) { break; }
            for (int z = fromZ - 4; z < fromZ + 4; z++) {
                blockPos.set(x, y, z);
                if(isSaveLocation(levelTo, blockPos)) {
                    locationGood = true;
                    break;
                }
            }
        }
        return locationGood;
    }

    private static boolean isSaveLocation(Level levelTo, BlockPos.MutableBlockPos blockPos) {
        BlockState blockState = levelTo.getBlockState(blockPos);
        return blockState.isAir() && isSafeSpawnLocation(levelTo, blockPos.above());
    }


    private static boolean isSafeSpawnLocation(Level world, BlockPos blockPos) {
        return world.isInWorldBounds(blockPos) && world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.above()).isAir();
    }


}

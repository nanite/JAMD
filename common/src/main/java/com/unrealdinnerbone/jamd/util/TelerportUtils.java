package com.unrealdinnerbone.jamd.util;

import com.unrealdinnerbone.jamd.JAMD;
import com.unrealdinnerbone.jamd.JAMDRegistry;
import com.unrealdinnerbone.jamd.block.PortalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Optional;

public class TelerportUtils {


    public static void teleport(Player playerEntity, ResourceKey<Level> toWorldKey, BlockPos blockPos) {
        ServerLevel toWorld = playerEntity.getServer().getLevel(toWorldKey);
        if (toWorld != null) {
            findPortalLocation(toWorld, blockPos).ifPresentOrElse(portalLocation -> {
                        if (toWorld.getBlockState(portalLocation).isAir()) {
                            toWorld.setBlockAndUpdate(portalLocation, JAMDRegistry.MINE_PORTAL_BLOCK.get().defaultBlockState());
                        }
                        TeleportHelper.teleport(playerEntity, toWorld, portalLocation.getX() + 0.5, portalLocation.getY() + 1, portalLocation.getZ() + 0.5);
                    },
                    () -> playerEntity.displayClientMessage(Component.translatable(JAMD.MOD_ID + ".invalid.pos"), true));

        } else {
            playerEntity.displayClientMessage(Component.translatable(JAMD.MOD_ID + ".invalid.world", toWorldKey.location().toString()), true);
        }
    }


    private static Optional<BlockPos> findPortalLocation(Level worldTo, BlockPos fromPos) {
        if(worldTo.getBlockState(fromPos).getBlock() == JAMDRegistry.MINE_PORTAL_BLOCK.get() && isSafeSpawnLocation(worldTo, fromPos)) {
            return Optional.of(fromPos.above());
        }

        int range = 5;
        return Optional.ofNullable(ChunkPos.rangeClosed(worldTo.getChunkAt(fromPos).getPos(), range)
                .map(chunkPos -> worldTo.getChunk(chunkPos.x, chunkPos.z).getBlockEntitiesPos())
                .flatMap(Collection::stream).toList().stream()
                .filter(pos -> worldTo.getBlockEntity(pos) instanceof PortalTileEntity)
                .findFirst()
                .orElseGet(() -> {
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);
                    int minY = worldTo.getMinBuildHeight();
                    int maxY = worldTo.getMaxBuildHeight();
//                        int mid = (minY + maxY);
//                        if(mid != 0) {
//                            mid = mid / 2;
//                        }
                    int start = 68;
                    for (int y = start - 1; y > minY; y--) {
                        if (forLocationAround(worldTo, mutableBlockPos, fromPos.getX(), fromPos.getZ(), y)) {
                            return mutableBlockPos;
                        }
                    }
                    for (int y = start; y < maxY; y++) {
                        if (forLocationAround(worldTo, mutableBlockPos, fromPos.getX(), fromPos.getZ(), y)) {
                            return mutableBlockPos;
                        }
                    }

                    return null;
                }));

    }

    private static boolean forLocationAround(Level levelTo, BlockPos.MutableBlockPos blockPos, int fromX, int fromZ, int y) {
        for (int x = fromX - 6; x < fromX + 6; x++) {
            for (int z = fromZ - 6; z < fromZ + 6; z++) {
                blockPos.set(x, y, z);
                if(isSaveLocation(levelTo, blockPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSaveLocation(Level levelTo, BlockPos.MutableBlockPos blockPos) {
        BlockState blockState = levelTo.getBlockState(blockPos);
        return blockState.isAir() && isSafeSpawnLocation(levelTo, blockPos.above());
    }


    private static boolean isSafeSpawnLocation(Level world, BlockPos blockPos) {
        return world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.above()).isAir();
    }


}

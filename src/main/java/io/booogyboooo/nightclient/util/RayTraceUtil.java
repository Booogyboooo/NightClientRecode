package io.booogyboooo.nightclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

public class RayTraceUtil {
	
	/**
	 * Get the entity under your crosshair
	 * <br>
	 * - getEntityUnderCrosshair()
	 */
    public static Entity getEntityUnderCrosshair() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return null;
        }

        HitResult hitResult = client.crosshairTarget;

        if (hitResult instanceof EntityHitResult entityHitResult) {
            return entityHitResult.getEntity();
        }

        return null;
    }
    
	/**
	 * Get the block side under your crosshair
	 * <br>
	 * - getBlockSideUnderCrosshair(maxDistance)
	 * <br>
	 * <br>
	 * maxDistance
	 * <br>
	 * - The max distance for the ray trace
	 */
    public static Direction getBlockSideUnderCrosshair(double maxDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return null;
        }

        HitResult hitResult = client.crosshairTarget;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            return blockHitResult.getSide();
        }

        return null;
    }
    
	/**
	 * Get the block hit result for the block under your crosshair
	 * <br>
	 * - getBlock(maxDistance)
	 * <br>
	 * <br>
	 * maxDistance
	 * <br>
	 * - The max distance for the ray trace
	 */
    public static BlockHitResult getBlock(double maxDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return null;
        }

        HitResult hitResult = client.crosshairTarget;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            return blockHitResult;
        }

        return null;
    }
}
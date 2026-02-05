package io.booogyboooo.nightclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class MouseUtil {
	public static Boolean canLeftClick = true;
	
    public static void leftClick() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client != null && client.player != null && client.interactionManager != null) {
            HitResult hitResult = client.crosshairTarget;
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                client.interactionManager.attackBlock(((BlockHitResult) hitResult).getBlockPos(), ((BlockHitResult)hitResult).getSide());
                client.player.swingHand(client.player.getActiveHand());
            } else if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                client.interactionManager.attackEntity(client.player, ((EntityHitResult) client.crosshairTarget).getEntity());
                client.player.swingHand(client.player.getActiveHand());
            } else {
                client.player.swingHand(client.player.getActiveHand());
            }
        }
    }
}
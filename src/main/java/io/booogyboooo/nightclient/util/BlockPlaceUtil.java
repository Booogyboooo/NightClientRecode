package io.booogyboooo.nightclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BlockPlaceUtil {
	
	/**
	 * Place a block at a certain BlockPos
	 * <br>
	 * - placeBlock(pos)
	 * <br>
	 * <br>
	 * pos
	 * <br>
	 * - BlockPos object
	 */
    public static void placeBlock(BlockPos pos) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        if (client.player == null || networkHandler == null || client.world == null || pos == null) {
            return;
        }
        Direction direction = Direction.UP;
        Vec3d hitVec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        BlockHitResult hitResult = new BlockHitResult(hitVec, direction, pos, false);
        PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0);
        networkHandler.sendPacket(packet);
    }
    
	/**
	 * Place a block with a certain hit res
	 * <br>
	 * - placeBlock(direction, hitResult)
	 * <br>
	 * <br>
	 * direction
	 * <br>
	 * - The Direction
	 * <br>
	 * <br>
	 * hitResult
	 * <br>
	 * - The Hit Result
	 */
    public static void placeBlock(Direction direction, BlockHitResult hitResult) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        if (client.player == null || networkHandler == null || client.world == null) {
            return;
        }
        PlayerInteractBlockC2SPacket packet = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0);
        networkHandler.sendPacket(packet);
    }
}

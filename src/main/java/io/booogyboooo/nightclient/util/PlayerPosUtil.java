package io.booogyboooo.nightclient.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/**
 * Util for interacting with the player position
 */
public class PlayerPosUtil {
	
	/**
	 * Get the block under the players BlockPos
	 * <br>
	 * - getBlockUnderPlayer()
	 */
    public static BlockPos getBlockUnderPlayer() {
    	MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.world != null) {
            Vec3d playerPos = client.player.getEntityPos();
            BlockPos blockUnderPos = new BlockPos( (int) Math.floor(playerPos.x), (int) Math.floor(playerPos.y - 1), (int) Math.floor(playerPos.z));
            ClientWorld world = client.world;
            BlockState blockStateUnder = world.getBlockState(blockUnderPos);
            if (!blockStateUnder.isAir()) {
                return blockUnderPos;
            }
        }
        return null;
    }
    
	/**
	 * Get the block under the players BlockPos
	 * <br>
	 * - getBlockUnderPlayer(allowAir)
	 * <br>
	 * <br>
	 * allowAir
	 * <br>
	 * - Can it return air
	 */
    public static BlockPos getBlockUnderPlayer(boolean allowAir) {
    	MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.world != null) {
            Vec3d playerPos = client.player.getEntityPos();
            BlockPos blockUnderPos = new BlockPos( (int) Math.floor(playerPos.x), (int) Math.floor(playerPos.y - 1), (int) Math.floor(playerPos.z));
            ClientWorld world = client.world;
            BlockState blockStateUnder = world.getBlockState(blockUnderPos);
            if (!blockStateUnder.isAir() && !allowAir) {
                return blockUnderPos;
            } else if (allowAir){
            	return blockUnderPos;
            }
        }
        return null;
    }
    
	/**
	 * Check if the player is over the void
	 * <br>
	 * - overVoid()
	 */
    public static boolean overVoid() {
    	MinecraftClient client = MinecraftClient.getInstance();
    	if (client.player == null) {
    		return false;
    	}
    	ClientPlayerEntity player = client.player;
        BlockPos.Mutable pos = new BlockPos.Mutable(player.getBlockX(), player.getBlockY(), player.getBlockZ());
        while (pos.getY() > player.getEntityWorld().getBottomY()) {
            pos.move(Direction.DOWN);
            BlockState state = player.getEntityWorld().getBlockState(pos);
            if (!state.isAir()) {
                return false;
            }
        }
        return true;
    }
}

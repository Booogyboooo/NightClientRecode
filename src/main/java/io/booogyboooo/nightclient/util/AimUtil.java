package io.booogyboooo.nightclient.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AimUtil {
	public static void lookAtEntity(PlayerEntity player, Entity target) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    double dx = target.getX() - player.getX();
	    double dy = (target.getEyeY() - player.getEyeY());
	    double dz = target.getZ() - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float yaw = (float) (Math.atan2(-dx, dz) * (180.0 / Math.PI));
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    player.setYaw(yaw);
	    player.setPitch(pitch);
	}
	
	public static float[] gatAngles(PlayerEntity player, Entity target) {
	    double dx = target.getX() - player.getX();
	    double dy = (target.getEyeY() - player.getEyeY());
	    double dz = target.getZ() - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float yaw = (float) (Math.atan2(-dx, dz) * (180.0 / Math.PI));
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    return new float[]{pitch, yaw};
	}
	
	public static void lookAtEntity(PlayerEntity player, Entity target, Boolean packet) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    double dx = target.getX() - player.getX();
	    double dy = (target.getEyeY() - player.getEyeY());
	    double dz = target.getZ() - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float yaw = (float) (Math.atan2(-dx, dz) * (180.0 / Math.PI));
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    if (packet) {
	    	MoveUtil.look(yaw, pitch);
	    } else {
		    player.setYaw(yaw);
		    player.setPitch(pitch);
	    }
	}
	
	public static void lookAtBlock(PlayerEntity player, BlockPos target, Boolean packet) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    double dx = target.getX() + 0.5 - player.getX();
	    double dy = target.getY() + 0.5 - player.getEyeY();
	    double dz = target.getZ() + 0.5 - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float yaw = (float) (Math.atan2(-dx, dz) * (180.0 / Math.PI));
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    if (packet) {
	    	MoveUtil.look(yaw, pitch);
	    } else {
		    player.setYaw(yaw);
		    player.setPitch(pitch);
	    }
	}
	
	public static void lookAtBlock(PlayerEntity player, BlockPos target, Direction side, Boolean packet) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    //nz = north
	    //pz = south
	    //nx = west
	    //px = east
	    double cx = 0.5;
	    double cy = 0.5;
	    double cz = 0.5;
	    if (side == Direction.WEST || side == Direction.EAST) {
	    	cx = side == Direction.WEST ? 0 : 1;
	    }
	    if (side == Direction.DOWN || side == Direction.UP) {
	    	cy = side == Direction.DOWN ? 0 : 1;
	    }
	    if (side == Direction.NORTH || side == Direction.SOUTH) {
	    	cz = side == Direction.NORTH ? 0 : 1;
	    }
	    double dx = target.getX() + cx - player.getX();
	    double dy = target.getY() + cy - player.getEyeY();
	    double dz = target.getZ() + cz - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float yaw = (float) (Math.atan2(-dx, dz) * (180.0 / Math.PI));
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    if (packet) {
	    	MoveUtil.look(yaw, pitch);
	    } else {
		    player.setYaw(yaw);
		    player.setPitch(pitch);
	    }
	}
	
	public static void lookAtBlockPitch(PlayerEntity player, BlockPos target, Direction side, Boolean packet) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    //nz = north
	    //pz = south
	    //nx = west
	    //px = east
	    double cx = 0.5;
	    double cy = 0.5;
	    double cz = 0.5;
	    if (side == Direction.WEST || side == Direction.EAST) {
	    	cx = side == Direction.WEST ? 0 : 1;
	    }
	    if (side == Direction.DOWN || side == Direction.UP) {
	    	cy = side == Direction.DOWN ? 0 : 1;
	    }
	    if (side == Direction.NORTH || side == Direction.SOUTH) {
	    	cz = side == Direction.NORTH ? 0 : 1;
	    }
	    double dx = target.getX() + cx - player.getX();
	    double dy = target.getY() + cy - player.getEyeY();
	    double dz = target.getZ() + cz - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    if (packet) {
	    	MoveUtil.look(player.getYaw(), pitch);
	    } else {
		    player.setPitch(pitch);
	    }
	}
	
	public static void lookAtBlockPitchR(PlayerEntity player, BlockPos target, Direction side, Boolean packet) {
	    if (player == null || target == null) { 
	    	return;
	    };
	    //nz = north
	    //pz = south
	    //nx = west
	    //px = east
	    double cx = 0.5 + (Math.random()/4);
	    double cy = 0.5 + (Math.random()/4);
	    double cz = 0.5 + (Math.random()/4);
	    if (side == Direction.WEST || side == Direction.EAST) {
	    	cx = side == Direction.WEST ? 0 : 1;
	    }
	    if (side == Direction.DOWN || side == Direction.UP) {
	    	cy = side == Direction.DOWN ? 0 : 1;
	    }
	    if (side == Direction.NORTH || side == Direction.SOUTH) {
	    	cz = side == Direction.NORTH ? 0 : 1;
	    }
	    double dx = target.getX() + cx - player.getX();
	    double dy = target.getY() + cy - player.getEyeY();
	    double dz = target.getZ() + cz - player.getZ();
	    double distance = Math.sqrt(dx * dx + dz * dz);
	    float pitch = (float) (Math.atan2(-dy, distance) * (180.0 / Math.PI));
	    if (packet) {
	    	MoveUtil.look(player.getYaw(), pitch);
	    } else {
		    player.setPitch(pitch);
	    }
	}
	
	public static Direction getDirectionFromYaw(float yaw) {
	    yaw = (yaw % 360 + 360) % 360;
	    if (yaw >= 45 && yaw < 135) {
	        return Direction.WEST;
	    } else if (yaw >= 135 && yaw < 225) {
	        return Direction.NORTH;
	    } else if (yaw >= 225 && yaw < 315) {
	        return Direction.EAST;
	    } else {
	        return Direction.SOUTH;
	    }
	}
}
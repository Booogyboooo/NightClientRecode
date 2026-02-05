package io.booogyboooo.nightclient.modules.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.RenderEventData;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;

public class ESP extends NightModule {

	public Box showPlayers = new Box("Players", "*", true);
	public Box showItems = new Box("Items", "Outline", false);
	public Box showContainers = new Box("Containers", "Box", false);
	public float x = 105;
	public float y = 10;
	public float z = 145;
	public boolean xUp = true;
	public boolean yUp = true;
	public boolean zUp = true;
	
	public ESP() {
		super(Type.RENDER, "ESP", "Box");
		this.addMode("Outline");
		this.addOption(showPlayers);
		this.addOption(showItems);
		this.addOption(showContainers);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
		this.registerEvent(this, "onRender");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
		this.unregisterEvent(this, "onRender");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (this.getMode().equals("Outline")) {
			if (showPlayers.checked()) {
				if (!RenderUtil.getEntitys().contains(EntityType.PLAYER)) {
					RenderUtil.addEntity(EntityType.PLAYER);
				}
			} else {
				if (RenderUtil.getEntitys().contains(EntityType.PLAYER)) {
					RenderUtil.removeEntity(EntityType.PLAYER);
				}
			}
			if (showItems.checked()) {
				if (!RenderUtil.getEntitys().contains(EntityType.ITEM)) {
					RenderUtil.addEntity(EntityType.ITEM);
				}
			} else {
				if (RenderUtil.getEntitys().contains(EntityType.ITEM)) {
					RenderUtil.removeEntity(EntityType.ITEM);
				}
			}
		} else {
			RenderUtil.getEntitys().clear();
		}
	}
	
	@Event(eventType = EventType.RENDER)
	public void onRender(EventData data) {
        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        RenderEventData rdata = (RenderEventData) data;
		if (this.getMode().equals("Box")) {
	        if (showPlayers.checked()) {
		        for (PlayerEntity player : mc.world.getPlayers()) {
		        	if (player == mc.player) {
		        		continue;
		        	}
		            RenderUtil.drawBoxOutline(rdata.getContext().consumers().getBuffer(RenderUtil.LINE_NO_DEPTH), player.getBoundingBox().offset(-cameraPos.x, -cameraPos.y, -cameraPos.z).expand(0.08), x/255f, y/255f, z/255f, 1.0f);
		            if (yUp) {
			            if (y <= 12) {
			            	y++;
			            } else {
			            	yUp = false;
			            }
		            } else if (!yUp) {
			            if (y >= 10) {
			            	y--;
			            } else {
			            	yUp = true;
			            }
		            }
		            if (zUp) {
			            if (z <= 212) {
			            	z++;
			            } else {
			            	zUp = false;
			            }
		            } else if (!zUp) {
			            if (z >= 122) {
			            	z--;
			            } else {
			            	zUp = true;
			            }
		            }
		            if (xUp) {
			            if (x <= 135) {
			            	x++;
			            } else {
			            	xUp = false;
			            }
		            } else if (!xUp) {
			            if (x >= 56) {
			            	x--;
			            } else {
			            	xUp = true;
			            }
		            }
		        }
		    }
	        if (showContainers.checked()) {
	    		int radius = Math.max(2, MinecraftClient.getInstance().options.getClampedViewDistance()) + 3;
	    		int diameter = radius * 2 + 1;
	    		ChunkPos center = MinecraftClient.getInstance().player.getChunkPos();
	    		ChunkPos min = new ChunkPos(center.x - radius, center.z - radius);
	    		ChunkPos max = new ChunkPos(center.x + radius, center.z + radius);
	    		
	    		Stream<WorldChunk> stream = Stream.<ChunkPos> iterate(min, pos -> {
	    			int x = pos.x;
	    			int z = pos.z;
	    			x++;
	    			if(x > max.x) {
	    				x = min.x;
	    				z++;
	    			}	
	    			return new ChunkPos(x, z);
	    		}).limit(diameter * diameter).filter(c -> MinecraftClient.getInstance().world.isChunkLoaded(c.x, c.z)).map(c -> MinecraftClient.getInstance().world.getChunk(c.x, c.z)).filter(Objects::nonNull);
	    		
	    		List<BlockEntity> blockEntities = new ArrayList<BlockEntity>();
	    		stream.forEach(chunk -> {
	    			blockEntities.addAll(chunk.getBlockEntities().values());
	    		});
	    		
	    		blockEntities.forEach(block -> {
	        		RenderUtil.drawBlockPosOutline(rdata.getContext().consumers().getBuffer(RenderUtil.LINE_NO_DEPTH), block.getPos(), 237/255f, 255f, 14/255f, 1.0f);
	        	});
	        }
		}
	}
	
}
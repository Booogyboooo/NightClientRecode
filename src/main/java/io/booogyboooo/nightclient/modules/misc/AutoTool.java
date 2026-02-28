package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends NightModule {
	
	public AutoTool() {
		super(Type.MISC, "AutoTool", "Default");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (!mc.options.attackKey.isPressed()) {
			return;
		}
		
		HitResult result = mc.crosshairTarget;
		
		if (result.getType() == HitResult.Type.BLOCK) {
			BlockPos block = ((BlockHitResult) result).getBlockPos();
			BlockState state = mc.world.getBlockState(block);
			
			float best = 1;
			int tslot = mc.player.getInventory().getSelectedSlot();
			
			for(int slot = 0; slot < 9; slot++) {
				if(slot == mc.player.getInventory().getSelectedSlot()) {
					continue;
				}
				
				ItemStack item = mc.player.getInventory().getStack(slot);
				float speed = item.getMiningSpeedMultiplier(state);
				
				if(speed <= best) {
					continue;
				}
				
				best = speed;
				tslot = slot;
			}
			
			mc.player.getInventory().setSelectedSlot(tslot);
		}
	}

}
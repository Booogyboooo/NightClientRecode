package io.booogyboooo.nightclient.modules.combat;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.DelayUtil;
import io.booogyboooo.nightclient.util.MouseUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class ShieldDisable extends NightModule {

	public boolean canAttack = true;
	
	public ShieldDisable() {
		super(Type.COMBAT, "ShieldDisable", "Default");
		//this.addMode("Silent");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	@Event(eventType = EventType.PRE_TICK, priority = -1)
	public void preTick(EventData data) {
		HitResult target = mc.crosshairTarget;
		if (target.getType() == HitResult.Type.ENTITY) {
			if (!((EntityHitResult) target).getEntity().isPlayer()) {
				return;	
			}
			
			PlayerEntity player = (PlayerEntity) ((EntityHitResult) target).getEntity();
			
			if (player.isHolding(Items.SHIELD) || player.getOffHandStack().getItem() == Items.SHIELD){
				int slot = axePos();
				if (player.isUsingItem() && slot != -1) {
					int oslot = mc.player.getInventory().getSelectedSlot();
					mc.player.getInventory().setSelectedSlot(slot);
					MouseUtil.leftClick();
					canAttack = false;
					DelayUtil.timeout(() -> {
						mc.player.getInventory().setSelectedSlot(oslot);
						canAttack = true;
					}, 92);
				}
			}
		}
	}
	
	public int axePos() {
        PlayerInventory inventory = mc.player.getInventory();
        for (int i = 0; i < 9; i++) {
            if (inventory.getStack(i).getItem().getName().getString().toLowerCase().contains("axe")) {
            	return i;
            }
        }
        return -1;
	}
}
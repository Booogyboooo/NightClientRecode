package io.booogyboooo.nightclient.modules.combat;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.util.AimUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;

public class AimAssist extends NightModule {
	
	private Box onlySwords = new Box("Only Sword", "Default", false);
	private Entity target = null;

	public AimAssist() {
		super(Type.COMBAT, "AimAssist", "Default");
		this.addOption(onlySwords);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "onRender");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "onRender");
		target = null;
	}
	
	@Event(eventType = EventType.RENDER)
	public void onRender(EventData event) {
		if (mc.crosshairTarget instanceof EntityHitResult) {
			target = ((EntityHitResult) mc.crosshairTarget).getEntity();
		}
		if (target == null) {
			return;
		}
		if (target.isPlayer()) {
			PlayerEntity t = (PlayerEntity) target;
			if (t.isDead()) {
				target = null;
			}
		}
		if (!onlySwords.checked()) {
			AimUtil.lookAtEntity(mc.player, target);
		} else {
			if (mc.player.getInventory().getMainStacks().get(mc.player.getInventory().getSelectedSlot()).getItem().getName().getString().contains("Sword")) {
				AimUtil.lookAtEntity(mc.player, target);
			}
		}
	}

}
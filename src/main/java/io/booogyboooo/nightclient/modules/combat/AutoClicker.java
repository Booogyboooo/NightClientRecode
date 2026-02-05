package io.booogyboooo.nightclient.modules.combat;

import java.util.ArrayList;
import java.util.List;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import io.booogyboooo.nightclient.util.DelayUtil;
import io.booogyboooo.nightclient.util.DelayUtil.Delay;
import io.booogyboooo.nightclient.util.MouseUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoClicker extends NightModule {

	public Box allowSword = new Box("Swords", "*", true);
	public Box allowAxe = new Box("Axes", "*", true);
	public Box allowFist = new Box("Fist", "*", false);
	public Box allowAny = new Box("Anything", "*", false);
	public Box attackPlayers = new Box("Players", "*", true);
	public Box playersOnly = new Box("No Mobs", "*", false);
	public Slider minCps = new Slider("Min CPS", "<1.8", 11, 0, 20, 1);
	public Slider randCps = new Slider("Random", "<1.8", 3, 0, 20, 1);
	public Slider delay = new Slider("Delay", "1.9+", 0, 0, 10, 1);
	public Slider rand = new Slider("Rand", "1.9+", 0, 0, 10, 1);
	public Delay curr = getDelay();
	public boolean canCreate = true;
	
	public AutoClicker() {
		super(Type.COMBAT, "AutoClicker", "1.9+");
		this.addMode("<1.8");
		this.addOption(allowSword);
		this.addOption(allowAxe);
		this.addOption(allowFist);
		this.addOption(allowAny);
		this.addOption(attackPlayers);
		this.addOption(playersOnly);
		this.addOption(minCps);
		this.addOption(randCps);
		this.addOption(delay);
		this.addOption(rand);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
	}
	
	public Delay getDelay() {
		int del = (int) (delay.getInteger() + Math.round(Math.random() * rand.getInteger()));
		return DelayUtil.createDelay(del * 50);
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		List<Item> whitelist = new ArrayList<Item>();
		if (allowSword.checked()) {
			whitelist.add(Items.WOODEN_SWORD);
			whitelist.add(Items.STONE_SWORD);
			whitelist.add(Items.GOLDEN_SWORD);
			whitelist.add(Items.IRON_SWORD);
			whitelist.add(Items.DIAMOND_SWORD);
			whitelist.add(Items.NETHERITE_SWORD);
		}
		if (allowAxe.checked()) {
			whitelist.add(Items.WOODEN_AXE);
			whitelist.add(Items.STONE_AXE);
			whitelist.add(Items.GOLDEN_AXE);
			whitelist.add(Items.IRON_AXE);
			whitelist.add(Items.DIAMOND_AXE);
			whitelist.add(Items.NETHERITE_AXE);
		}
		if (allowFist.checked()) {
			whitelist.add(Items.AIR);
		}
		Item holding = mc.player.getInventory().getSelectedStack().getItem();
		if (!whitelist.contains(holding) && !allowAny.checked()) {
			return;
		}
		if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
			if (((((EntityHitResult) mc.crosshairTarget).getEntity().isPlayer() && !attackPlayers.checked())) || (!((EntityHitResult) mc.crosshairTarget).getEntity().isPlayer() && playersOnly.checked())) {
				return;
			}
		}
		Integer chance = (int) (minCps.getInteger() + Math.round(Math.random() * randCps.getInteger()));
		Integer rand = (int) Math.round(Math.random() * 20);
		if (!this.getMode().equals("1.9+") && rand > chance) {
			return;
		}
		if (this.getMode().equals("<1.8")) { 
			if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
				MouseUtil.leftClick();
			}
		} else if (this.getMode().equals("1.9+")) {
			if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY && mc.player.getAttackCooldownProgress(0.0F) >= 1.0f && mc.player.distanceTo(((EntityHitResult) mc.crosshairTarget).getEntity()) < 3.05) {
				if (canCreate) {
					curr = getDelay();
					canCreate = false;
				}
				if (curr.finished()) {
					MouseUtil.leftClick();
					canCreate = true;
				}
			}
		}
	}
}
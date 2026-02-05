package com.notprolific.nightclient.modules.combat;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.silentaim.SilentAimState;
import io.booogyboooo.nightclient.silentaim.SilentFlags;
import io.booogyboooo.nightclient.silentaim.util.SilentSlotUtil;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import io.booogyboooo.nightclient.util.DelayUtil;
import io.booogyboooo.nightclient.util.InventoryUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class AutoPot extends NightModule {
	
	private Box turtleMaster = new Box("Turtle", "*", false);
	private Slider hearts = new Slider("Hearts", "*", 4, 1, 10, 0.5);
	private Box strength = new Box("Strength", "*", false);
	private Box fireResistance = new Box("Fire Res", "*", false);
	private Box swiftness = new Box("Swiftness", "*", false);
	private SilentAimState aimState = NightClientRecode.getAimState();
	
	public AutoPot() {
		super(Type.COMBAT, "AutoPot", "Default");
		this.addMode("Silent");
		this.addOption(turtleMaster);
		this.addOption(hearts);
		this.addOption(strength);
		this.addOption(fireResistance);
		this.addOption(swiftness);
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "preTick");
		this.registerEvent(this, "silentPreTick");
	}
	

	@Override
	public void onDisable() {
		this.unregisterEvent(this, "preTick");
		this.unregisterEvent(this, "silentPreTick");
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void silentPreTick(EventData data) {
		if (!this.getMode().equals("Silent")) {
			return;
		}
		if (((int) mc.world.getTime()) % 10 != 0) {
			return;
		}
		if (statusCheck(StatusEffects.STRENGTH) && strength.checked()) {
			silentSplashPotion(StatusEffects.STRENGTH);
		}
		if (statusCheck(StatusEffects.FIRE_RESISTANCE) && fireResistance.checked()) {
			silentSplashPotion(StatusEffects.FIRE_RESISTANCE);
		}
		if (meetsHealth() && turtleMaster.checked() && hasTurtlePotion() != 1 && (!mc.player.hasStatusEffect(StatusEffects.RESISTANCE) && !mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) &&  mc.currentScreen == null) {
			silentSplashTurtlePotion();
		}
		if (!mc.player.hasStatusEffect(StatusEffects.SPEED) && hasSwiftPotion() != 1 && mc.currentScreen == null && swiftness.checked()) {
			silentSplashSwiftPotion();
		}
	}
	
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (!this.getMode().equals("Default")) {
			return;
		}
		if (((int) mc.world.getTime()) % 10 != 0) {
			return;
		}
		if (statusCheck(StatusEffects.STRENGTH) && strength.checked()) {
			splashPotion(StatusEffects.STRENGTH);
		}
		if (meetsHealth() && hasTurtlePotion() != 1 && (!mc.player.hasStatusEffect(StatusEffects.RESISTANCE) && !mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) &&  mc.currentScreen == null && turtleMaster.checked()) {
			splashTurtlePotion();
		}
		if (!mc.player.hasStatusEffect(StatusEffects.SPEED) && hasSwiftPotion() != 1 && mc.currentScreen == null && swiftness.checked()) {
			splashSwiftPotion();
		}
		if (statusCheck(StatusEffects.FIRE_RESISTANCE) && fireResistance.checked()) {
			splashPotion(StatusEffects.FIRE_RESISTANCE);
		}
	}
	
	public boolean meetsHealth() {
		if (mc.player.getHealth() <= (hearts.getDouble() * 2)) {
			return true;
		}
		return false;
	}
	
	public boolean statusCheck(RegistryEntry<StatusEffect> effect) {
		if (mc.player.hasStatusEffect(effect) || hasPotion(effect) == -1 || mc.currentScreen != null) {
			return false;
		}
		return true;
	}
	
    public int hasPotion(RegistryEntry<StatusEffect> effect) {
    	int s = -1;
        for (int slot = 0; slot < mc.player.getInventory().size(); slot++) {
        	ItemStack stack = mc.player.getInventory().getStack(slot);
            if (stack.getName().getString().contains(effect.value().getName().getString())) {
            	s = slot;
            }
        }
        if (s != -1) {
        	return s < 9 ? s + 36 : s;
        }
        return s;
    }
    
	public void splashPotion(RegistryEntry<StatusEffect> effect) {
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasPotion(effect);
		mc.player.getInventory().setSelectedSlot(1);
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		float opitch = mc.player.getPitch();
		mc.player.setPitch(90);
	    mc.options.useKey.setPressed(true);
	    DelayUtil.timeout(() -> {
		    mc.player.setPitch(opitch);
		    mc.options.useKey.setPressed(false);
		    InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		    mc.player.getInventory().setSelectedSlot(oslot);
	    }, 200);
	}
	
	public void silentSplashPotion(RegistryEntry<StatusEffect> effect) {
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasPotion(effect);
		SilentSlotUtil.setSpoofing(true);
		mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(1));
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		aimState.enable();
		aimState.setPitch(90);
	    mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 1, mc.player.getYaw(), 90));
	    DelayUtil.timeout(() -> {
		    mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(oslot));
		    SilentSlotUtil.setSpoofing(false, 50);
		    aimState.disable();
	    }, 200);
	}

	public void silentSplashSwiftPotion() {
		if (SilentFlags.SLOT_SPOOFING) {
			return;
		}
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasSwiftPotion();
		SilentSlotUtil.setSpoofing(true);
		mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(1));
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		aimState.enable();
		aimState.setPitch(90);
	    mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 1, mc.player.getYaw(), 90));
	    DelayUtil.timeout(() -> {
		    mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(oslot));
		    SilentSlotUtil.setSpoofing(false, 50);
		    aimState.disable();
	    }, 200);
	}

	public void silentSplashTurtlePotion() {
		if (SilentFlags.SLOT_SPOOFING) {
			return;
		}
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasTurtlePotion();
		SilentSlotUtil.setSpoofing(true);
		mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(1));
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		aimState.enable();
		aimState.setPitch(90);
	    mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 1, mc.player.getYaw(), 90));
	    DelayUtil.timeout(() -> {
		    mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(oslot));
		    SilentSlotUtil.setSpoofing(false, 50);
		    aimState.disable();
	    }, 200);
	}
	
    public int hasTurtlePotion() {
    	int s = -1;
        for (int slot = 0; slot < mc.player.getInventory().size(); slot++) {
        	ItemStack stack = mc.player.getInventory().getStack(slot);
            if (stack.getName().getString().contains("Turtle")) {
            	s = slot;
            }
        }
        if (s != -1) {
        	return s < 9 ? s + 36 : s;
        }
        return s;
    }
	
	public void splashTurtlePotion() {
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasTurtlePotion();
		mc.player.getInventory().setSelectedSlot(1);
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		float opitch = mc.player.getPitch();
		mc.player.setPitch(90);
	    mc.options.useKey.setPressed(true);
	    DelayUtil.timeout(() -> {
		    mc.player.setPitch(opitch);
		    mc.options.useKey.setPressed(false);
		    InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		    mc.player.getInventory().setSelectedSlot(oslot);
	    }, 200);
	}
	
    public int hasSwiftPotion() {
    	int s = -1;
        for (int slot = 0; slot < mc.player.getInventory().size(); slot++) {
        	ItemStack stack = mc.player.getInventory().getStack(slot);
            if (stack.getName().getString().contains("Swiftness")) {
            	s = slot;
            }
        }
        if (s != -1) {
        	return s < 9 ? s + 36 : s;
        }
        return s;
    }
	
	public void splashSwiftPotion() {
		int oslot = mc.player.getInventory().getSelectedSlot();
		int slot = hasSwiftPotion();
		mc.player.getInventory().setSelectedSlot(1);
		InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		float opitch = mc.player.getPitch();
		mc.player.setPitch(90);
	    mc.options.useKey.setPressed(true);
	    DelayUtil.timeout(() -> {
		    mc.player.setPitch(opitch);
		    mc.options.useKey.setPressed(false);
		    InventoryUtil.interactSlot(mc, slot, SlotActionType.SWAP);
		    mc.player.getInventory().setSelectedSlot(oslot);
	    }, 200);
	}
	
}
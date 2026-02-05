package io.booogyboooo.nightclient.modules.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import io.booogyboooo.nightclient.util.DelayUtil;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AutoTotem extends NightModule {
	
	public Box inventoryOnly = new Box("Inv Only", "vannila", false);
	public Slider minDelay = new Slider("Min Delay", "legit", 6, 0, 10, 1);
	public Slider randDelay = new Slider("Random", "legit", 8, 0, 10, 1);
	public int slot = -1;
	public boolean block = false;
	
	public AutoTotem() {
		super(Type.COMBAT, "AutoTotem", "vannila");
		this.addMode("legit");
		this.addOption(inventoryOnly);
		this.addOption(minDelay);
		this.addOption(randDelay);
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
        if (mc.player == null || !this.isToggled() || mc.player.getInventory().getStack(40).getItem() == Items.TOTEM_OF_UNDYING || block) {
            return;
        }
        if ((inventoryOnly.checked()  && !(mc.currentScreen instanceof InventoryScreen)) || (!(mc.currentScreen instanceof InventoryScreen) && this.getMode().equals("legit"))) {
        	return;
        }
        slot = -1;
        if (this.getMode().equals("vannila")) {
		    for (int i = 0; i < 36; ++i) {
		        if (mc.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
		            slot = i;
		        }
		    }
		    if (slot == -1) {
		        return;
		    }
		    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot < 9 ? slot + 36 : slot, 40, SlotActionType.SWAP, mc.player);
        } else if (this.getMode().equals("legit")) {
        	List<Integer> totemSlots = new ArrayList<>();
		    for (int i = 0; i < 36; ++i) {
		        if (mc.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
		        	totemSlots.add(i);
		        }
		    }
		    if (totemSlots.isEmpty()) {
		    	return;
		    }
		    slot = totemSlots.get(ThreadLocalRandom.current().nextInt(totemSlots.size()));
		    block = true;
		    int delay = Integer.valueOf((int) Math.round(Math.random() * randDelay.getInteger()) + minDelay.getInteger()) * 50;
		    DelayUtil.timeoutSameThread(() -> {
		    	if (mc.currentScreen instanceof InventoryScreen) {
		    		mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot < 9 ? slot + 36 : slot, 40, SlotActionType.SWAP, mc.player);
		    	} else {
		    		mc.player.sendMessage(Text.literal("[AutoTotem]").formatted(Formatting.GOLD).append(Text.literal(" could not move totem (inv closed)").formatted(Formatting.WHITE)), false);
		    	}
		    	block = false;
		    }, delay);
        }
	}

}
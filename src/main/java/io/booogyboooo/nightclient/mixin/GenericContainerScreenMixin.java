package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import io.booogyboooo.nightclient.modules.misc.Stealer;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler> implements ScreenHandlerProvider<GenericContainerScreenHandler> {
	@Shadow
	@Final
	private int rows;
	private int mode;
	
	public GenericContainerScreenMixin(GenericContainerScreenHandler container, PlayerInventory playerInventory, Text name) {
		super(container, playerInventory, name);
	}
	
	@Override
	public void init() {
		super.init();
		if (Stealer.INSTANCE.isToggled()) {
			new Thread(() -> shiftClickSlots(0, rows * 9, 1)).start();
		}
	}
	
	@Unique
	private void shiftClickSlots(int from, int to, int mode) {
		this.mode = mode;
		for(int i = from; i < to; i++) {
			Slot slot = handler.slots.get(i);
			if(slot.getStack().isEmpty()) {
				continue;
			}
			try {
				Thread.sleep(Stealer.INSTANCE.getDelay());
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			
			if(this.mode != mode || client.currentScreen == null) {
				break;
			}
			onMouseClick(slot, slot.id, 0, SlotActionType.QUICK_MOVE);
		}
	}
}
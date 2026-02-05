package io.booogyboooo.nightclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryUtil {
	public static Item getOffhandItem(MinecraftClient mc) {
		return mc.player.getOffHandStack().getItem();
	}
	
	@Deprecated
	public static int getSlot(MinecraftClient mc, Item item) {
        int slot = -1;
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            if (mc.player.getInventory().getStack(i).isOf(item)) {
                slot = i;
                break;
            }
        }
        return slot;
	}
	
    public static int getSlot(Item item) {
    	MinecraftClient mc = MinecraftClient.getInstance();
    	int s = -1;
        for (int slot = 0; slot < mc.player.getInventory().size(); slot++) {
        	ItemStack stack = mc.player.getInventory().getStack(slot);
            if (stack.getName().getString().equals(item.getName().getString())) {
            	s = slot;
            }
        }
        if (s != -1) {
        	return s < 9 ? s + 36 : s;
        }
        return s;
    }
	
	public static void interactSlot(MinecraftClient mc, int slot, SlotActionType action) {
		mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, slot, 1, action, mc.player);
	}
}
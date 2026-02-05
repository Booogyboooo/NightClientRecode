package io.booogyboooo.nightclient.silentaim.util;

import io.booogyboooo.nightclient.silentaim.SilentFlags;
import io.booogyboooo.nightclient.util.DelayUtil;

public class SilentSlotUtil {
	public static void setSpoofing(boolean spoofing) {
		SilentFlags.SLOT_SPOOFING = spoofing;
	}
	
	public static void setSpoofing(boolean spoofing, int delay) {
		DelayUtil.timeout(() -> {
			SilentFlags.SLOT_SPOOFING = spoofing;
		}, delay);
	}
}
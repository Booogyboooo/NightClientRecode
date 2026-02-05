package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.DelayUtil;
import io.booogyboooo.nightclient.util.RayTraceUtil;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;

public class RightClickTP extends NightModule {

	public boolean buttonDown = false;
	
	public RightClickTP() {
		super(Type.MOVEMENT, "RightClickTP", "Vannila");
		this.addMode("NoLim");
	}
	
	@Override
	public void onEnable() {
		buttonDown = false;
		this.registerEvent(this, "preTick");
	}
	
	@Override
	public void onDisable() {
		buttonDown = false;
		this.unregisterEvent(this, "preTick");
	}
	
	@SuppressWarnings("deprecation")
	@Event(eventType = EventType.PRE_TICK)
	public void preTick(EventData data) {
		if (this.getMode().equals("Vannila")) {
			if (mc.mouse.wasRightButtonClicked() && !buttonDown) {
				buttonDown = true;
				DelayUtil.timeout(() -> {buttonDown = false;}, 350);
				BlockHitResult block = RayTraceUtil.getBlock(3);
				if (mc.world.getBlockState(block.getBlockPos()).getBlock() == null || mc.world.getBlockState(block.getBlockPos()).getBlock() == Blocks.AIR || !mc.world.getBlockState(block.getBlockPos()).isSolid()) {
					mc.inGameHud.getChatHud().addMessage(Text.of("Target block is out of range."));
					return;
				} else {
					mc.player.setPos(block.getBlockPos().up().getX(), block.getBlockPos().up().getY(), block.getBlockPos().up().getZ());
				}
			}
		} else if (this.getMode().equals("NoLim")) {
			if (mc.mouse.wasRightButtonClicked()) {
				buttonDown = true;
				DelayUtil.timeout(() -> {buttonDown = false;}, 350);
				BlockHitResult block = RayTraceUtil.getBlock(3);
				if (block == null) {
					return;
				}
				mc.player.setPos(block.getBlockPos().up().getX(), block.getBlockPos().up().getY(), block.getBlockPos().up().getZ());
			}
		}
	}

}
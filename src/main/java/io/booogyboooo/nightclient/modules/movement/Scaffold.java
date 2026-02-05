package io.booogyboooo.nightclient.modules.movement;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.BlockPlaceUtil;
import io.booogyboooo.nightclient.util.PlayerPosUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends NightModule {

	public Scaffold() {
		super(Type.MOVEMENT, "Scaffold", "Shift");
		this.addMode("Packet");
	}
	
	@Override
	public void onEnable() {
		this.registerEvent(this, "postTick");
	}
	
	@Override
	public void onDisable() {
		this.unregisterEvent(this, "postTick");
	}
	
	@Event(eventType = EventType.POST_TICK)
	public void postTick(EventData data) {
		if (this.getMode().equals("Shift")) {
			Vec3d pos = mc.player.getEntityPos();
			BlockPos blockPos = mc.player.getBlockPos();
			double xOffset = pos.x - blockPos.getX();
			double zOffset = pos.z - blockPos.getZ();
			BlockState blockBelow = mc.world.getBlockState(blockPos.down());
			boolean isAirBelow = blockBelow.isAir();
			if (isAirBelow || (xOffset < 0.05 || xOffset > 1 - 0.05) || (zOffset < 0.05 || zOffset > 1 - 0.05)) {
				mc.options.sneakKey.setPressed(true);
			} else {
				mc.options.sneakKey.setPressed(false);
			}
		} else if (this.getMode().equals("Packet")) {
			BlockPlaceUtil.placeBlock(PlayerPosUtil.getBlockUnderPlayer(true));
		}
	}
}
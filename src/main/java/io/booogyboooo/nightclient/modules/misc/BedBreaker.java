package io.booogyboooo.nightclient.modules.misc;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.ui.components.options.Box;
import io.booogyboooo.nightclient.ui.components.options.Slider;
import io.booogyboooo.nightclient.util.DelayUtil;
import net.minecraft.block.BedBlock;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BedBreaker extends NightModule {

	public Box shouldAim = new Box("aim", "Vannila", true);
	public Slider delay = new Slider("delay", "Vannila", 250, 0, 1000, 25);
	
	public BedBreaker() {
		super(Type.MISC, "BedBreaker", "Vannila");
		this.addOption(shouldAim);
		this.addOption(delay);
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
		BlockPos bedPos = getBed();
		if (bedPos == null) {
			return;
		}
		Direction face = Direction.UP;
        Vec3d eyes = mc.player.getEyePos();
        Vec3d hitVec = Vec3d.ofCenter(bedPos).add(Vec3d.of(face.getVector()).multiply(0.5)).add(0, 0.5625 - 1, 0);
        double dx = hitVec.x - eyes.x;
        double dy = hitVec.y - eyes.y - (1 - 0.5625);
        double dz = hitVec.z - eyes.z;
        
        if (shouldAim.checked()) {
	        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90F);
	        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz))));
	        
	        mc.player.setYaw(yaw);
	        mc.player.setPitch(pitch);
        }
        
        BlockHitResult hitResult = new BlockHitResult(hitVec, face, bedPos, false);
        
        mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0));
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, bedPos, face));
	
        DelayUtil.timeoutSameThread(() -> {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, bedPos, face));
        }, delay.getInteger());
	}
	
    private BlockPos getBed() {
        BlockPos bed = null;
        BlockPos playerPos = mc.player.getBlockPos();
        int r = (int) Math.ceil(6);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos checkPos = playerPos.add(x, y, z);
                    if (playerPos.getSquaredDistance(checkPos) <= 36) {
                        if (mc.world.getBlockState(checkPos).getBlock() instanceof BedBlock) {
                            bed = checkPos;
                            break;
                        }
                    }
                }
            }
        }

        return bed;
    }

}

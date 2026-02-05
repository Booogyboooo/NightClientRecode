package io.booogyboooo.nightclient.modules.combat;

import java.util.Comparator;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.module.Type;
import io.booogyboooo.nightclient.util.AimUtil;
import io.booogyboooo.nightclient.util.DelayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class HitBoxes extends NightModule {

	public HitBoxes() {
		super(Type.COMBAT, "HitBoxes", "modify");
	}
	
	@Event(eventType = EventType.LEFT_CLICK)
	public void onLeftClick(EventData data) {
		double range = 3;
		Vec3d playerPos = mc.player.getEntityPos();
        Box search = new Box(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range);
		LivingEntity closest = mc.world.getEntitiesByClass(LivingEntity.class, search, e -> e != mc.player && e.isAlive()).stream().min(Comparator.comparingDouble(e -> e.squaredDistanceTo(playerPos))).orElse(null);
		if (closest == null) {
			return;
		}
        Vec3d start = mc.player.getCameraPosVec(1.0F);
        Vec3d direction = mc.player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(3));
		if (closest.getBoundingBox().expand(1).intersects(start, end)) {
			data.cancel(data);
			float[] angles = AimUtil.gatAngles(mc.player, closest);
			NightClientRecode.getAimState().enable();
			NightClientRecode.getAimState().look(angles[0], angles[1]);
			DelayUtil.timeout(() -> {
				NightClientRecode.getAimState().disable();
			}, 51);
		}
	}

}
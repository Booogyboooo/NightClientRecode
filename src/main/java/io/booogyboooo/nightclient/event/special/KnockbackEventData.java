package io.booogyboooo.nightclient.event.special;

import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import net.minecraft.util.math.Vec3d;

public class KnockbackEventData extends EventData {

	private double y;
	private double x;
	private double z;

	public KnockbackEventData(double x, double y, double z) {
		super(true, EventType.KNOCKBACK);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3d getKnockback() {
		return new Vec3d(this.x, this.y, this.z);
	}
	
	public void setKnockback(KnockbackEventData event, double x, double y, double z) {
		event.x = x;
		event.y = y;
		event.z = z;
	}
	
	public void setKnockback(KnockbackEventData event, Vec3d kb) {
		event.x = kb.x;
		event.y = kb.y;
		event.z = kb.z;
	}

}
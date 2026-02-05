package io.booogyboooo.nightclient.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.MinecraftClient;

public class DelayUtil {

	/**
	 * Run code later (Delay)
	 * <br>
	 * - timeout(code, delay)
	 * <br>
	 * <br>
	 * code
	 * <br>
	 * - A Runnable object () -> {}
	 * <br>
	 * <br>
	 * delay
	 * <br>
	 * - Delay in milliseconds
	 */
	public static void timeout(Runnable code, int delay) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(code, delay, TimeUnit.MILLISECONDS);
        scheduler.shutdown();
	}
	
	/**
	 * Run code later (Delay) 
	 * <br>
	 * - timeoutSameThread(code, delay)
	 * <br>
	 * <br>
	 * code
	 * <br>
	 * - A Runnable object () -> {}
	 * <br>
	 * <br>
	 * delay
	 * <br>
	 * - Delay in milliseconds
	 */
	public static void timeoutSameThread(Runnable code, int delay) {
		MinecraftClient client = MinecraftClient.getInstance();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {client.execute(code);}, delay, TimeUnit.MILLISECONDS);
        scheduler.shutdown();
	}
	
	public static Delay createDelay(int delay) {
		return new Delay(delay);
	}
	
	public static class Delay {
		private int delay;
		private long end;
		private boolean finished;

		public Delay(int delay) {
			this.delay = delay;
			this.end = System.currentTimeMillis() + delay;
			this.finished = false;
		}
		
		public int getDelay() {
			return this.delay;
		}
		
		public long getEnding() {
			return this.end;
		}
		
		public boolean finished() {
			if (System.currentTimeMillis() > this.end) {
				this.finished = true;
			}
			return this.finished;
		}
		
		public void finish() {
			this.finished = true;
		}
	}
}

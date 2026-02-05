package io.booogyboooo.nightclient.bind;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.annotation.Event;
import io.booogyboooo.nightclient.event.special.KeyEventData;
import io.booogyboooo.nightclient.module.ModuleList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class BindHandler {
	public void init() {
		this.hook();
		NightClientRecode.getEventManager().registerEvent(this, "_keypress");
	}
	
	private void hook() {
		ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
			Window window = MinecraftClient.getInstance().getWindow();
			GLFWKeyCallbackI mc = GLFW.glfwSetKeyCallback(window.getHandle(), null);
			GLFWKeyCallbackI callback = (windowHandle, key, scancode, action, mods) -> {
			    if (action == GLFW.GLFW_PRESS) {
			        try {
						NightClientRecode.getEventManager().fireEvent(EventType.KEYPRESS,new KeyEventData(key, mods));
					} catch (Exception e) {}
			    }
			    mc.invoke(windowHandle, key, scancode, action, mods);
			};
			GLFW.glfwSetKeyCallback(window.getHandle(), callback);
		});
	}
	
	@Event(eventType = EventType.KEYPRESS)
	public void _keypress(EventData data) {
		KeyEventData dat = (KeyEventData) data;
		if (MinecraftClient.getInstance().currentScreen != null) {
			return;
		}
		ModuleList.getList().forEach(m -> {
			if (m.getBind() == dat.getKeyCode()) {
				m.toggle();
			}
		});
	}
}
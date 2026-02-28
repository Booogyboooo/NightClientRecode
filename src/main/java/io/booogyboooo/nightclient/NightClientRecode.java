package io.booogyboooo.nightclient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.KeyBinding.Category;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notprolific.nightclient.modules.combat.AutoPot;

import io.booogyboooo.nightclient.bind.BindHandler;
import io.booogyboooo.nightclient.command.CommandManager;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventManager;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.event.special.RenderEventData;
import io.booogyboooo.nightclient.folder.NightFolder;
import io.booogyboooo.nightclient.hud.NightHUD;
import io.booogyboooo.nightclient.module.ModuleList;
import io.booogyboooo.nightclient.module.NightModule;
import io.booogyboooo.nightclient.modules.combat.AimAssist;
import io.booogyboooo.nightclient.modules.combat.AutoClicker;
import io.booogyboooo.nightclient.modules.combat.AutoTotem;
import io.booogyboooo.nightclient.modules.combat.Criticals;
import io.booogyboooo.nightclient.modules.combat.ShieldDisable;
import io.booogyboooo.nightclient.modules.exploit.AntiDurability;
import io.booogyboooo.nightclient.modules.exploit.AntiVoid;
import io.booogyboooo.nightclient.modules.exploit.Blink;
import io.booogyboooo.nightclient.modules.exploit.BookDupe;
import io.booogyboooo.nightclient.modules.exploit.GodMode;
import io.booogyboooo.nightclient.modules.exploit.Lag;
import io.booogyboooo.nightclient.modules.misc.AutoTool;
import io.booogyboooo.nightclient.modules.misc.BedBreaker;
import io.booogyboooo.nightclient.modules.misc.BoxEscape;
import io.booogyboooo.nightclient.modules.misc.FastBreak;
import io.booogyboooo.nightclient.modules.misc.FastUse;
import io.booogyboooo.nightclient.modules.misc.NoFall;
import io.booogyboooo.nightclient.modules.misc.Stealer;
import io.booogyboooo.nightclient.modules.movement.Flight;
import io.booogyboooo.nightclient.modules.movement.JumpBoost;
import io.booogyboooo.nightclient.modules.movement.Knockback;
import io.booogyboooo.nightclient.modules.movement.RightClickTP;
import io.booogyboooo.nightclient.modules.movement.Scaffold;
import io.booogyboooo.nightclient.modules.movement.Speed;
import io.booogyboooo.nightclient.modules.movement.Sprint;
import io.booogyboooo.nightclient.modules.render.CustomCapes;
import io.booogyboooo.nightclient.modules.render.ESP;
import io.booogyboooo.nightclient.modules.render.EntityInfo;
import io.booogyboooo.nightclient.modules.render.FullBright;
import io.booogyboooo.nightclient.modules.render.NoOverlay;
import io.booogyboooo.nightclient.nightscript.NightScriptEngine;
import io.booogyboooo.nightclient.nightscript.general.LineType;
import io.booogyboooo.nightclient.silentaim.SilentAimState;
import io.booogyboooo.nightclient.ui.NightUI;

public class NightClientRecode implements ModInitializer {
	public static final String MOD_ID = "nightclient-recode";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static KeyBinding openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open NightUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.MISC));
	private static EventManager manager = new EventManager();
	private static SilentAimState aimState = new SilentAimState();
	private static NightHUD nightHUD = new NightHUD();
	private static CommandManager commandManager = new CommandManager();
	private static BindHandler bindHandler = new BindHandler();
	private static NightScriptEngine scriptEngine = new NightScriptEngine();
	
	@Override
	public void onInitialize() {
		LineType.init();
		NightModule.mc = MinecraftClient.getInstance();
		registerAPIEvents();
		manager.init();
		aimState.init();
		nightHUD.init();
		commandManager.init();
		bindHandler.init();
		
		// Testing
		// String path = MinecraftClient.getInstance().runDirectory.getPath() + "/NightClient/test.ns";
		// scriptEngine.load(new File(path));
		
		//Combat
		ModuleList.register(new AimAssist());
		ModuleList.register(new AutoClicker());
		ModuleList.register(new AutoTotem());
		ModuleList.register(new Criticals());
		ModuleList.register(new AutoPot());
		ModuleList.register(new ShieldDisable());
		
		//Exploit
		ModuleList.register(new AntiDurability());
		ModuleList.register(new AntiVoid());
		ModuleList.register(new Blink());
		ModuleList.register(new BookDupe());
		ModuleList.register(new GodMode());
		ModuleList.register(new Lag());
		
		//Misc
		ModuleList.register(new AutoTool());
		ModuleList.register(new BedBreaker());
		ModuleList.register(new BoxEscape());
		ModuleList.register(new FastBreak());
		ModuleList.register(new FastUse());
		ModuleList.register(new NoFall());
		ModuleList.register(new Stealer());
		
		//Movement
		ModuleList.register(new Flight());
		ModuleList.register(new JumpBoost());
		ModuleList.register(new Knockback());
		ModuleList.register(new RightClickTP());
		ModuleList.register(new Scaffold());
		ModuleList.register(new Speed());
		ModuleList.register(new Sprint());
		
		//Render
		ModuleList.register(new CustomCapes());
		ModuleList.register(new EntityInfo());
		ModuleList.register(new ESP());
		ModuleList.register(new FullBright());
		ModuleList.register(new NoOverlay());
		
		NightFolder.init();
		
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
        	if (openGuiKey.wasPressed()) {
            	client.setScreen(new NightUI());
            }
        });
	}
	
	public void registerAPIEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
        	try {
				manager.fireEvent(EventType.POST_TICK, new EventData(false, EventType.POST_TICK));
			} catch (Exception e) {
				
			}
		});
		
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
        	try {
				manager.fireEvent(EventType.PRE_TICK, new EventData(false, EventType.PRE_TICK));
			} catch (Exception e) {
				
			}
		});
		
		WorldRenderEvents.END_MAIN.register(context -> {
			try {
				manager.fireEvent(EventType.RENDER, new RenderEventData(context));
			} catch (Exception e) {

			}
		});
	}
	
	public static EventManager getEventManager() {
		return manager;
	}
	
	public static SilentAimState getAimState() {
		return aimState;
	}
	
	public static CommandManager getCommandManager() {
		return commandManager;
	}
	
	public static NightScriptEngine getScriptEngine() {
		return scriptEngine;
	}
}
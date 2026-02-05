package io.booogyboooo.nightclient.ui;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

public class NightUIData {
	public static float ZOOM = 1;
	public static final int WIDTH = 250;
	public static final int HEIGHT = 50;
	public static final int SPACING = 30;
	public static int BG_COLOR = 0xbf000000;
	public static int TITLE_COLOR = Color.WHITE.getRGB();
	public static final int ISPACING = 20;
	public static final float RSCALE = 0.33f;
	public static final int OHEIGHT = 36;
	public static int OCOLOR = Color.DARK_GRAY.getRGB();
	public static int BORDER_COLOR = Color.BLACK.getRGB();
	public static int MENABLED = Color.WHITE.getRGB();
	public static int MDISABLED = Color.LIGHT_GRAY.darker().getRGB();
	public static int BCOLOR = Color.GRAY.getRGB();
	public static int ECOLOR = Color.LIGHT_GRAY.getRGB();
	public static TextRenderer TEXT_RENDERER = MinecraftClient.getInstance().textRenderer;

	public static void reset() {
		NightUIData.ZOOM = 1;
		NightUIData.BG_COLOR = 0xbf000000;
		NightUIData.TITLE_COLOR = Color.WHITE.getRGB();
		NightUIData.OCOLOR = Color.DARK_GRAY.getRGB();
		NightUIData.BORDER_COLOR = Color.BLACK.getRGB();
		NightUIData.MENABLED = Color.WHITE.getRGB();
		NightUIData.MDISABLED = Color.LIGHT_GRAY.darker().getRGB();
		NightUIData.BCOLOR = Color.GRAY.getRGB();
		NightUIData.ECOLOR = Color.LIGHT_GRAY.getRGB();
	}
}
package io.booogyboooo.nightclient.util;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class RenderUtil {
	private static List<EntityType<?>> entityTypes = new ArrayList<EntityType<?>>();

	public static List<EntityType<?>> getEntitys() {
		return entityTypes;
	}
	
	public static void addEntity(EntityType<?> entity) {
		entityTypes.add(entity);
	}
	
	public static void removeEntity(EntityType<?> entity) {
		entityTypes.remove(entity);
	}
	
	public static void drawBorder(DrawContext ctx, int x, int y, int w, int h, int color, int thickness) {
	    for (int t = 0; t < thickness; t++) {
	        ctx.fill(x + t, y + t, x + w - t, y + t + 1, color);
	        ctx.fill(x + t, y + h - t - 1, x + w - t, y + h - t, color);
	        ctx.fill(x + t, y + t, x + t + 1, y + h - t, color);
	        ctx.fill(x + w - t - 1, y + t, x + w - t, y + h - t, color);
	    }
	}
	
	public static void drawBlockPosOutline(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float alpha) {
		Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
	    Box box = new Box(x, y, z, x + 1, y + 1, z + 1).offset(-cameraPos.x, -cameraPos.y, -cameraPos.z);
	    drawBoxOutline(vertexConsumer, box, red, green, blue, alpha);
	}
	
	public static void drawBlockPosOutline(VertexConsumer vertexConsumer, BlockPos blockPos, float red, float green, float blue, float alpha) {
		Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
	    Box box = new Box(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1).offset(-cameraPos.x, -cameraPos.y, -cameraPos.z);
	    drawBoxOutline(vertexConsumer, box, red, green, blue, alpha);
	}
	
	public static void drawBoxOutline(VertexConsumer vertexConsumer, Box box, float red, float green, float blue, float alpha) {	
		double x1 = box.minX, x2 = box.maxX;
	    double y1 = box.minY, y2 = box.maxY;
	    double z1 = box.minZ, z2 = box.maxZ;

	    double[][] edges = {
	        {x1, y1, z1, x2, y1, z1}, {x2, y1, z1, x2, y1, z2},
	        {x2, y1, z2, x1, y1, z2}, {x1, y1, z2, x1, y1, z1},

	        {x1, y2, z1, x2, y2, z1}, {x2, y2, z1, x2, y2, z2},
	        {x2, y2, z2, x1, y2, z2}, {x1, y2, z2, x1, y2, z1},

	        {x1, y1, z1, x1, y2, z1}, {x2, y1, z1, x2, y2, z1},
	        {x2, y1, z2, x2, y2, z2}, {x1, y1, z2, x1, y2, z2}
	    };

	    for (double[] edge : edges) {
	        vertexConsumer.vertex((float) edge[0], (float) edge[1], (float) edge[2]).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f).vertex((float) edge[3], (float) edge[4], (float) edge[5]).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f);
	    }
	}
	
    public static final RenderPipeline LINE_NO_DEPTH_PIPELINE = RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET).withLocation("pipeline/line_no_depth").withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build();
    public static final RenderLayer.MultiPhase LINE_NO_DEPTH = RenderLayer.of("nightclientrecode:line_no_depth", 1536, false, true, LINE_NO_DEPTH_PIPELINE, RenderLayer.MultiPhaseParameters.builder().lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(2))).layering(RenderLayer.VIEW_OFFSET_Z_LAYERING).target(RenderLayer.ITEM_ENTITY_TARGET).build(false));
    
}
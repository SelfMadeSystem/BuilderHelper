package uwu.smsgamer.builderhelper.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;

public class RenderUtils {
    public static void startDrawing() {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.disableDepthTest();
    }

    public static void stopDrawing() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.popMatrix();
    }

    public static void drawBox(MatrixStack matrixStack, VertexConsumer vertexConsumer, Vec3d offset, BlockPos pos1, BlockPos pos2, float r, float g, float b, float a) {
        drawOutlinedBox(matrixStack.peek().getModel(), vertexConsumer,
                pos1.getX() - offset.getX(), pos1.getY() - offset.getY(), pos1.getZ() - offset.getZ(),
                pos2.getX() - offset.getX(), pos2.getY() - offset.getY(), pos2.getZ() - offset.getZ(), r, g, b, a);
    }

    private static void drawOutlinedBox(Matrix4f matrix, VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        float minX = (float) Math.min(x1, x2);
        float minY = (float) Math.min(y1, y2);
        float minZ = (float) Math.min(z1, z2);
        float maxX = (float) Math.max(x1, x2);
        float maxY = (float) Math.max(y1, y2);
        float maxZ = (float) Math.max(z1, z2);

        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();

        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
    }

    private static void drawBox(Matrix4f matrix, VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        float minX = (float) Math.min(x1, x2);
        float minY = (float) Math.min(y1, y2);
        float minZ = (float) Math.min(z1, z2);
        float maxX = (float) Math.max(x1, x2);
        float maxY = (float) Math.max(y1, y2);
        float maxZ = (float) Math.max(z1, z2);
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, minY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, minX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, minZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix, maxX, maxY, maxZ).color(red, green, blue, alpha).next();
    }
}

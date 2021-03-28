package uwu.smsgamer.builderhelper.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.utils.RenderUtils;

public interface BoxRenderableItem extends RenderableItem {
    @Override
    default void render(WorldRenderContext ctx, ItemStack stack, World world) {
        float alpha = 0.8F;
        RenderUtils.startDrawing();
        MatrixStack stacks = ctx.matrixStack();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(1, VertexFormats.POSITION_COLOR);

        Vec3d offset = ctx.camera().getPos();

        CompoundTag tag1 = stack.getSubTag("pos1");
        CompoundTag tag2 = stack.getSubTag("pos2");

        BlockPos pos1 = null;
        if (tag1 != null)
            pos1 = new BlockPos(tag1.getInt("x"), tag1.getInt("y"), tag1.getInt("z"));

        BlockPos pos2 = null;
        if (tag2 != null)
            pos2 = new BlockPos(tag2.getInt("x"), tag2.getInt("y"), tag2.getInt("z"));

        if (tag1 != null && tag2 != null)
            drawMainBox(stacks, buffer, offset, pos1, pos2, alpha);

        if (pos1 != null)
            drawFirst(stacks, buffer, offset, pos1, alpha);

        if (pos2 != null)
            drawSecond(stacks, buffer, offset, pos2, alpha);

        tessellator.draw();
        RenderUtils.stopDrawing();
    }

    default void drawFirst(MatrixStack stacks, VertexConsumer buffer, Vec3d offset, BlockPos pos1, float alpha) {
        RenderUtils.drawBox(stacks, buffer, offset, pos1, pos1.add(1, 1, 1), 1, 0, 0, alpha);
    }

    default void drawSecond(MatrixStack stacks, VertexConsumer buffer, Vec3d offset, BlockPos pos2, float alpha) {
        RenderUtils.drawBox(stacks, buffer, offset, pos2, pos2.add(1, 1, 1), 0, 1, 0, alpha);
    }

    void drawMainBox(MatrixStack stacks, VertexConsumer buffer, Vec3d offset, BlockPos pos1, BlockPos pos2, float alpha);

    default void drawBox(MatrixStack stacks, VertexConsumer buffer, Vec3d offset, BlockPos pos1, BlockPos pos2, float alpha) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());

        RenderUtils.drawBox(stacks, buffer, offset,
                new BlockPos(minX, minY, minZ), new BlockPos(maxX + 1, maxY + 1, maxZ + 1), 1, 1, 1, alpha);
    }
}

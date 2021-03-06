package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.render.BoxRenderableItem;
import uwu.smsgamer.builderhelper.utils.BlockUtils;

public class WallsItem extends Item implements BoxRenderableItem {
    public WallsItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        ItemStack stack = miner.getMainHandStack();
        if (miner.isSneaking()) {
            CompoundTag pos1 = stack.getSubTag("pos1");
            CompoundTag pos2 = stack.getSubTag("pos2");

            if (pos1 != null && pos2 != null)
                walls(world, pos1, pos2, Blocks.AIR.getDefaultState(), miner);

            return false;
        }
        CompoundTag tag = stack.getOrCreateSubTag("pos1");

        boolean different = (!tag.contains("x") ||
                (tag.getInt("x") != pos.getX()) ||
                (tag.getInt("y") != pos.getY()) ||
                (tag.getInt("z") != pos.getZ()));

        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());

        if (world.isClient() && different) {
            miner.sendMessage(new TranslatableText("message.builder_helper.pos2", pos.getX(), pos.getY(), pos.getZ()), false);
        }

        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer().isSneaking()) {
            CompoundTag pos1 = context.getStack().getSubTag("pos1");
            CompoundTag pos2 = context.getStack().getSubTag("pos2");

            if (pos1 != null && pos2 != null)
                walls(context.getWorld(), pos1, pos2, context.getWorld().getBlockState(context.getBlockPos()), context.getPlayer());

            return ActionResult.PASS;
        }

        CompoundTag tag = context.getStack().getOrCreateSubTag("pos2");

        BlockPos blockPos = context.getBlockPos();

        boolean different = (!tag.contains("x") ||
                (tag.getInt("x") != blockPos.getX()) ||
                (tag.getInt("y") != blockPos.getY()) ||
                (tag.getInt("z") != blockPos.getZ()));

        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());

        if (context.getWorld().isClient() && different) {
            context.getPlayer().sendMessage(new TranslatableText("message.builder_helper.pos1", blockPos.getX(), blockPos.getY(), blockPos.getZ()), false);
        }

        return different ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    private void walls(World world, CompoundTag pos1, CompoundTag pos2, BlockState state, PlayerEntity player) {
        BlockUtils.walls(world, pos1.getInt("x"), pos1.getInt("y"), pos1.getInt("z"),
                pos2.getInt("x"), pos2.getInt("y"), pos2.getInt("z"), state, player);
    }

    @Override
    public void drawMainBox(MatrixStack stacks, VertexConsumer buffer, Vec3d offset, BlockPos pos1, BlockPos pos2, float alpha) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());
        this.drawBox(stacks, buffer, offset, pos1, pos2, alpha);
        this.drawBox(stacks, buffer, offset, new BlockPos(minX + 1, minY, minZ + 1), new BlockPos(maxX - 1, maxY, maxZ - 1), alpha);
        /*this.drawBox(stacks, buffer, offset, new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, minZ), alpha);
        this.drawBox(stacks, buffer, offset, new BlockPos(maxX, minY, minZ), new BlockPos(maxX, maxY, maxZ), alpha);
        this.drawBox(stacks, buffer, offset, new BlockPos(maxX, minY, maxZ), new BlockPos(minX, maxY, maxZ), alpha);
        this.drawBox(stacks, buffer, offset, new BlockPos(minX, minY, maxZ), new BlockPos(minX, maxY, minZ), alpha);*/
    }
}

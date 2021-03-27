package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.utils.BlockUtils;

public class FillItem extends Item {
    public FillItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        ItemStack stack = miner.getMainHandStack();
        if (miner.isSneaking()) {
            CompoundTag pos1 = stack.getOrCreateSubTag("pos1");
            CompoundTag pos2 = stack.getOrCreateSubTag("pos2");

            fill(world, pos1, pos2, Blocks.AIR.getDefaultState(), miner);

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
            CompoundTag pos1 = context.getStack().getOrCreateSubTag("pos1");
            CompoundTag pos2 = context.getStack().getOrCreateSubTag("pos2");

            fill(context.getWorld(), pos1, pos2, context.getWorld().getBlockState(context.getBlockPos()), context.getPlayer());

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

    private void fill(World world, CompoundTag pos1, CompoundTag pos2, BlockState state, PlayerEntity player) {
        BlockUtils.fill(world, pos1.getInt("x"), pos1.getInt("y"), pos1.getInt("z"),
                pos2.getInt("x"), pos2.getInt("y"), pos2.getInt("z"), state, player);
    }
}

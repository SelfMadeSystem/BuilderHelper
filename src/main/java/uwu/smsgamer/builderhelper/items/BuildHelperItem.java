package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BuildHelperItem extends Item {
    public BuildHelperItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        CompoundTag tag = miner.getMainHandStack().getOrCreateSubTag("pos1");
        BlockPos blockPos = pos;

        boolean different = (!tag.contains("x") ||
                        (tag.getInt("x") != blockPos.getX()) ||
                        (tag.getInt("y") != blockPos.getY()) ||
                        (tag.getInt("z") != blockPos.getZ()));

        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());

        if (world.isClient() && different) {
            miner.sendMessage(new TranslatableText("message.builder_helper.pos2", blockPos.getX(), blockPos.getY(), blockPos.getZ()), false);
        }

        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
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
}

package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

            new FillStuff(world, pos1, pos2).fill(Blocks.AIR.getDefaultState()); // I wanna use WorldEdit.

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

            new FillStuff(context.getWorld(), pos1, pos2).fill(context.getWorld().getBlockState(context.getBlockPos()));

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

    public static class FillStuff {

        private final World world;
        private final int x1;
        private final int y1;
        private final int z1;
        private final int x2;
        private final int y2;
        private final int z2;

        public FillStuff(World world, CompoundTag pos1, CompoundTag pos2) {
            this.world = world;
            this.x1 = pos1.getInt("x");
            this.y1 = pos1.getInt("y");
            this.z1 = pos1.getInt("z");
            this.x2 = pos2.getInt("x");
            this.y2 = pos2.getInt("y");
            this.z2 = pos2.getInt("z");
        }

        public int minX() {
            return Math.min(x1, x2);
        }

        public int minY() {
            return Math.min(y1, y2);
        }

        public int minZ() {
            return Math.min(z1, z2);
        }

        public int maxX() {
            return Math.max(x1, x2);
        }

        public int maxY() {
            return Math.max(y1, y2);
        }

        public int maxZ() {
            return Math.max(z1, z2);
        }

        public void fill(BlockState block) {
            for (BlockPos pos : BlockPos.iterate(minX(), minY(), minZ(), maxX(), maxY(), maxZ()))
                world.setBlockState(pos, block);
        }

        /*@Override
        public Boolean apply(ItemStack itemStack) {
            Item item = itemStack.getItem();
            if (item instanceof BlockItem) {
                Iterator<BlockPos> iterator = BlockPos.iterate(minX(), minY(), minZ(), maxX(), maxY(), maxZ()).iterator();
                System.out.println(iterator.hasNext());

                BlockState block = ((BlockItem) item).getBlock().getDefaultState();

                while (iterator.hasNext()) {
                    BlockPos pos = iterator.next();
                    System.out.println(pos.toString());
                    world.setBlockState(pos, block);
                }

                return true;
            }
            return false;
        }*/
    }
}

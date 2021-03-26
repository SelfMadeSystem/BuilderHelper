package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.FillCommand;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.screens.BlockScreen;

import java.util.Iterator;
import java.util.function.Function;

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
        if (context.getPlayer().isSneaking()) {

            CompoundTag pos1 = context.getStack().getOrCreateSubTag("pos1");
            CompoundTag pos2 = context.getStack().getOrCreateSubTag("pos2");
            if (context.getWorld().isClient()) {
                MinecraftClient.getInstance().openScreen(new BlockScreen(context.getPlayer(),
                        new FillFunction(context.getWorld(), pos1.getInt("x"), pos1.getInt("y"), pos1.getInt("z"),
                                pos2.getInt("x"), pos2.getInt("y"), pos2.getInt("z"))));
            }
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

    // Move to utils.
    public static class FillFunction implements Function<ItemStack, Boolean> {

        private final World world;
        private final int x1;
        private final int y1;
        private final int z1;
        private final int x2;
        private final int y2;
        private final int z2;

        public FillFunction(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.world = world;
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
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

        @Override
        public Boolean apply(ItemStack itemStack) {
            // Todo: This isn't gonna work. It just sets the blocks client side, not server side.
            //  Make it so it sets a state somewhere and next block you place gets copied.

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
        }
    }
}

package uwu.smsgamer.builderhelper.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {
    public static BlockUtils INSTANCE;

    {
        INSTANCE = this;
    }

    private static BlockUtils getInstance() {
        if (INSTANCE == null) new BlockUtils();
        return INSTANCE;
    }

    public static void fill(World world, int x1, int y1, int z1, int x2, int y2, int z2, BlockState state, PlayerEntity player) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        getInstance().fillBlocks(world, minX, minY, minZ, maxX, maxY, maxZ, state, player);
    }

    public static void walls(World world, int x1, int y1, int z1, int x2, int y2, int z2, BlockState state, PlayerEntity player) {
        int minX = Math.min(x1, x2);

        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        getInstance().wallBlocks(world, minX, minY, minZ, maxX, maxY, maxZ, state, player);
    }

    protected void fillBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
        for (BlockPos pos : BlockPos.iterate(minX, minY, minZ, maxX, maxY, maxZ)) world.setBlockState(pos, state);
    }

    protected void wallBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (x == minX || x == maxX) {
                    for (int z = minZ; z <= maxZ; z++) {
                        world.setBlockState(new BlockPos(x, y, z), state);
                    }
                } else {
                    world.setBlockState(new BlockPos(x, y, minZ), state);
                    world.setBlockState(new BlockPos(x, y, maxZ), state);
                }
            }
        }
    }
}

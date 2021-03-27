package uwu.smsgamer.builderhelper.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {
    public static BlockUtils INSTANCE;

    private static BlockUtils getInstance() {
        if (INSTANCE == null) new BlockUtils();
        return INSTANCE;
    }

    {
        INSTANCE = this;
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

    protected void fillBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
        for (BlockPos pos : BlockPos.iterate(minX, minY, minZ, maxX, maxY, maxZ)) world.setBlockState(pos, state);
    }
}

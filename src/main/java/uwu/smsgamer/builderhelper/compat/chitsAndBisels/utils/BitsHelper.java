package uwu.smsgamer.builderhelper.compat.chitsAndBisels.utils;

import io.github.coolmineman.bitsandchisels.api.BitUtils;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BitsHelper {
    public static void hollowBlock(World world, BlockPos pos) {
        if (!world.isClient()) {
            BlockState air = Blocks.AIR.getDefaultState();
            for (int x = 1; x < 15; x++) {
                for (int y = 1; y < 15; y++) {
                    for (int z = 1; z < 15; z++) {
                        BitUtils.setBit(world, pos, x, y, z, air);
                    }
                }
            }
            BitUtils.update(world, pos);
        }
    }
}

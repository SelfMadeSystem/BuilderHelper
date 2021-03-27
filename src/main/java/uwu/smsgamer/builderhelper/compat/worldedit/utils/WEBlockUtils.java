package uwu.smsgamer.builderhelper.compat.worldedit.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.utils.BlockUtils;

public class WEBlockUtils extends BlockUtils {
    @Override
    protected void fillBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
//        LocalSession session = WorldEdit.getInstance().getSessionManager().get(player);
//        EditSession editSession = session.createEditSession();
//        FabricAdapter.adapt(player);
//        editSession.setBlocks(new CuboidRegion(new FabricWorld(world)))
    }
}

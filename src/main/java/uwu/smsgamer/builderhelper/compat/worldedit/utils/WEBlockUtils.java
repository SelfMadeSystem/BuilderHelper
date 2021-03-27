package uwu.smsgamer.builderhelper.compat.worldedit.utils;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.fabric.*;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.utils.BlockUtils;

public class WEBlockUtils extends BlockUtils {
    @Override
    protected void fillBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, net.minecraft.block.BlockState state, PlayerEntity player) {
        if (world.isClient()) {
            super.fillBlocks(world, minX, minY, minZ, maxX, maxY, maxZ, state, player);
            return;
        }
        FabricPlayer owner = FabricAdapter.adaptPlayer((ServerPlayerEntity) player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(owner);
        EditSession editSession = session.createEditSession(owner);

        try {
            editSession.setBlocks(new CuboidRegion(FabricAdapter.adapt(world), BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ)),
                    FuzzyBlockState.builder().type(FabricAdapter.adapt(state)).build());
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        editSession.commit();
        editSession.close();
        session.remember(editSession);
    }

    @Override
    protected void wallBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
        if (world.isClient()) {
            super.wallBlocks(world, minX, minY, minZ, maxX, maxY, maxZ, state, player);
            return;
        }
        FabricPlayer owner = FabricAdapter.adaptPlayer((ServerPlayerEntity) player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(owner);
        EditSession editSession = session.createEditSession(owner);

        try {
            editSession.makeWalls(new CuboidRegion(FabricAdapter.adapt(world), BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ)),
                    FuzzyBlockState.builder().type(FabricAdapter.adapt(state)).build());
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        editSession.commit();
        editSession.close();
        session.remember(editSession);
    }

    @Override
    protected void faceBlocks(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, PlayerEntity player) {
        if (world.isClient()) {
            super.faceBlocks(world, minX, minY, minZ, maxX, maxY, maxZ, state, player);
            return;
        }
        FabricPlayer owner = FabricAdapter.adaptPlayer((ServerPlayerEntity) player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(owner);
        EditSession editSession = session.createEditSession(owner);

        try {
            editSession.makeCuboidFaces(new CuboidRegion(FabricAdapter.adapt(world), BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ)),
                    (Pattern) FuzzyBlockState.builder().type(FabricAdapter.adapt(state)).build());
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
        editSession.commit();
        editSession.close();
        session.remember(editSession);
    }
}

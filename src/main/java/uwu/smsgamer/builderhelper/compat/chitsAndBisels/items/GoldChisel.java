package uwu.smsgamer.builderhelper.compat.chitsAndBisels.items;

import io.github.coolmineman.bitsandchisels.*;
import io.github.coolmineman.bitsandchisels.api.BitUtils;
import io.github.coolmineman.bitsandchisels.api.client.RedBoxCallback;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.network.*;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.BuilderHelper;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.BACCompat;

import java.util.Optional;

public class GoldChisel extends ToolItem {

    public static final Identifier PACKET_ID = new Identifier(BuilderHelper.MOD_ID, "gold_chisel_packet");
    private long lastBreakTick = 0;

    public GoldChisel(Settings settings) {
        super(ToolMaterials.STONE, settings);
    }

    public void init() {
        ServerSidePacketRegistry.INSTANCE.register(PACKET_ID, (packetContext, attachedData) -> {
            BlockPos pos = attachedData.readBlockPos();
            int x = attachedData.readInt();
            int y = attachedData.readInt();
            int z = attachedData.readInt();
            packetContext.getTaskQueue().execute(() -> {
                // Execute on the main thread
                PlayerEntity player = packetContext.getPlayer();
                World world = player.world;
                ItemStack stack = player.getMainHandStack();
                if (world.canSetBlock(pos) && stack.getItem() == BACCompat.getInstance().GOLD_CHISEL_ITEM && player.getBlockPos().getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), true) < 81) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 2; k++) {
                                Optional<BlockState> oldstate = BitUtils.getBit(world, pos, x + i, y + j, z + k);
                                if (oldstate.isPresent() && BitUtils.setBit(world, pos, x + i, y + j, z + k, Blocks.AIR.getDefaultState())) {
                                    BitUtils.update(world, pos);
                                    if (!oldstate.get().isAir()) player.inventory.offerOrDrop(world, BitUtils.getBitItemStack(oldstate.get()));
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    public void initClient() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            Item i = player.getStackInHand(hand).getItem();
            if (i instanceof GoldChisel) {
                return ((GoldChisel)i).interactBreakBlockClient(player, world, pos);
            }
            return ActionResult.PASS;
        });
        RedBoxCallback.EVENT.register((redBoxDrawer, matrixStack, vertexConsumer, worldoffsetx, worldoffsety, worldoffsetz) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player.getMainHandStack().getItem() == BACCompat.getInstance().GOLD_CHISEL_ITEM) {
                HitResult hit = client.crosshairTarget;
                if (hit.getType() == HitResult.Type.BLOCK) {
                    Direction direction = ((BlockHitResult)hit).getSide();
                    BlockPos pos = ((BlockHitResult)hit).getBlockPos();
                    int x = (int) Math.floor(Math.floor(((hit.getPos().getX() - pos.getX()) * 16) + (direction.getOffsetX() * -0.5d)) / 2) * 2;
                    int y = (int) Math.floor(Math.floor(((hit.getPos().getY() - pos.getY()) * 16) + (direction.getOffsetY() * -0.5d)) / 2) * 2;
                    int z = (int) Math.floor(Math.floor(((hit.getPos().getZ() - pos.getZ()) * 16) + (direction.getOffsetZ() * -0.5d)) / 2) * 2;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 2; k++) {
                                redBoxDrawer.drawRedBox(matrixStack, vertexConsumer, pos, x + i, y + j, z + k, worldoffsetx, worldoffsety, worldoffsetz);
                            }
                        }
                    }
                }
            }
        });
    }

    public ActionResult interactBreakBlockClient(PlayerEntity player, World world, BlockPos pos) {
        if (player.world.getTime() - lastBreakTick < 5) return ActionResult.CONSUME;
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        if (hit.getType() == HitResult.Type.BLOCK) {
            Direction direction = ((BlockHitResult)hit).getSide();
            int x = (int) Math.floor(Math.floor(((hit.getPos().getX() - pos.getX()) * 16) + (direction.getOffsetX() * -0.5d)) / 2) * 2;
            int y = (int) Math.floor(Math.floor(((hit.getPos().getY() - pos.getY()) * 16) + (direction.getOffsetY() * -0.5d)) / 2) * 2;
            int z = (int) Math.floor(Math.floor(((hit.getPos().getZ() - pos.getZ()) * 16) + (direction.getOffsetZ() * -0.5d)) / 2) * 2;
            if (world.getBlockEntity(pos) instanceof BitsBlockEntity || BitUtils.exists(BitUtils.getBit(world, pos, x, y, z))) {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(pos);
                passedData.writeInt(x);
                passedData.writeInt(y);
                passedData.writeInt(z);
                ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, passedData);
                lastBreakTick = player.world.getTime();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }
    
}

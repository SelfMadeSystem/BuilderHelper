package uwu.smsgamer.builderhelper.compat.chitsAndBisels.items;

import io.github.coolmineman.bitsandchisels.api.BitUtils;
import io.github.coolmineman.bitsandchisels.api.client.RedBoxCallback;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.*;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import uwu.smsgamer.builderhelper.BuilderHelper;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.BACCompat;

// Todo: Instead of 2x2x2, making it customisable.
// Todo: Make it render properly (the item itself)
// Todo: Fix the block being inside other blocks at certain aiming poses.
public class BigBitItem extends Item {

    public static final Identifier PACKET_ID = new Identifier(BuilderHelper.MOD_ID, "big_bit_packet");

    public BigBitItem(Settings settings) {
        super(settings);
    }

    public void init() {
        ServerSidePacketRegistry.INSTANCE.register(PACKET_ID, (packetContext, attachedData) -> {
            // Get the BlockPos we put earlier in the IO thread
            BlockPos pos = attachedData.readBlockPos();
            int x = attachedData.readInt();
            int y = attachedData.readInt();
            int z = attachedData.readInt();
            Hand hand = attachedData.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
            packetContext.getTaskQueue().execute(() -> {
                // Execute on the main thread
                PlayerEntity player = packetContext.getPlayer();
                World world = player.world;
                if (world.canSetBlock(pos) && player.getBlockPos().getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), true) < 81/* && !BitUtils.exists(BitUtils.getBit(world, pos, x, y, z))*/) {
                    ItemStack stack = player.getStackInHand(hand);
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            for (int k = 0; k < 2; k++) {
                                boolean b = BitUtils.setBit(world, pos, x + i, y + j, z + k, BitUtils.getBit(stack));
                                if (b) BitUtils.update(world, pos);
                            }
                        }
                    }
                }
            });
        });
    }

    public void initClient() {
        RedBoxCallback.EVENT.register((redBoxDrawer, matrixStack, vertexConsumer, worldoffsetx, worldoffsety, worldoffsetz) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player.getMainHandStack().getItem() == BACCompat.getInstance().BIG_BIT_ITEM) {
                HitResult hit = client.crosshairTarget;
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = ((BlockHitResult) hit).getBlockPos();
                    int x = (int) Math.floor(Math.floor((hit.getPos().getX() - pos.getX()) * 16) / 2) * 2;
                    int y = (int) Math.floor(Math.floor((hit.getPos().getY() - pos.getY()) * 16) / 2) * 2;
                    int z = (int) Math.floor(Math.floor((hit.getPos().getZ() - pos.getZ()) * 16) / 2) * 2;

                    if (x > 15) {
                        pos = pos.add(1, 0, 0);
                        x -= 16;
                    }
                    if (y > 15) {
                        pos = pos.add(0, 1, 0);
                        y -= 16;
                    }
                    if (z > 15) {
                        pos = pos.add(0, 0, 1);
                        z -= 16;
                    }
                    if (x < 0) {
                        pos = pos.add(-1, 0, 0);
                        x += 16;
                    }
                    if (y < 0) {
                        pos = pos.add(0, -1, 0);
                        y += 16;
                    }
                    if (z < 0) {
                        pos = pos.add(0, 0, -1);
                        z += 16;
                    }

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

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        World world = context.getWorld();

        if (stack.getSubTag("bit") == null) {
            BlockState state = world.getBlockState(pos);
            context.getStack().putSubTag("bit", NbtHelper.fromBlockState(state));
            return ActionResult.SUCCESS;
        }

        if (!world.isClient) return ActionResult.CONSUME;
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        if (hit.getType() == HitResult.Type.BLOCK) {
            int x = (int) Math.floor(Math.floor((hit.getPos().getX() - pos.getX()) * 16) / 2) * 2;
            int y = (int) Math.floor(Math.floor((hit.getPos().getY() - pos.getY()) * 16) / 2) * 2;
            int z = (int) Math.floor(Math.floor((hit.getPos().getZ() - pos.getZ()) * 16) / 2) * 2;

            if (x > 15) {
                pos = pos.add(1, 0, 0);
                x -= 16;
            }
            if (y > 15) {
                pos = pos.add(0, 1, 0);
                y -= 16;
            }
            if (z > 15) {
                pos = pos.add(0, 0, 1);
                z -= 16;
            }
            if (x < 0) {
                pos = pos.add(-1, 0, 0);
                x += 16;
            }
            if (y < 0) {
                pos = pos.add(0, -1, 0);
                y += 16;
            }
            if (z < 0) {
                pos = pos.add(0, 0, -1);
                z += 16;
            }

            //if (BitUtils.canPlace(world, pos, x, y, z)) {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBlockPos(pos);
                passedData.writeInt(x);
                passedData.writeInt(y);
                passedData.writeInt(z);
                passedData.writeBoolean(context.getHand().equals(Hand.MAIN_HAND));
                ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, passedData);
                return ActionResult.SUCCESS;
            //}

        }
        return ActionResult.PASS;
    }

    @Override
    public Text getName(ItemStack stack) {
        BlockState state = stack.getSubTag("bit") != null ? NbtHelper.toBlockState(stack.getSubTag("bit")) : Blocks.AIR.getDefaultState();
        return new TranslatableText(this.getTranslationKey(stack), new TranslatableText(state.getBlock().getTranslationKey()));
    }
}

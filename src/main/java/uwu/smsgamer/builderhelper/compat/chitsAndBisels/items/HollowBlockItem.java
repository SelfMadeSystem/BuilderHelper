package uwu.smsgamer.builderhelper.compat.chitsAndBisels.items;

import io.github.coolmineman.bitsandchisels.api.client.RedBoxCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.*;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.BACCompat;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.utils.BitsHelper;

public class HollowBlockItem extends Item {
    public HollowBlockItem(Settings settings) {
        super(settings);
    }

    public void initClient() {
        RedBoxCallback.EVENT.register((redBoxDrawer, matrixStack, vertexConsumer, worldoffsetx, worldoffsety, worldoffsetz) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player.getMainHandStack().getItem() == BACCompat.getInstance().HOLLOW_BLOCK_ITEM) {
                HitResult hit = client.crosshairTarget;
                if (hit.getType() == HitResult.Type.BLOCK) {
                    for (int x = 1; x < 15; x++) {
                        for (int y = 1; y < 15; y++) {
                            for (int z = 1; z < 15; z++) {
                                redBoxDrawer.drawRedBox(matrixStack, vertexConsumer, ((BlockHitResult) hit).getBlockPos(), x, y, z, worldoffsetx, worldoffsety, worldoffsetz);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BitsHelper.hollowBlock(context.getWorld(), context.getBlockPos());
        return ActionResult.SUCCESS;
    }
}

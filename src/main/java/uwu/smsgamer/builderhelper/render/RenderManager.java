package uwu.smsgamer.builderhelper.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public class RenderManager {
    private static RenderManager INSTANCE;

    {
        INSTANCE = this;
    }

    public static RenderManager getInstance() {
        if (INSTANCE == null) new RenderManager();
        return INSTANCE;
    }

    public void initClient() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                ItemStack stack = MinecraftClient.getInstance().player.getMainHandStack();
                if (stack.getItem() instanceof RenderableItem) {
                    ((RenderableItem) stack.getItem()).render(ctx, stack, ctx.world());
                }
            }
        });
    }
}

package uwu.smsgamer.builderhelper.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface RenderableItem {
    void render(WorldRenderContext ctx, ItemStack stack, World world);
}

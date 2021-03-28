package uwu.smsgamer.builderhelper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;
import uwu.smsgamer.builderhelper.items.*;
import uwu.smsgamer.builderhelper.render.RenderManager;

public class BuilderHelper implements ModInitializer {
    public static final String MOD_ID = "builder_helper";

    public static final FillItem FILL_ITEM = new FillItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
    public static final WallsItem WALLS_ITEM = new WallsItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
    public static final FacesItem FACES_ITEM = new FacesItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fill_item"), FILL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "walls_item"), WALLS_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "faces_item"), FACES_ITEM);
        RenderManager.getInstance().initClient();
        CompatibilityLoader.loadCompatibilityLoaders();
    }
}

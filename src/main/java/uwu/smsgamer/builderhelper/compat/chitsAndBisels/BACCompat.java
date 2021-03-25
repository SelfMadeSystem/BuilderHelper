package uwu.smsgamer.builderhelper.compat.chitsAndBisels;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.items.HollowBlockHelper;

public class BACCompat extends CompatibilityLoader {
    @Override
    public boolean shouldInitialize() {
        return FabricLoader.getInstance().isModLoaded("bitsandchisels");
    }

//    public final HollowBlockHelper hollowBlockHelperItem = new HollowBlockHelper(new FabricItemSettings().group(BitsAndChisels.OTHER_GROUP).maxCount(1));

    @Override
    public void onInitialize() {
    }
}

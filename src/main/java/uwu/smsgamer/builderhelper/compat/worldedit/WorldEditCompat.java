package uwu.smsgamer.builderhelper.compat.worldedit;

import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;
import uwu.smsgamer.builderhelper.compat.worldedit.utils.WEBlockUtils;
import uwu.smsgamer.builderhelper.utils.BlockUtils;

public class WorldEditCompat extends CompatibilityLoader {
    @Override
    public boolean shouldInitialize() {
        return false;
    }

    @Override
    public void onInitialize() {
        BlockUtils.INSTANCE = new WEBlockUtils();
    }
}

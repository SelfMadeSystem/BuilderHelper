package uwu.smsgamer.builderhelper.compat;

import uwu.smsgamer.builderhelper.compat.chitsAndBisels.BACCompat;
import uwu.smsgamer.builderhelper.compat.worldedit.WorldEditCompat;

public abstract class CompatibilityLoader {
    public static void loadCompatibilityLoaders() {
        BACCompat.getInstance().load();
        WorldEditCompat.getInstance().load();
    }

    public static void loadClientCompatibilityLoaders() {
        BACCompat.getInstance().loadClient();
        WorldEditCompat.getInstance().loadClient();
    }

    public void load() {
        if (shouldInitialize()) onInitialize();
    }

    public void loadClient() {
        if (shouldInitialize()) onInitializeClient();
    }

    public abstract boolean shouldInitialize();

    public abstract void onInitialize();

    public void onInitializeClient() {
    }
}

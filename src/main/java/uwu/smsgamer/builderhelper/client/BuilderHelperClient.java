package uwu.smsgamer.builderhelper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;

@Environment(EnvType.CLIENT)
public class BuilderHelperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CompatibilityLoader.loadClientCompatibilityLoaders();
    }
}

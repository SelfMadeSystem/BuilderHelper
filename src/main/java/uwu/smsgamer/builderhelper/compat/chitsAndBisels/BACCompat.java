package uwu.smsgamer.builderhelper.compat.chitsAndBisels;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import uwu.smsgamer.builderhelper.BuilderHelper;
import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.items.HollowBlockItem;

public class BACCompat extends CompatibilityLoader {
    @Override
    public boolean shouldInitialize() {
        return FabricLoader.getInstance().isModLoaded("bitsandchisels");
    }

    public final HollowBlockItem HOLLOW_BLOCK_ITEM = new HollowBlockItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));

    @Override
    public void onInitialize() {
        System.out.println("BuilderHelper BitsAndChisels Compatibility loading!");
        Registry.register(Registry.ITEM, new Identifier(BuilderHelper.MOD_ID, "hollow_block_item"), HOLLOW_BLOCK_ITEM);
    }
}

package uwu.smsgamer.builderhelper.compat.chitsAndBisels;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import uwu.smsgamer.builderhelper.BuilderHelper;
import uwu.smsgamer.builderhelper.compat.CompatibilityLoader;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.items.*;

public class BACCompat extends CompatibilityLoader {
    private static BACCompat INSTANCE;
    public HollowBlockItem HOLLOW_BLOCK_ITEM;
    public GoldChisel GOLD_CHISEL_ITEM;
    public WoodChisel WOOD_CHISEL_ITEM;

    {
        INSTANCE = this;
    }

    public static BACCompat getInstance() {
        if (INSTANCE == null) new BACCompat();
        return INSTANCE;
    }

    @Override
    public boolean shouldInitialize() {
        return FabricLoader.getInstance().isModLoaded("bitsandchisels");
    }

    @Override
    public void onInitialize() {
        System.out.println("BuilderHelper BitsAndChisels Compatibility loading!");
        GOLD_CHISEL_ITEM = new GoldChisel(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
        WOOD_CHISEL_ITEM = new WoodChisel(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
        HOLLOW_BLOCK_ITEM = new HollowBlockItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
        Registry.register(Registry.ITEM, new Identifier(BuilderHelper.MOD_ID, "hollow_block_item"), HOLLOW_BLOCK_ITEM);
        Registry.register(Registry.ITEM, new Identifier(BuilderHelper.MOD_ID, "gold_chisel"), GOLD_CHISEL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(BuilderHelper.MOD_ID, "wood_chisel"), WOOD_CHISEL_ITEM);
        GOLD_CHISEL_ITEM.init();
        WOOD_CHISEL_ITEM.init();
    }

    @Override
    public void onInitializeClient() {
        HOLLOW_BLOCK_ITEM.initClient();
        GOLD_CHISEL_ITEM.initClient();
        WOOD_CHISEL_ITEM.initClient();
    }
}

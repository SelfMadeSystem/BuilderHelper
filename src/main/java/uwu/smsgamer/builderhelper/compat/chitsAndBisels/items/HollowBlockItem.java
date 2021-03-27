package uwu.smsgamer.builderhelper.compat.chitsAndBisels.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import uwu.smsgamer.builderhelper.compat.chitsAndBisels.utils.BitsHelper;

public class HollowBlockItem extends Item {
    public HollowBlockItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BitsHelper.hollowBlock(context.getWorld(), context.getBlockPos());
        return ActionResult.SUCCESS;
    }
}

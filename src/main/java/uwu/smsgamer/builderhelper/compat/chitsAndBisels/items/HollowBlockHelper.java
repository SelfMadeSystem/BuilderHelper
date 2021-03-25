package uwu.smsgamer.builderhelper.compat.chitsAndBisels.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class HollowBlockHelper extends Item {
    public HollowBlockHelper(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.SUCCESS;
    }
}

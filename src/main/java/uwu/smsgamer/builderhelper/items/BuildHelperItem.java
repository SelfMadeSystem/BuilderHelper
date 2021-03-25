package uwu.smsgamer.builderhelper.items;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class BuildHelperItem extends Item {
    public BuildHelperItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        context.getWorld().setBlockState(context.getBlockPos(), Blocks.AIR.getDefaultState());
        return ActionResult.SUCCESS;
    }
}

package alkalus.main.core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import alkalus.main.config.AsmConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TooltipHandler {

    private static Item mPoppetShelfItem;
    private static Block mPoppetShelfBlock;
    private static ItemStack mPoppetShelfStack;
    private static final Class mPoppetShelfClass;

    static {
        mPoppetShelfClass = ReflectionUtils.getClass("com.emoniph.witchery.blocks.BlockPoppetShelf");
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {

        if (mPoppetShelfBlock == null || mPoppetShelfItem == null || mPoppetShelfStack == null) {
            if (event.itemStack != null && event.itemStack.getItem() != null) {
                // Set Block
                if (mPoppetShelfBlock == null) {
                    Block b = Block.getBlockFromItem(event.itemStack.getItem());
                    if (mPoppetShelfClass.isInstance(b)) {
                        mPoppetShelfBlock = b;
                    }
                }
                if (mPoppetShelfItem == null || mPoppetShelfStack == null) {
                    // Set Item/ItemStack from Block since it matches.
                    if (mPoppetShelfBlock != null) {
                        Item i = Item.getItemFromBlock(mPoppetShelfBlock);
                        if (i != null) {
                            mPoppetShelfItem = i;
                            mPoppetShelfStack = new ItemStack(mPoppetShelfItem);
                        }
                    }
                }
            }
        }

        // Variables are set Ok, now to add a tooltip.
        if (mPoppetShelfStack != null) {
            if (Utils.areStacksEqual(mPoppetShelfStack, event.itemStack, true)) {
                if (!AsmConfig.allowPoppetShelfChunkLoading) {
                    event.toolTip.add(EnumChatFormatting.RED + "This block has chunkloading disabled");
                } else {
                    event.toolTip.add(EnumChatFormatting.GREEN + "This block has chunkloading enabled");
                }
            }
        }
    }
}

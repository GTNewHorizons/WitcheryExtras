package alkalus.main.core.util;

import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.emoniph.witchery.Witchery;

import alkalus.main.config.AsmConfig;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TooltipHandler {

    private static Item POPPET_SHELF_ITEM;

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (POPPET_SHELF_ITEM == null) {
            POPPET_SHELF_ITEM = Item.getItemFromBlock(Witchery.Blocks.POPPET_SHELF);
        } else {
            if (event.itemStack != null && event.itemStack.getItem() == POPPET_SHELF_ITEM) {
                if (!AsmConfig.allowPoppetShelfChunkLoading) {
                    event.toolTip.add(EnumChatFormatting.RED + "This block has chunkloading disabled");
                } else {
                    event.toolTip.add(EnumChatFormatting.GREEN + "This block has chunkloading enabled");
                }
            }
        }
    }
}

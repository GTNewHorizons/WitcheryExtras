package alkalus.main.handlers;

import static alkalus.main.mixins.hooks.EntitySizeManager.OFFSET_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

import alkalus.main.mixins.hooks.EntitySizeManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ClientSizeHandler {

    @SubscribeEvent
    public void onUpdateClient(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }
        EntityPlayer player = event.player;
        if (player == Minecraft.getMinecraft().thePlayer) {
            IExtendedEntityProperties prop = player.getExtendedProperties(OFFSET_PROPERTY);
            if (prop instanceof EntitySizeManager.OffsetContents contents) {
                player.yOffset = 1.62f - contents.currentOffset;
                if (contents.currentOffset == contents.targetOffset) {
                    return;
                }
                if (contents.currentOffset < contents.targetOffset) {
                    contents.currentOffset = Math.min(contents.currentOffset + 0.01F, contents.targetOffset);
                } else {
                    contents.currentOffset = Math.max(contents.currentOffset - 0.01F, contents.targetOffset);
                }
            }
        }
    }
}

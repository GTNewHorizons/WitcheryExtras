package alkalus.main.handlers;

import static alkalus.main.core.WitcheryExtras.NETWORK;
import static alkalus.main.mixins.hooks.EntitySizeManager.OFFSET_PROPERTY;
import static alkalus.main.mixins.hooks.EntitySizeManager.getTargetYOffset;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.IExtendedEntityProperties;

import alkalus.main.mixins.hooks.EntitySizeManager;
import alkalus.main.network.EntitySizeSyncPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ServerSizeHandler {

    @SubscribeEvent
    public void onUpdateServer(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }
        if (event.player instanceof EntityPlayerMP player) {
            float tmp = getTargetYOffset(player);
            IExtendedEntityProperties prop = player.getExtendedProperties(OFFSET_PROPERTY);
            if (prop instanceof EntitySizeManager.OffsetContents contents) {
                if (contents.targetOffset != tmp) {
                    contents.targetOffset = tmp;
                    NETWORK.sendTo(new EntitySizeSyncPacket(tmp), player);
                }
            }
        }
    }
}

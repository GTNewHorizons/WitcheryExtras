package alkalus.main.compat.efr;

import static alkalus.main.handlers.ClientSizeHandler.getScale;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.pose.PlayerScaleEvent;

public class PlayerScaleEventListener {

    @SubscribeEvent
    public void onScale(PlayerScaleEvent event) {
        var player = event.player;
        event.scale = getScale(player);
    }
}

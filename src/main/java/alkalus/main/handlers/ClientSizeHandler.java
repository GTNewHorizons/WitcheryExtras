package alkalus.main.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.brewing.potions.PotionResizing;
import com.emoniph.witchery.util.EntitySizeInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientSizeHandler {

    public static float getTargetYOffset(EntityPlayer player) {
        EntitySizeInfo size = new EntitySizeInfo(player);
        PotionEffect shrunk = Witchery.Potions.RESIZING != null
                ? player.getActivePotionEffect(Witchery.Potions.RESIZING)
                : null;
        if (shrunk == null && size.isDefault) {
            return 0;
        }
        float amp = shrunk != null ? PotionResizing.getScaleFactor(shrunk.getAmplifier()) : 1.0F;
        float scale = size.defaultHeight / 1.8F * amp;
        return 1.8F * (1.0F - scale);
    }

    public static float currentOffset = 0.F;

    @SubscribeEvent
    public void onUpdateClient(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END || event.side == Side.SERVER) {
            return;
        }
        EntityPlayer player = event.player;
        if (player == Minecraft.getMinecraft().thePlayer) {
            float targetOffset = Math.min(getTargetYOffset(player), 1.62f);
            // to keep the origin behavior
            if (targetOffset == 0) currentOffset = targetOffset;
            player.yOffset = 1.62f - currentOffset;
            if (currentOffset == targetOffset) {
                return;
            }
            if (currentOffset < targetOffset) {
                currentOffset = Math.min(currentOffset + 0.01F, targetOffset);
            } else {
                currentOffset = Math.max(currentOffset - 0.01F, targetOffset);
            }
        }
    }
}

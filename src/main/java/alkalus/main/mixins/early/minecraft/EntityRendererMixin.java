package alkalus.main.mixins.early.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import alkalus.main.handlers.ClientSizeHandler;
import alkalus.main.proxy.CommonProxy;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;

    @ModifyVariable(method = "orientCamera", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    private float applyOffset(float origin) {
        if (!CommonProxy.usingEFR && this.mc.renderViewEntity instanceof EntityPlayer player) {
            if (!player.isPlayerSleeping() && !player.isRiding()) {
                return origin + ClientSizeHandler.currentOffset;
            }
        }
        return origin;
    }
}

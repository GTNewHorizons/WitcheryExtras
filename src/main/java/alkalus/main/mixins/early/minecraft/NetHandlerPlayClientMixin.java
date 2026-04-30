package alkalus.main.mixins.early.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import alkalus.main.mixins.hooks.EntitySizeManager;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {

    @ModifyExpressionValue(
            method = "handlePlayerPosLook",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/play/server/S08PacketPlayerPosLook;func_148928_d()D"))
    private double applyOffset(double origin) {
        return origin - EntitySizeManager.OffsetContents.getCurrentOffset(Minecraft.getMinecraft().thePlayer);
    }
}

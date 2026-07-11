package alkalus.main.mixins.early.minecraft;

import net.minecraft.network.play.client.C03PacketPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import alkalus.main.handlers.ClientSizeHandler;

@Mixin(C03PacketPlayer.C06PacketPlayerPosLook.class)
public class C06PacketPlayerPosLookMixin {

    @ModifyVariable(method = "<init>(DDDDFFZ)V", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private static double applyYOffset(double origin) {
        if (origin == -999) return origin;
        return origin + ClientSizeHandler.currentOffset;
    }
}

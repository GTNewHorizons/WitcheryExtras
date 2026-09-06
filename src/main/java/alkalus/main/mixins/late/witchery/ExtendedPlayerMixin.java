package alkalus.main.mixins.late.witchery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.common.ExtendedPlayer;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ExtendedPlayer.class, remap = false)
public class ExtendedPlayerMixin {

    @Shadow
    private EntityPlayer player;

    @Inject(method = "sync", at = @At("HEAD"), remap = false, cancellable = true)
    private void sync(CallbackInfo ci) {
        if (player instanceof FakePlayer) ci.cancel();
    }
}

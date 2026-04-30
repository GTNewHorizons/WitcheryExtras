package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.client.PlayerRender;

@Mixin(value = PlayerRender.class, remap = false)
public class PlayerRenderMixin {

    @Inject(
            method = "onRenderTick",
            at = @At(value = "NEW", target = "com/emoniph/witchery/util/EntitySizeInfo"),
            cancellable = true)
    private void onRenderTick(CallbackInfo ci) {
        ci.cancel();
    }
}

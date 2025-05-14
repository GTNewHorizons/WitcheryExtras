package alkalus.main.mixins.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.blocks.BlockPoppetShelf.TileEntityPoppetShelf;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import alkalus.main.asm.AsmConfig;

@SuppressWarnings("UnusedMixin")
@Mixin(TileEntityPoppetShelf.class)
public class TileEntityPoppetShelfMixin {

    // Inject directly after the super call
    @Inject(
            method = "initiate",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/emoniph/witchery/blocks/TileEntityBase;initiate()V",
                    shift = At.Shift.AFTER),
            cancellable = true,
            remap = false) // not an MC method
    public void checkCreateTicketConfig(CallbackInfo ci) {
        // Disallow chunk loading if not configured
        if (!AsmConfig.allowPoppetShelfChunkLoading) ci.cancel();
    }

    // Wrap the world.isRemote check
    @ModifyExpressionValue(
            method = "invalidate",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"))
    public boolean checkReleaseTicketConfig(boolean isRemote) {
        // The check is inverted (i.e., '!world.isRemote'), so our logic must also invert the config.
        // !thisMethod(isRemote)
        // ->
        // !(isRemote || !allowChunkLoading)
        // ->
        // !isRemote && allowChunkLoading
        return isRemote || !AsmConfig.allowPoppetShelfChunkLoading;
    }
}

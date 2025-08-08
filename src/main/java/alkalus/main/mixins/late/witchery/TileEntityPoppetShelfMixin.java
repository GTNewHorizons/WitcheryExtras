package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.blocks.BlockPoppetShelf.TileEntityPoppetShelf;
import com.emoniph.witchery.blocks.TileEntityBase;
import com.emoniph.witchery.util.Log;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

import alkalus.main.config.AsmConfig;

@Mixin(TileEntityPoppetShelf.class)
public class TileEntityPoppetShelfMixin extends TileEntityBase {

    @Inject(
        method = "initiate",
        cancellable = true,
        remap = false,
        at = @At(
            value = "INVOKE",
            target = "Lcom/emoniph/witchery/blocks/TileEntityBase;initiate()V",
            shift = At.Shift.AFTER))
    private void checkConfig(CallbackInfo ci) {
        if (!AsmConfig.allowPoppetShelfChunkLoading) {
            ci.cancel();
        }
    }

    @WrapWithCondition(
        method = "invalidate",
        at = @At(
            value = "INVOKE",
            target = "Lcom/emoniph/witchery/util/Log;warning(Ljava/lang/String;)V",
            remap = false))
    private boolean muteWarning(Log log, String message) {
        return AsmConfig.allowPoppetShelfChunkLoading;
    }
}

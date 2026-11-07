package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.emoniph.witchery.common.ExtendedPlayer;
import com.emoniph.witchery.common.GenericEvents;

@Mixin(value = GenericEvents.class, remap = false)
public class GenericEventsMixin {

    // Linked to ServerTickEventsMixin.scheduleSyncAndRestorePlayerInventoryFrom
    @Redirect(
            method = "onPlayerCloneEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/emoniph/witchery/common/ExtendedPlayer;restorePlayerInventoryFrom(Lcom/emoniph/witchery/common/ExtendedPlayer;)V"))
    private void suppressRestorePlayerInventoryFrom(ExtendedPlayer instance, ExtendedPlayer original) {}
}

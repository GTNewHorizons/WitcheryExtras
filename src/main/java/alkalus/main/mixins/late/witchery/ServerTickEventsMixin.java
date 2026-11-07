package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.emoniph.witchery.common.ExtendedPlayer;
import com.emoniph.witchery.common.ServerTickEvents;

@Mixin(value = ServerTickEvents.class, remap = false)
public class ServerTickEventsMixin {

    // Linked to GenericEventsMixin.suppressRestorePlayerInventoryFrom
    @Redirect(
            method = "onPlayerRespawn",
            at = @At(value = "INVOKE", target = "Lcom/emoniph/witchery/common/ExtendedPlayer;scheduleSync()V"))
    private void scheduleSyncAndRestorePlayerInventoryFrom(ExtendedPlayer instance) {
        instance.restorePlayerInventoryFrom(instance);
        instance.scheduleSync();
    }
}

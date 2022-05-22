package alkalus.main.mixins.witchery;

import com.emoniph.witchery.blocks.BlockSpinningWheel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@SuppressWarnings("UnusedMixin")
@Mixin(value = BlockSpinningWheel.TileEntitySpinningWheel.class)
public class TileEntitySpinningWheelMixin {

    @Shadow(remap = false)
    private ItemStack[] slots;

    @Inject(method = "updateIfContained", at = @At("HEAD"), remap = false, cancellable = true)
    private void onUpdateIfContained(ArrayList<ItemStack> available, int slot, CallbackInfo ci) {
        if (this.slots[slot] != null) {
            for (int i = 0; i < available.size(); ++i) {
                if (available.get(i).isItemEqual(this.slots[slot])) {
/* - */             // --this.slots[slot].stackSize;
/* + */             this.slots[slot].stackSize -= available.get(i).stackSize;
                    if (this.slots[slot].stackSize <= 0) {
                        this.slots[slot] = null;
                    }

                    available.remove(i);
                    ci.cancel();
                    return;
                }
            }
        }
        ci.cancel();
    }
}

package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.emoniph.witchery.blocks.BlockDistillery;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@SuppressWarnings("UnusedMixin")
@Mixin(value = BlockDistillery.TileEntityDistillery.class, remap = false)
public abstract class TileEntityDistilleryMixin {

    // Manually specify obfuscation mapping, and require that exactly one hits.
    // The mixin AP can't find it automatically for some reason
    @ModifyExpressionValue(
            method = { "isItemValidForSlot", "func_94041_b" },
            at = @At(value = "CONSTANT", args = "intValue=3"),
            require = 1,
            allow = 1)
    private int witcheryextras$fixSlotInsertion(int original) {
        return 2;
    }
}

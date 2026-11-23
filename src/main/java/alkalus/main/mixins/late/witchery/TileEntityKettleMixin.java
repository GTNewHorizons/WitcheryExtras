package alkalus.main.mixins.late.witchery;

import net.minecraft.item.ItemStack;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.emoniph.witchery.blocks.BlockKettle.TileEntityKettle;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

@SuppressWarnings("UnusedMixin")
@Mixin(value = TileEntityKettle.class, remap = false)
public class TileEntityKettleMixin {

    // Pipes that probe canExtractItem with the whole stack and then write back the already split
    // stack (e.g. CodeChickenLib InventoryUtils#decrStackSize used by Translocators) hit the
    // reduction == 0 fallback, which billed the whole probed amount instead of what was taken.
    // Bill the difference between the probed size and the written back size instead.
    @ModifyExpressionValue(
            method = { "setInventorySlotContents", "func_70299_a" },
            at = @At(
                    value = "FIELD",
                    target = "Lcom/emoniph/witchery/blocks/BlockKettle$TileEntityKettle;lastExtractionQuantity:I",
                    opcode = Opcodes.GETFIELD),
            remap = false)
    private int witcheryextras$billActualExtraction(int probedQuantity, @Local(argsOnly = true) ItemStack stack) {
        return probedQuantity - stack.stackSize;
    }
}

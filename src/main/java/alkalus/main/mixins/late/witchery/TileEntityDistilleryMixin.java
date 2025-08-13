package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.emoniph.witchery.blocks.BlockDistillery;

@SuppressWarnings("UnusedMixin")
@Mixin(BlockDistillery.TileEntityDistillery.class)
public class TileEntityDistilleryMixin {

    @ModifyConstant(method = "isItemValidForSlot", constant = @Constant(intValue = 3), remap = false)
    private int witcheryextras$fixSlotInsertion(int original) {
        return 2;
    }
}

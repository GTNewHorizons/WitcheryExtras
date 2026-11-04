package alkalus.main.mixins.late.witchery;

import net.minecraft.util.StatCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.emoniph.witchery.item.ItemLeonardsUrn;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ItemLeonardsUrn.InventoryLeonardsUrn.class)
public class ItemLeonardsUrnMixin {

    @Inject(method = { "getInventoryName", "func_145825_b" }, at = @At("HEAD"), cancellable = true, remap = false)
    private void witcheryextras$useItemNameKey(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(StatCollector.translateToLocal("gui.witcheryextras.leonardsurn.title"));
    }
}

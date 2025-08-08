package alkalus.main.mixins.late.witchery;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.emoniph.witchery.crafting.SpinningRecipes;

@SuppressWarnings("UnusedMixin")
@Mixin(SpinningRecipes.SpinningRecipe.class)
public class SpinningRecipeMixin {

    @Inject(method = "indexOf", at = @At("HEAD"), remap = false, cancellable = true)
    private void indexOf(ArrayList<ItemStack> list, ItemStack item, CallbackInfoReturnable<Integer> cir) {
        for (int i = 0; i < list.size(); ++i) {
            /* - */
            // if (list.get(i).isItemEqual(item)) {
            /* + */ if (list.get(i)
                .isItemEqual(item) && list.get(i).stackSize >= item.stackSize) {
                cir.setReturnValue(i); // return i;
                return;
            }
        }
        cir.setReturnValue(-1); // return -1;
    }
}

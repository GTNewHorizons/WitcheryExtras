package alkalus.main.mixins.late.witchery;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;

import com.emoniph.witchery.entity.EntityNightmare;
import com.emoniph.witchery.item.ItemGeneral;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import baubles.api.BaublesApi;

@Mixin(EntityNightmare.class)
public class EntityNightmareMixin_Bauble {

    @WrapMethod(method = "findInInventory", remap = false)
    private boolean witcheryExtras$findInInventory(InventoryPlayer inventory, ItemGeneral.SubItem item,
            Operation<Boolean> original) {

        IInventory baubles = BaublesApi.getBaubles(inventory.player);
        for (int slot = 0; slot < baubles.getSizeInventory(); slot++) {
            ItemStack bauble = baubles.getStackInSlot(slot);
            if (item.isMatch(bauble)) {
                return true;
            }
        }

        return original.call(inventory, item);
    }
}

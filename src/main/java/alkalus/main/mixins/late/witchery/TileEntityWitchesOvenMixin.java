package alkalus.main.mixins.late.witchery;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.util.Utils;

@Mixin(TileEntityWitchesOven.class)
public abstract class TileEntityWitchesOvenMixin extends TileEntity implements ISidedInventory {

    @Shadow(remap = false)
    private ItemStack[] furnaceItemStacks;

    @Shadow
    public abstract int getInventoryStackLimit();

    @Shadow(remap = false)
    protected abstract void generateByProduct(ItemStack itemstack);

    @Inject(
            method = "updateEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;markBlockForUpdate(III)V",
                    shift = At.Shift.AFTER))
    private void saveTile(CallbackInfo ci) {
        this.markDirty();
    }

    /**
     * @author - Alkalus
     * @reason overwrite original recipe map
     */
    @Overwrite(remap = false)
    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        OvenRecipes.OvenRecipe aValidRecipe = null;
        ItemStack aJarStack = this.furnaceItemStacks[4];
        int aJarCount = aJarStack != null ? aJarStack.stackSize : 0;
        // Do we have any recipes without jar usage?
        if (aJarCount == 0) {
            for (OvenRecipes.OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= 0) {
                    if (Utils.areStacksEqual(r.inputs, this.furnaceItemStacks[0])) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipes.OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= aJarCount) {
                    if (Utils.areStacksEqual(r.inputs, this.furnaceItemStacks[0])) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        }

        if (aValidRecipe == null) {
            return false;
        } else {
            final ItemStack itemstack = aValidRecipe.output;
            final ItemStack outputSlot = this.furnaceItemStacks[2];
            if (outputSlot == null) {
                return true;
            }
            if (!outputSlot.isItemEqual(itemstack)) {
                return false;
            }
            final int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
            return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
        }
    }

    /**
     * @author - Alkalus
     * @reason overwrite original recipe map
     */
    @Overwrite(remap = false)
    public void smeltItem() {
        OvenRecipes.OvenRecipe aValidRecipe = null;
        ItemStack aJarStack = this.furnaceItemStacks[4];
        int aJarCount = aJarStack != null ? aJarStack.stackSize : 0;
        // Do we have any recipes without jar usage?
        if (aJarCount == 0) {
            for (OvenRecipes.OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= 0) {
                    if (Utils.areStacksEqual(r.inputs, this.furnaceItemStacks[0])) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipes.OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= aJarCount) {
                    if (Utils.areStacksEqual(r.inputs, this.furnaceItemStacks[0])) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        }

        if (aValidRecipe != null) {
            if (this.canSmelt()) {
                final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
                if (this.furnaceItemStacks[2] == null) {
                    this.furnaceItemStacks[2] = itemstack.copy();
                } else if (this.furnaceItemStacks[2].isItemEqual(itemstack)) {
                    final ItemStack itemStack = this.furnaceItemStacks[2];
                    itemStack.stackSize += itemstack.stackSize;
                }
                this.generateByProduct(itemstack);
                final ItemStack itemStack2 = this.furnaceItemStacks[0];
                --itemStack2.stackSize;
                if (this.furnaceItemStacks[0].stackSize <= 0) {
                    this.furnaceItemStacks[0] = null;
                }
            }
        }
    }

}

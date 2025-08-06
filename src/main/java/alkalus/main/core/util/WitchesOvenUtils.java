package alkalus.main.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;

public class WitchesOvenUtils {

    private static final int SLOT_TO_COOK = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_COOKED = 2;
    private static final int SLOT_BY_PRODUCT = 3;
    private static final int SLOT_JARS = 4;

    public static ItemStack[] getFurnaceItemStacks(TileEntityWitchesOven aTile) {
        return (ItemStack[]) ReflectionUtils.getFieldValue(ReflectionUtils.getField(TileEntityWitchesOven.class, "furnaceItemStacks"), aTile);
    }

    public static int getEmptyJarCount(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getEmptyJarSlot(aTile);
        return aJarStack != null ? aJarStack.stackSize : 0;
    }

    public static ItemStack getInputSlot(TileEntityWitchesOven aTile) {
        return getFurnaceItemStacks(aTile)[SLOT_TO_COOK];
    }

    public static ItemStack getFuelSlot(TileEntityWitchesOven aTile) {
        return getFurnaceItemStacks(aTile)[SLOT_FUEL];
    }

    public static ItemStack getOutputSlot(TileEntityWitchesOven aTile) {
        return getFurnaceItemStacks(aTile)[SLOT_COOKED];
    }

    public static ItemStack getFumeOutputSlot(TileEntityWitchesOven aTile) {
        return getFurnaceItemStacks(aTile)[SLOT_BY_PRODUCT];
    }

    public static ItemStack getEmptyJarSlot(TileEntityWitchesOven aTile) {
        return getFurnaceItemStacks(aTile)[SLOT_JARS];
    }

    public static void setInputSlot(TileEntityWitchesOven aTile, ItemStack aNewVal) {
        getFurnaceItemStacks(aTile)[SLOT_TO_COOK] = aNewVal;
    }

    public static void setFuelSlot(TileEntityWitchesOven aTile, ItemStack aNewVal) {
        getFurnaceItemStacks(aTile)[SLOT_FUEL] = aNewVal;
    }

    public static void setOutputSlot(TileEntityWitchesOven aTile, ItemStack aNewVal) {
        getFurnaceItemStacks(aTile)[SLOT_COOKED] = aNewVal;
    }

    public static void setFumeOutputSlot(TileEntityWitchesOven aTile, ItemStack aNewVal) {
        getFurnaceItemStacks(aTile)[SLOT_BY_PRODUCT] = aNewVal;
    }

    public static void setEmptyJarSlot(TileEntityWitchesOven aTile, ItemStack aNewVal) {
        getFurnaceItemStacks(aTile)[SLOT_JARS] = aNewVal;
    }

    public static boolean canSmelt(TileEntityWitchesOven aTile) {
        if (getInputSlot(aTile) == null) {
            return false;
        }
        OvenRecipe aValidRecipe = null;
        int aJarCount = getEmptyJarCount(aTile);
        // Do we have any recipes without jar usage?
        if (aJarCount == 0) {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= 0) {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= aJarCount) {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
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
            final ItemStack outputSlot = getOutputSlot(aTile);
            if (outputSlot == null) {
                return true;
            }
            if (!outputSlot.isItemEqual(itemstack)) {
                return false;
            }
            final int result = getOutputSlot(aTile).stackSize + itemstack.stackSize;

            return result <= aTile.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
        }
    }

    public static void smeltItem(TileEntityWitchesOven aTile) {

        OvenRecipe aValidRecipe = null;
        int aJarCount = getEmptyJarCount(aTile);
        // Do we have any recipes without jar usage?
        if (aJarCount == 0) {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= 0) {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars <= aJarCount) {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        }

        if (aValidRecipe != null) {
            if (canSmelt(aTile)) {
                final ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getInputSlot(aTile));
                if (getOutputSlot(aTile) == null) {
                    setOutputSlot(aTile, itemstack.copy());
                } else if (getOutputSlot(aTile).isItemEqual(itemstack)) {
                    final ItemStack itemStack = getOutputSlot(aTile);
                    itemStack.stackSize += itemstack.stackSize;
                }
                generateByProduct(aTile, itemstack);
                final ItemStack itemStack2 = getInputSlot(aTile);
                --itemStack2.stackSize;
                if (getInputSlot(aTile).stackSize <= 0) {
                    setInputSlot(aTile, null);
                }
            }
        }
    }

}

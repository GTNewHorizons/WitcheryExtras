package alkalus.main.core.util;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.FMLCommonHandler;

public class Utils {

    /**
     * The Item WildCard Tag. Even shorter than the "-1" of the past
     */
    public static final short W = OreDictionary.WILDCARD_VALUE;

    public static synchronized ItemStack getSimpleStack(final Item x, final int i) {
        return getSimpleStack(new ItemStack(x), i);
    }

    public static synchronized ItemStack getSimpleStack(final ItemStack x, final int i) {
        try {
            final ItemStack r = x.copy();
            r.stackSize = i;
            return r;
        } catch (final Throwable e) {
            return null;
        }
    }

    public static ItemStack[] getAllItemsFromOreDictEntry(String oreDictName) {
        return getAllItemsFromOreDictEntry(oreDictName, new ItemStack[] {});
    }

    public static ItemStack[] getAllItemsFromOreDictEntry(String oreDictName, ItemStack[] exclusions) {
        HashSet<ItemStack> Q = new HashSet<ItemStack>();
        if (OreDictionary.doesOreNameExist(oreDictName)) {
            for (ItemStack i : OreDictionary.getOres(oreDictName)) {
                if (i != null) {
                    if (exclusions.length > 0) {
                        for (int r = 0; r < exclusions.length; r++) {
                            if (!i.equals(exclusions[r]) && !ItemStack.areItemStacksEqual(i, exclusions[r])) {
                                Q.add(i);
                            } else {
                                continue;
                            }
                        }
                    } else {
                        Q.add(i);
                    }
                }
            }
        }

        ItemStack[] V = new ItemStack[] {};
        int mSlot = 0;
        if (!Q.isEmpty()) {
            V = new ItemStack[Q.size()];
            for (ItemStack w : Q) {
                V[mSlot++] = w;
            }
        }
        return V;
    }

    @SuppressWarnings("unchecked")
    public static synchronized boolean removeAllCraftingRecipesByOutputItem(final ItemStack I) {
        if (I == null) {
            return false;
        }
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        return recipes
                .removeIf(s -> (s != null) && (s.getRecipeOutput() != null) && (s.getRecipeOutput().isItemEqual(I)));
    }

    public static void registerEvent(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
    }

    public static boolean hasValidOreDictTag(String validTag, ItemStack hasTag) {
        if (hasTag != null) {
            for (int tag : OreDictionary.getOreIDs(hasTag)) {
                String oreName = OreDictionary.getOreName(tag);
                if (oreName.equalsIgnoreCase(validTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack simpleMetaStack(final Item item, final int meta, final int size) {
        try {
            if (item == null) {
                return null;
            }
            return new ItemStack(item, size, meta);

        } catch (final NullPointerException e) {
            return null;
        }
    }

    public static ItemStack simpleMetaStack(final Block block, final int meta, final int size) {
        return simpleMetaStack(Item.getItemFromBlock(block), meta, size);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
        return areStacksEqual(aStack1, aStack2, false);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return aStack1 != null && aStack2 != null
                && aStack1.getItem() == aStack2.getItem()
                && (aIgnoreNBT || ((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null))
                        && (aStack1.getTagCompound() == null
                                || aStack1.getTagCompound().equals(aStack2.getTagCompound())))
                && (Items.feather.getDamage(aStack1) == Items.feather.getDamage(aStack2)
                        || Items.feather.getDamage(aStack1) == W
                        || Items.feather.getDamage(aStack2) == W);
    }
}

package alkalus.main.core.util;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;
import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockWitchesOven;
import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;
import com.emoniph.witchery.util.Config;
import com.emoniph.witchery.util.Log;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

@SuppressWarnings("unused")
public class WitchesOvenUtils {

    private static final int COOK_TIME = 180;
    private static final double FUNNEL_CHANCE = 0.25;
    private static final double FILTERED_FUNNEL_CHANCE = 0.3;
    private static final double DOUBLED_FILTERED_FUNNEL_CHANCE = 0.8;
    private static final int SLOT_TO_COOK = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_COOKED = 2;
    private static final int SLOT_BY_PRODUCT = 3;
    private static final int SLOT_JARS = 4;
    private static final int[] slots_top = new int[] {0, 4};
    private static final int[] slots_bottom = new int[] {4, 1};
    private static final int[] slots_sides = new int[] {3, 2, 4, 1};

    public static ItemStack[] getFurnaceItemStacks(TileEntityWitchesOven aTile) {
        return (ItemStack[]) ReflectionUtils.getFieldValue(
                ReflectionUtils.getField(TileEntityWitchesOven.class, "furnaceItemStacks"), aTile);
    }

    public static int[] getSlotsTop(TileEntityWitchesOven aTile) {
        return (int[]) ReflectionUtils.getFieldValue(
                ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_top"), aTile);
    }

    public static int[] getSlotsBottom(TileEntityWitchesOven aTile) {
        return (int[]) ReflectionUtils.getFieldValue(
                ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_bottom"), aTile);
    }

    public static int[] getSlotsSides(TileEntityWitchesOven aTile) {
        return (int[]) ReflectionUtils.getFieldValue(
                ReflectionUtils.getField(TileEntityWitchesOven.class, "slots_sides"), aTile);
    }

    public static void updateEntity(TileEntityWitchesOven aTile) {
        final boolean flag = aTile.furnaceBurnTime > 0;
        boolean aDoBlockUpdate = false;
        if (aTile.furnaceBurnTime > 0) {
            --aTile.furnaceBurnTime;
        }
        if (!aTile.getWorldObj().isRemote) {

            if (canSmelt(aTile)) {

                // Add Fuel
                if (aTile.furnaceBurnTime == 0) {
                    final int itemBurnTime = TileEntityFurnace.getItemBurnTime(getFuelSlot(aTile));
                    aTile.furnaceBurnTime = itemBurnTime;
                    aTile.currentItemBurnTime = itemBurnTime;
                    if (aTile.furnaceBurnTime > 0) {
                        aDoBlockUpdate = true;
                        if (getFuelSlot(aTile) != null) {
                            final ItemStack itemStack = getFuelSlot(aTile);
                            --itemStack.stackSize;
                            if (getFuelSlot(aTile).stackSize == 0) {
                                setFuelSlot(aTile, getFuelSlot(aTile).getItem().getContainerItem(getFuelSlot(aTile)));
                            }
                        }
                    }
                }

                // Try do recipe
                if (aTile.isBurning()) {
                    ++aTile.furnaceCookTime;
                    if (aTile.furnaceCookTime >= getCookTime(aTile)) {
                        aTile.furnaceCookTime = 0;
                        smeltItem(aTile);
                        aDoBlockUpdate = true;
                    }
                } else {
                    aTile.furnaceCookTime = 0;
                }
            }

            if (flag != aTile.furnaceBurnTime > 0) {
                aDoBlockUpdate = true;
                BlockWitchesOven.updateWitchesOvenBlockState(
                        aTile.furnaceBurnTime > 0, aTile.getWorldObj(), aTile.xCoord, aTile.yCoord, aTile.zCoord);
            }
        }
        if (aDoBlockUpdate) {
            aTile.markDirty();
            aTile.getWorldObj().markBlockForUpdate(aTile.xCoord, aTile.yCoord, aTile.zCoord);
        }
    }

    public static int getEmptyJarCount(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getEmptyJarSlot(aTile);
        return aJarStack != null ? aJarStack.stackSize : 0;
    }

    public static ItemStack getInputSlot(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getFurnaceItemStacks(aTile)[SLOT_TO_COOK];
        return aJarStack != null ? aJarStack : null;
    }

    public static ItemStack getFuelSlot(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getFurnaceItemStacks(aTile)[SLOT_FUEL];
        return aJarStack != null ? aJarStack : null;
    }

    public static ItemStack getOutputSlot(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getFurnaceItemStacks(aTile)[SLOT_COOKED];
        return aJarStack != null ? aJarStack : null;
    }

    public static ItemStack getFumeOutputSlot(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getFurnaceItemStacks(aTile)[SLOT_BY_PRODUCT];
        return aJarStack != null ? aJarStack : null;
    }

    public static ItemStack getEmptyJarSlot(TileEntityWitchesOven aTile) {
        ItemStack aJarStack = getFurnaceItemStacks(aTile)[SLOT_JARS];
        return aJarStack != null ? aJarStack : null;
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
                if (r.jars > 0) {
                    continue;
                } else {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars > aJarCount) {
                    continue;
                } else {
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

            // return result <= aTile.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
            return result <= aTile.func_70297_j_() && result <= itemstack.getMaxStackSize();
        }
    }

    public static void smeltItem(TileEntityWitchesOven aTile) {

        OvenRecipe aValidRecipe = null;
        int aJarCount = getEmptyJarCount(aTile);
        // Do we have any recipes without jar usage?
        if (aJarCount == 0) {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars > 0) {
                    continue;
                } else {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        } else {
            for (OvenRecipe r : OvenRecipes.getRecipeMap()) {
                if (r.jars > aJarCount) {
                    continue;
                } else {
                    if (Utils.areStacksEqual(r.inputs, getInputSlot(aTile))) {
                        aValidRecipe = r;
                        break;
                    }
                }
            }
        }

        if (aValidRecipe == null) {
            return;
        } else {
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

    public static int getFumeFunnels(TileEntityWitchesOven aTile) {
        int funnels = 0;
        final int meta = aTile.getWorldObj().getBlockMetadata(aTile.xCoord, aTile.yCoord, aTile.zCoord);
        switch (meta) {
            case 2:
            case 3: {
                funnels += (isFumeFunnel(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, meta) ? 1 : 0);
                funnels += (isFumeFunnel(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, meta) ? 1 : 0);
                break;
            }
            case 4:
            case 5: {
                funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, meta) ? 1 : 0);
                funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, meta) ? 1 : 0);
                break;
            }
        }
        funnels += (isFumeFunnel(aTile, aTile.xCoord, aTile.yCoord + 1, aTile.zCoord, meta) ? 1 : 0);
        return funnels;
    }

    public static boolean isFumeFunnel(
            TileEntityWitchesOven aTile, final int xCoord, final int yCoord, final int zCoord, final int meta) {
        final Block block = aTile.getWorldObj().getBlock(xCoord, yCoord, zCoord);
        return (block == Witchery.Blocks.OVEN_FUMEFUNNEL || block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED)
                && aTile.getWorldObj().getBlockMetadata(xCoord, yCoord, zCoord) == meta;
    }

    public static double getFumeFunnelsChance(TileEntityWitchesOven aTile) {
        double funnels = 0.0;
        switch (aTile.getWorldObj().getBlockMetadata(aTile.xCoord, aTile.yCoord, aTile.zCoord)) {
            case 2: {
                funnels += getFumeFunnelChance(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, 2);
                funnels += getFumeFunnelChance(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, 2);
                break;
            }
            case 3: {
                funnels += getFumeFunnelChance(aTile, aTile.xCoord + 1, aTile.yCoord, aTile.zCoord, 3);
                funnels += getFumeFunnelChance(aTile, aTile.xCoord - 1, aTile.yCoord, aTile.zCoord, 3);
                break;
            }
            case 4: {
                funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, 4);
                funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, 4);
                break;
            }
            case 5: {
                funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord + 1, 5);
                funnels += getFumeFunnelChance(aTile, aTile.xCoord, aTile.yCoord, aTile.zCoord - 1, 5);
                break;
            }
        }
        return funnels;
    }

    public static double getFumeFunnelChance(
            TileEntityWitchesOven aTile, final int x, final int y, final int z, final int meta) {
        final Block block = aTile.getWorldObj().getBlock(x, y, z);
        if (block == Witchery.Blocks.OVEN_FUMEFUNNEL) {
            if (aTile.getWorldObj().getBlockMetadata(x, y, z) == meta) {
                return 0.25;
            }
        } else if (block == Witchery.Blocks.OVEN_FUMEFUNNEL_FILTERED
                && aTile.getWorldObj().getBlockMetadata(x, y, z) == meta) {
            return Config.instance().doubleFumeFilterChance ? 0.8 : 0.3;
        }
        return 0.0;
    }

    public static int getCookTime(TileEntityWitchesOven aTile) {
        final int time = 180 - 20 * getFumeFunnels(aTile);
        return time;
    }

    public static void generateByProduct(TileEntityWitchesOven aTile, final ItemStack itemstack) {
        try {
            final double BASE_CHANCE = 0.3;
            final double funnels = getFumeFunnelsChance(aTile);
            Log.instance()
                    .debug("" + getInputSlot(aTile) + ": "
                            + getInputSlot(aTile).getItem().getUnlocalizedName());
            if (aTile.getWorldObj().rand.nextDouble() <= Math.min(0.3 + funnels, 1.0)
                    && getEmptyJarSlot(aTile) != null) {
                if (getInputSlot(aTile).getItem() == Item.getItemFromBlock(Blocks.sapling)
                        && getInputSlot(aTile).getItemDamage() != 3) {
                    switch (getInputSlot(aTile).getItemDamage()) {
                        case 0: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1));
                            break;
                        }
                        case 1: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
                            break;
                        }
                        case 2: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1));
                            break;
                        }
                    }
                } else if (getInputSlot(aTile).getItem() == Item.getItemFromBlock(Witchery.Blocks.SAPLING)) {
                    switch (getInputSlot(aTile).getItemDamage()) {
                        case 0: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(1));
                            break;
                        }
                        case 1: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemReekOfMisfortune.createStack(1));
                            break;
                        }
                        case 2: {
                            createByProduct(aTile, Witchery.Items.GENERIC.itemOdourOfPurity.createStack(1));
                            break;
                        }
                    }
                } else if (getInputSlot(aTile).getUnlocalizedName().equals("tile.bop.saplings")
                        && getInputSlot(aTile).getItemDamage() == 6) {
                    createByProduct(aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
                } else if (isForestrySapling(getInputSlot(aTile))) {
                    final NBTBase tag = getInputSlot(aTile).getTagCompound().getTag("Genome");
                    if (tag != null && tag instanceof NBTTagCompound) {
                        final NBTTagCompound compound = (NBTTagCompound) tag;
                        if (compound.hasKey("Chromosomes") && compound.getTag("Chromosomes") instanceof NBTTagList) {
                            final NBTTagList list = compound.getTagList("Chromosomes", 10);
                            if (list != null && list.tagCount() > 0) {
                                final NBTBase chromoBase = (NBTBase) list.getCompoundTagAt(0);
                                if (chromoBase != null && chromoBase instanceof NBTTagCompound) {
                                    final NBTTagCompound chromosome = (NBTTagCompound) chromoBase;
                                    if (chromosome.hasKey("UID0")) {
                                        final String treeType = chromosome.getString("UID0");
                                        if (treeType != null) {
                                            Log.instance().debug("Forestry tree: " + treeType);
                                            if (treeType.equals("forestry.treeOak")) {
                                                createByProduct(
                                                        aTile,
                                                        Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1));
                                            } else if (treeType.equals("forestry.treeSpruce")) {
                                                createByProduct(
                                                        aTile, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1));
                                            } else if (treeType.equals("forestry.treeBirch")) {
                                                createByProduct(
                                                        aTile,
                                                        Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    createByProduct(aTile, Witchery.Items.GENERIC.itemFoulFume.createStack(1));
                }
            }
        } catch (Throwable e) {
            Log.instance().warning(e, "Exception occured while generating a by product from a witches oven");
        }
    }

    public static void createByProduct(TileEntityWitchesOven aTile, final ItemStack byProduct) {
        final int BY_PRODUCT_INDEX = 3;
        if (getFumeOutputSlot(aTile) == null) {
            setFumeOutputSlot(aTile, byProduct);
            final ItemStack itemStack = getEmptyJarSlot(aTile);
            if (--itemStack.stackSize <= 0) {
                setEmptyJarSlot(aTile, null);
            }
        } else if (getFumeOutputSlot(aTile).isItemEqual(byProduct)
                && getFumeOutputSlot(aTile).stackSize + byProduct.stackSize
                        < getFumeOutputSlot(aTile).getMaxStackSize()) {
            final ItemStack itemStack2 = getFumeOutputSlot(aTile);
            itemStack2.stackSize += byProduct.stackSize;
            final ItemStack itemStack3 = getEmptyJarSlot(aTile);
            if (--itemStack3.stackSize <= 0) {
                setEmptyJarSlot(aTile, null);
            }
        }
    }

    private static boolean isForestrySapling(ItemStack aSap) {
        if (aSap != null && aSap.hasTagCompound() && aSap.getTagCompound().hasKey("Genome")) {
            return true;
        }
        return false;
    }

    public static ItemStack getVanillaEquivForForestrySapling(ItemStack aSap) {
        if (isForestrySapling(aSap)) {
            final NBTBase tag = aSap.getTagCompound().getTag("Genome");
            if (tag != null && tag instanceof NBTTagCompound) {
                final NBTTagCompound compound = (NBTTagCompound) tag;
                if (compound.hasKey("Chromosomes") && compound.getTag("Chromosomes") instanceof NBTTagList) {
                    final NBTTagList list = compound.getTagList("Chromosomes", 10);
                    if (list != null && list.tagCount() > 0) {
                        final NBTBase chromoBase = (NBTBase) list.getCompoundTagAt(0);
                        if (chromoBase != null && chromoBase instanceof NBTTagCompound) {
                            final NBTTagCompound chromosome = (NBTTagCompound) chromoBase;
                            if (chromosome.hasKey("UID0")) {
                                final String treeType = chromosome.getString("UID0");
                                if (treeType != null) {
                                    Log.instance().debug("Forestry tree: " + treeType);
                                    if (treeType.equals("forestry.treeOak")) {
                                        return new ItemStack(Blocks.sapling, 1, 0);
                                    } else if (treeType.equals("forestry.treeSpruce")) {
                                        return new ItemStack(Blocks.sapling, 1, 1);
                                    } else if (treeType.equals("forestry.treeBirch")) {
                                        return new ItemStack(Blocks.sapling, 1, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}

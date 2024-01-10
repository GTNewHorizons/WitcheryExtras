package alkalus.main.core.crafting;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.emoniph.witchery.Witchery;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.AutoMap;
import alkalus.main.core.util.Pair;
import alkalus.main.core.util.Utils;

public class OvenRecipes {

    private static final ArrayList<OvenRecipe> mRecipeMap = new ArrayList<OvenRecipe>();

    public static final ArrayList<OvenRecipe> getRecipeMap() {
        return mRecipeMap;
    }

    public static void generateDefaultOvenRecipes() {

        // Remove recipe for the old Witches Oven
        // Utils.removeAllCraftingRecipesByOutputItem(Utils.simpleMetaStack(Witchery.Blocks.OVEN_IDLE, 0, 1));
        // GameRegistry.addShapedRecipe(Utils.simpleMetaStack(WitcheryExtras.OVEN_IDLE, 0, 1), new Object[] { " s ",
        // "ibi", "isi", Character.valueOf('s'), new ItemStack(Blocks.iron_bars), Character.valueOf('b'), new
        // ItemStack(Blocks.furnace), Character.valueOf('i'), new ItemStack(Items.iron_ingot) });

        // Vanilla Saplings
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 0, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1)); // Oak - 0
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 1, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1)); // Spruce - 1
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 2, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1)); // Birch - 2
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 3, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemFoulFume.createStack(1)); // Jungle - 3
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 4, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1)); // Acacia - 4
        addRecipe(
                Utils.simpleMetaStack(Blocks.sapling, 5, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1)); // Dark Oak - 5

        // Witchery Saplings
        addRecipe(
                Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 0, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(1));
        addRecipe(
                Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 1, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemReekOfMisfortune.createStack(1));
        addRecipe(
                Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 2, 1),
                1,
                Witchery.Items.GENERIC.itemAshWood.createStack(1),
                Witchery.Items.GENERIC.itemOdourOfPurity.createStack(1));

        // Raw Materials
        addRecipe("logWood", 1, Witchery.Items.GENERIC.itemFoulFume.createStack(1));
        addRecipe("meatRaw", 1, Witchery.Items.GENERIC.itemFoulFume.createStack(1));

        // Test
        // addRecipe(Utils.simpleMetaStack(Blocks.diamond_ore, 0, 1), 1,
        // Witchery.Items.GENERIC.itemCreeperHeart.createStack(1),
        // Witchery.Items.GENERIC.itemRedstoneSoup.createStack(1)); //Dark Oak - 5
    }

    private static final ItemStack createStack(final int stackSize) {
        return new ItemStack((Item) Witchery.Items.GENERIC, stackSize, 27);
    }

    public static ItemStack getEmptyJar(int amount) {
        return createStack(amount);
    }

    public static ItemStack getWitcheryItemByID(int id, int stackSize) {
        return new ItemStack((Item) Witchery.Items.GENERIC, stackSize, id);
    }

    private OvenRecipes() {}

    /**
     * @param input1       - The Input Item.
     * @param jars         - The required amount of Jars.
     * @param jarOutput    - The type of Jar output.
     * @param outputAmount - Amount of outputs, output item is based on furnace recipe of input item.
     * @return - A new {@link OverRecipe} object.
     */
    public static OvenRecipe addRecipe(final ItemStack input1, final int jars, final ItemStack jarOutput) {
        return addRecipe(input1, "", jars, null, 0, jarOutput, jars);
    }

    /**
     * @param input1       - The Input Item.
     * @param jars         - The required amount of Jars.
     * @param customOutput - A Custom output, output size reflect Itemstack size passed in.
     * @param jarOutput    - The type of Jar output.
     * @param outputAmount - Amount of outputs, output item is based on furnace recipe of input item.
     * @return - A new {@link OverRecipe} object.
     */
    public static OvenRecipe addRecipe(final ItemStack input1, final int jars, final ItemStack customOutput,
            final ItemStack jarOutput) {
        return addRecipe(input1, "", jars, customOutput, customOutput.stackSize, jarOutput, jars);
    }

    /**
     * @param input1       - A Valid Oredict String.
     * @param jars         - The required amount of Jars.
     * @param jarOutput    - The type of Jar output.
     * @param outputAmount - Amount of outputs, output item is based on furnace recipe of input item.
     * @return - A new {@link OverRecipe} object.
     */
    public static OvenRecipe addRecipe(final String input1, final int jars, final ItemStack jarOutput) {
        return addRecipe(null, input1, jars, null, 0, jarOutput, jars);
    }

    /**
     *
     * @param input1  - The Input Item.
     * @param jars    - The required amount of Jars.
     * @param output0 - A custom ItemStack output.
     * @param amt0    - The amount of custom outputs.
     * @param output1 - The type of Jar output.
     * @param amt1    - The amount of Jars output
     * @return - A new {@link OverRecipe} object.
     */
    public static OvenRecipe addRecipe(final ItemStack input1, final int jars, final ItemStack output0, int amt0,
            final ItemStack output1, final int amt1) {
        return addRecipe(input1, "", jars, output0, amt0, output1, amt1);
    }

    /**
     *
     * @param input1         - The Input Item.
     * @param inputString1   - A Valid Oredict String.
     * @param jars           - The required amount of Jars.
     * @param customOutput   - A custom ItemStack output.
     * @param amt1           - The amount of custom outputs.
     * @param outputJarStack - The type of Jar output.
     * @param amt2           - The amount of Jars output
     * @return - A new {@link OverRecipe} object.
     */
    public static OvenRecipe addRecipe(final ItemStack input1, final String inputString1, final int jars,
            final ItemStack customOutput, final int amt1, final ItemStack outputJarStack, final int amt2) {

        // Build Inputs
        ItemStack mInputStack = null;
        String mInputString = null;
        int mJars = 0;
        if (input1 != null) {
            mInputStack = input1;
        } else if (inputString1 != null && !inputString1.equals("")) {
            mInputString = inputString1;
        } else {
            WitcheryExtras.log(0, "Failed adding a Oven recipe for: INVALID.");
            return null;
        }

        WitcheryExtras.log(
                0,
                "Trying to add an Oven Recipe for "
                        + (mInputStack != null ? mInputStack.getDisplayName() : mInputString));

        mJars = jars;
        // Build Outputs
        ItemStack mOutputStack = null;
        int mOutputAmount = 0;
        ItemStack mJarOutputStack = null;
        int mJarOutputAmount = 0;
        boolean y[] = new boolean[2];

        // Furnace Output
        if (customOutput != null && amt1 > 0) {
            mOutputStack = customOutput;
            mOutputAmount = amt1;
            y[0] = true;
        } else {
            ItemStack[] w = Utils.getAllItemsFromOreDictEntry(inputString1);
            ItemStack s = null;
            if (w != null && w.length > 0) {
                for (int i = 0; i < w.length; i++) {
                    ItemStack gh = FurnaceRecipes.smelting().getSmeltingResult(w[i]);
                    if (gh != null) {
                        gh = gh.copy();
                        if (gh.stackSize > 64 || gh.stackSize <= 0) {
                            gh.stackSize = 1;
                        }
                        s = Utils.getSimpleStack(gh.copy(), gh.stackSize);
                        break;
                    }
                }
            } else {
                WitcheryExtras.log(0, "Found no entries in OreDict for " + inputString1);
            }
            if (s != null) {
                mOutputStack = s;
                mOutputAmount = s.stackSize;
                y[0] = true;
            } else {
                WitcheryExtras.log(0, "Set Y0 to False");
                y[0] = false;
            }
        }
        // Jar Output
        if (outputJarStack != null && (amt2 > 0 && amt2 == jars)) {
            mJarOutputStack = outputJarStack;
            mJarOutputAmount = amt2;
            y[1] = true;
        } else {
            y[1] = false;
            WitcheryExtras.log(0, "Set Y1 to False");
        }
        if (y[0] && y[1]) {
            OvenRecipe recipe;
            if (mInputStack != null) {
                recipe = new OvenRecipe(
                        mInputStack.copy(),
                        mJars,
                        mOutputStack.copy(),
                        mOutputAmount,
                        mJarOutputStack.copy(),
                        mJarOutputAmount);
            } else {
                recipe = new OvenRecipe(
                        mInputString,
                        mJars,
                        mOutputStack.copy(),
                        mOutputAmount,
                        mJarOutputStack.copy(),
                        mJarOutputAmount);
            }
            mRecipeMap.add(recipe);
            WitcheryExtras.log(0, "Added an Oven Recipe" + recipe.getDescription());
            return recipe;
        } else {
            WitcheryExtras.log(
                    0,
                    "Failed when trying to add an Oven Recipe for "
                            + (mInputStack != null ? mInputStack.getDisplayName() : mInputString));
            return null;
        }
    }

    /**
     * Returns an {@link OverRecipe} based on input item and an amount of jars.
     * 
     * @param input1 - An ItemStack
     * @param jars   - Amount of Jars
     * @return - The Closest Matching {@link OverRecipe}.
     */
    public static OvenRecipe getOvenResult(final ItemStack input1, final int jars) {
        for (final OvenRecipe recipe : mRecipeMap) {
            if (recipe.isInputMatch(input1, jars)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Returns a {@link OverRecipe} based on an output item.
     * 
     * @param result - The output item.
     * @return - The first Matching {@link OverRecipe}.
     */
    public static OvenRecipe findRecipeFor(final ItemStack result) {
        for (final OvenRecipe recipe : mRecipeMap) {
            if (recipe.resultsIn(result)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Returns a {@link OverRecipe} based on an input item. This may not always return the same recipe.
     * 
     * @param input1 - An ItemStack
     * @return - The first Matching {@link OverRecipe}.
     */
    public static OvenRecipe findRecipeUsing(final ItemStack ingredient) {
        for (final OvenRecipe recipe : mRecipeMap) {
            if (recipe.uses(ingredient)) {
                return recipe;
            }
        }
        return null;
    }

    public static class OvenRecipe {

        public final String validOreDictInput;
        public final ItemStack inputs;
        public final int jars;
        public final ItemStack output;
        public final ItemStack outputJar;
        public final int outputAmount1;
        public final int outputAmountJar;
        public final AutoMap<Pair<ItemStack, Integer>> outputs = new AutoMap<Pair<ItemStack, Integer>>();

        /**
         *
         * @param input1  - The {@link ItemStack} to be burned in the oven.
         * @param jars    - An {@link int} for the amount of Jars required.
         * @param output1 - The {@link ItemStack} which is a result of using the Oven.
         * @param amount1 - An {@link int} for the amount of {@link output1} given.
         * @param output2 - The {@link ItemStack} which is a filled Jar.
         * @param amount2 - An {@link int} for the amount of {@link output2} given.
         */
        private OvenRecipe(final String oreDictSub, final int jars, final ItemStack output1, final int amount1,
                final ItemStack output2, final int amount2) {
            this(null, oreDictSub, jars, output1, amount1, output2, amount2);
        }

        /**
         *
         * @param input1  - The {@link ItemStack} to be burned in the oven.
         * @param jars    - An {@link int} for the amount of Jars required.
         * @param output1 - The {@link ItemStack} which is a result of using the Oven.
         * @param amount1 - An {@link int} for the amount of {@link output1} given.
         * @param output2 - The {@link ItemStack} which is a filled Jar.
         * @param amount2 - An {@link int} for the amount of {@link output2} given.
         */
        private OvenRecipe(final ItemStack input1, final int jars, final ItemStack output1, final int amount1,
                final ItemStack output2, final int amount2) {
            this(input1, "", jars, output1, amount1, output2, amount2);
        }

        /**
         *
         * @param input1  - The {@link ItemStack} to be burned in the oven.
         * @param jars    - An {@link int} for the amount of Jars required.
         * @param output1 - The {@link ItemStack} which is a result of using the Oven.
         * @param amount1 - An {@link int} for the amount of {@link output1} given.
         * @param output2 - The {@link ItemStack} which is a filled Jar.
         * @param amount2 - An {@link int} for the amount of {@link output2} given.
         */
        private OvenRecipe(final ItemStack input1, String oreDict, final int jars, final ItemStack output1,
                final int amount1, final ItemStack output2, final int amount2) {
            this.inputs = input1;
            this.validOreDictInput = oreDict;
            this.jars = jars;
            this.output = output1;
            this.outputJar = output2;
            this.outputAmount1 = amount1;
            this.outputAmountJar = amount2;
            Pair<ItemStack, Integer> a1 = new Pair<ItemStack, Integer>(output1, amount1);
            Pair<ItemStack, Integer> a2 = new Pair<ItemStack, Integer>(output1, amount1);
            this.outputs.put(a1);
            this.outputs.put(a2);
        }

        private boolean isInputMatch(final ItemStack intput1, int amt1) {
            return (this.jars == 0 || ((isMatch(intput1, this.inputs) && amt1 >= this.jars))
                    || (!this.validOreDictInput.equals("")
                            && Utils.hasValidOreDictTag(this.validOreDictInput, intput1)));
        }

        @SuppressWarnings("unused")
        private boolean isMatch(final ItemStack output1, int amt1, final ItemStack output2, int amt2) {
            return (this.jars == 0 || ((amt1 == this.outputAmount1 && amt2 == this.outputAmountJar)
                    && (OvenRecipe.isMatch(output1, this.output) && OvenRecipe.isMatch(output2, outputJar))));
        }

        public static boolean isMatch(final ItemStack a, final ItemStack b) {
            return (a == null && b == null) || (a != null && b != null
                    && a.getItem() == b.getItem()
                    && (!a.getHasSubtypes() || a.getItemDamage() == b.getItemDamage()));
        }

        public static boolean isMatch(final ItemStack a, final ItemStack b, String oreName) {
            return ((Utils.hasValidOreDictTag(oreName, a) && Utils.hasValidOreDictTag(oreName, b))
                    || a == null && b == null)
                    || (a != null && b != null
                            && a.getItem() == b.getItem()
                            && (!a.getHasSubtypes() || a.getItemDamage() == b.getItemDamage()));
        }

        public int getJars() {
            return this.jars;
        }

        public ItemStack[] getOutputs() {
            ItemStack x[] = new ItemStack[2];
            Pair<ItemStack, Integer> a1 = outputs.get(0);
            Pair<ItemStack, Integer> a2 = outputs.get(1);
            x[0] = Utils.getSimpleStack(a1.getKey(), a1.getValue());
            x[1] = Utils.getSimpleStack(a2.getKey(), a2.getValue());
            return x;
        }

        public String getDescription() {
            String[] y = new String[] { "descriptor",
                    ("Input: " + (inputs != null ? this.inputs.getDisplayName() : "No Item")
                            + " x"
                            + (inputs != null ? inputs.stackSize : 0)),
                    ("Jars Required: " + this.jars),
                    ("Output: " + (output != null ? this.output.getDisplayName() : "No Item")
                            + " x"
                            + (output != null ? output.stackSize : 0)),
                    ("Jar Output: " + this.outputJar.getDisplayName() + " x" + jars),
                    ("OreDict: " + this.validOreDictInput) };
            String z = "";
            for (String x : y) {
                WitcheryExtras.log(0, x);
            }
            for (String x : y) {
                z += "." + x;
            }
            return z;
        }

        public boolean resultsIn(final ItemStack result) {
            for (final ItemStack stack : getOutputs()) {
                if (stack != null && stack.isItemEqual(result)) {
                    return true;
                }
            }
            return false;
        }

        public boolean uses(final ItemStack ingredient) {
            if (this.inputs != null && this.inputs.isItemEqual(ingredient)) {
                return true;
            }
            return Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(ingredient) && this.jars > 0;
        }

        public boolean isValid() {
            return inputs != null && output != null;
        }
    }
}

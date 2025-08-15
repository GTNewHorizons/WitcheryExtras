package alkalus.main.api;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.emoniph.witchery.WitcheryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;
import com.emoniph.witchery.crafting.SpinningRecipes.SpinningRecipe;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.ritual.Circle;
import com.emoniph.witchery.ritual.Rite;
import com.emoniph.witchery.ritual.RitualTraits;
import com.emoniph.witchery.ritual.Sacrifice;

import alkalus.main.api.plugin.base.BasePluginWitchery;
import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;
import alkalus.main.core.types.Witchery_CreaturePower;
import alkalus.main.core.types.Witchery_Distillery;
import alkalus.main.core.types.Witchery_Infusion;
import alkalus.main.core.types.Witchery_Kettle;
import alkalus.main.core.types.Witchery_Oven;
import alkalus.main.core.types.Witchery_Predictions;
import alkalus.main.core.types.Witchery_Rite;
import alkalus.main.core.types.Witchery_SpinningWheel;
import alkalus.main.core.util.WitcheryRecipeHandlerInternal;
import scala.collection.mutable.HashTable;

/**
 *
 * This class brings together the multiple segments spread through the core of the mod. Should make modification of
 * Witchery only rely on this class for 95% of changes/additions.
 *
 * This is the single public class to be used externally by other mods, which simply binds together the various other
 * parts into one simple interface.
 *
 * @author Alkalus
 */
public class RecipeManager {

    /*
     * Convenience classes for all the different recipe types.
     */

    /**
     *
     * Use this class to queue {@link Runnable} objects for loading at the end of each load phase.
     * {@link WitcheryExtras} will run all {@link Runnable} objects queued in the order which they are cached. It is
     * recommended to have a sub-mod that loads {@code 'before:WitcheryExtras'}, however all three load phases can have
     * plugins added prior to this mod starting up.
     * 
     * @author Alkalus
     *
     */
    public static class PluginManager {

        /**
         * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
         *
         * @param basePluginWitchery - {@link BasePluginWitchery} object which you want to load after
         *                           {@link WitcheryExtras} {@code PreInit()} phase.
         * @return - {@link boolean} which always returns true, to signify that the plugin has been queued.
         */
        public static boolean loadPluginForPreInit(BasePluginWitchery basePluginWitchery) {
            WitcheryExtras.addEventPreInit(basePluginWitchery);
            return true;
        }

        /**
         * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
         * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code Init()} phase.
         *
         * @param plugin - {@link BasePluginWitchery} object which you want to load after {@link WitcheryExtras}
         *               {@code Init()} phase.
         * @return - {@link boolean} which always returns true, to signify that the plugin has been queued.
         */
        public static boolean loadPluginForInit(BasePluginWitchery plugin) {
            WitcheryExtras.addEventInit(plugin);
            return true;
        }

        /**
         * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
         * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code Init()} phase.
         * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code PostInit()} phase.
         *
         * @param plugin - {@link BasePluginWitchery} object which you want to load after {@link WitcheryExtras}
         *               {@code PostInit()} phase.
         * @return - {@link boolean} which always returns true, to signify that the plugin has been queued.
         */
        public static boolean loadPluginForPostInit(BasePluginWitchery plugin) {
            WitcheryExtras.addEventPostInit(plugin);
            return true;
        }
    }

    public static class WitchesOven {

        /**
         * @param input1         - The Input Item.
         * @param inputString1   - A Valid Oredict String.
         * @param jars           - The required amount of Jars.
         * @param customOutput   - A custom ItemStack output.
         * @param amt1           - The amount of custom outputs.
         * @param outputJarStack - The type of Jar output.
         * @param amt2           - The amount of Jars output
         * @return - A new {@link OverRecipe} object.
         */
        public static synchronized boolean addRecipe(final ItemStack input1, final String inputString1, final int jars,
                final ItemStack customOutput, final int amt1, final ItemStack outputJarStack, final int amt2) {
            return WitcheryRecipeHandlerInternal
                    .addOvenRecipe(input1, inputString1, jars, customOutput, amt1, outputJarStack, amt2);
        }

        /**
         * This Function will remove the {@link OvenRecipe} input.
         * 
         * @param mRecipe - An {@link OvenRecipe} for a recipe you wish to remove.
         * @return - {@link boolean} representing whether or not the recipe was removed.
         */
        public static synchronized boolean removeRecipe(OvenRecipe mRecipe) {
            return WitcheryRecipeHandlerInternal.removeOvenRecipe(mRecipe);
        }

        /**
         * Finds a {@link OvenRecipe} matching a broad range of inputs.
         * 
         * @param input1 - An {@link ItemStack}, input 1.
         * @param jars   - An {@link ItemStack}, amount of Jars used.
         * @return - An {@link OvenRecipe} representing the closest matching recipe.
         */
        public static synchronized OvenRecipe getOvenResult(ItemStack input1, int jars) {
            return Witchery_Oven.getOvenResult(input1, jars);
        }

        /**
         * This Function will find the first {@link OvenRecipe} it finds that matches the specified output.
         * 
         * @param outputs - An {@link ItemStack} of the Output, for a recipe you wish to find.
         * @return - An {@link OvenRecipe} representing the closest matching recipe.
         */
        public static synchronized OvenRecipe findRecipeForOutput(ItemStack result) {
            return Witchery_Oven.findRecipeFor(result);
        }

        /**
         * This Function will find the first {@link OvenRecipe} it finds that matches the specified ingredient.
         * 
         * @param outputs - An {@link ItemStack} of the ingredient, for a recipe you wish to find.
         * @return - A {@link OvenRecipe} representing the closest matching recipe.
         */
        public static synchronized OvenRecipe findRecipeUsingIngredient(ItemStack ingredient) {
            return Witchery_Oven.findRecipeUsing(ingredient);
        }
    }

    public static class SpinningWheel {

        /**
         *
         * @param aInputFibre - The Item to be spun.
         * @param aOutput     - The item produced.
         * @param aModifiers  - Modifiers to change the recipe.
         * @return - {@link boolean} representing whether or not the recipe was added.
         */
        public static boolean addRecipe(ItemStack aInputFibre, ItemStack aOutput, ItemStack[] aModifiers) {
            return WitcheryRecipeHandlerInternal.addSpinningWheelRecipe(aOutput, aInputFibre, aModifiers);
        }

        /**
         * This Function will remove the closest matching recipe.
         * 
         * @param result    - An {@link ItemStack}, The result of the spinning recipe.
         * @param fibre     - An {@link ItemStack}, The 'fibre' used to produce the result..
         * @param modifiers - An {@link ItemStack}, The items used to modify this recipe (Can be null).
         * @return - {@link boolean} representing whether or not the recipe was removed.
         */
        public static boolean removeRecipe(ItemStack result, ItemStack fibre, ItemStack... modifiers) {
            return WitcheryRecipeHandlerInternal.removeSpinningWheelRecipe(result, fibre, modifiers);
        }

        /**
         * This Function will remove the {@link SpinningRecipe} input.
         * 
         * @param outputs - An {@link SpinningRecipe} for a recipe you wish to remove.
         * @return - {@link boolean} representing whether or not the recipe was removed.
         */
        public static synchronized boolean removeRecipe(SpinningRecipe mRecipe) {
            return WitcheryRecipeHandlerInternal.removeSpinningRecipe(mRecipe);
        }

        /**
         * This Function will find the first {@link SpinningRecipe} it finds that matches the specified output.
         * 
         * @param fibre     - The Input Used
         * @param modifiers - Modifiers used (Can be null)
         * @return - An {@link SpinningRecipe} representing the closest matching recipe.
         */
        public static synchronized SpinningRecipe getRecipe(ItemStack fibre, ItemStack[] modifiers) {
            return Witchery_SpinningWheel.getRecipe(fibre, modifiers);
        }

        /**
         * This Function will find the first {@link SpinningRecipe} it finds that matches the specified output.
         * 
         * @param outputs - An {@link ItemStack} of the Output, for a recipe you wish to find.
         * @return - An {@link SpinningRecipe} representing the closest matching recipe.
         */
        public static synchronized SpinningRecipe findRecipeFor(ItemStack result) {
            return Witchery_SpinningWheel.findRecipeFor(result);
        }

        /**
         * This Function will find the first {@link SpinningRecipe} it finds that matches the specified ingredient.
         * 
         * @param outputs - An {@link ItemStack} of the ingredient, for a recipe you wish to find. May be the fibre or a
         *                modifier.
         * @return - A {@link SpinningRecipe} representing the closest matching recipe.
         */
        public static synchronized SpinningRecipe findRecipeUsing(ItemStack ingredient) {
            return Witchery_SpinningWheel.findRecipeUsing(ingredient);
        }

        /**
         * This Function will find the first {@link SpinningRecipe} it finds that matches the specified input.
         * 
         * @param outputs - An {@link ItemStack} of the fibre used, for a recipe you wish to find.
         * @return - A {@link SpinningRecipe} representing the closest matching recipe.
         */
        public static synchronized SpinningRecipe findRecipeUsingFibre(ItemStack ingredient) {
            return Witchery_SpinningWheel.findRecipeUsingFibre(ingredient);
        }
    }

    public static class Distillery {

        /**
         * @param input1  - First Input Item
         * @param input2  - Secondary Input Item
         * @param jars    - An {@link int} that represents the required amount of empty jars.
         * @param output1 - First Output Item
         * @param output2 - Second Output Item
         * @param output3 - Third Output Item
         * @param output4 - Fourth Output Item
         * @return - {@link boolean} representing whether or not the recipe was added.
         */
        public static synchronized boolean addRecipe(ItemStack input1, ItemStack input2, int jars, ItemStack output1,
                ItemStack output2, ItemStack output3, ItemStack output4) {
            return WitcheryRecipeHandlerInternal
                    .addDistilleryRecipe(input1, input2, jars, output1, output2, output3, output4);
        }

        /**
         * This Function will remove the {@link KettleRecipe} input.
         * 
         * @param outputs - An {@link DistilleryRecipe} for a recipe you wish to remove.
         * @return - {@link boolean} representing whether or not the recipe was removed.
         */
        public static synchronized boolean removeRecipe(DistilleryRecipe mRecipe) {
            return WitcheryRecipeHandlerInternal.removeDistilleryRecipe(mRecipe);
        }

        /**
         * Finds a {@link DistilleryRecipe} matching a broad range of inputs.
         * 
         * @param input1 - An {@link ItemStack}, input 1.
         * @param input2 - An {@link ItemStack}, input 2.
         * @param jars   - An {@link ItemStack}, input 3. This input slot is for Jars.
         * @return - An {@link DistilleryRecipe} representing the closest matching recipe.
         */
        public static synchronized DistilleryRecipe getDistillingResult(ItemStack input1, ItemStack input2,
                ItemStack jars) {
            return Witchery_Distillery.getDistillingResult(input1, input2, jars);
        }

        /**
         * This Function will find the first {@link DistilleryRecipe} it finds that matches the specified output.
         * 
         * @param outputs - An {@link ItemStack} of the Output, for a recipe you wish to find.
         * @return - An {@link DistilleryRecipe} representing the closest matching recipe.
         */
        public static synchronized DistilleryRecipe findRecipeForOutput(ItemStack result) {
            return Witchery_Distillery.findRecipeFor(result);
        }

        /**
         * This Function will find the first {@link DistilleryRecipe} it finds that matches the specified ingredient.
         * 
         * @param outputs - An {@link ItemStack} of the ingredient, for a recipe you wish to find.
         * @return - A {@link DistilleryRecipe} representing the closest matching recipe.
         */
        public static synchronized DistilleryRecipe findRecipeUsingIngredient(ItemStack ingredient) {
            return Witchery_Distillery.findRecipeUsing(ingredient);
        }
    }

    public static class Kettle {

        /**
         * @param output        - {@link ItemStack} The Output Stack.
         * @param hatBonus      - {@link int} The bonus granted from wearing {@value Witchery} Hats.
         * @param familiarType  - {@link int} The ID of the familiar required, can be 0.
         * @param powerRequired - {@link float} The amount of power required from your Altar, can be 0.
         * @param color         - {@link int} The colour of the brew when the {@link KettleRecipe} is processing.
         * @param dimension     - {@link int} The dimension ID required. Can't be left as 0 to allow all Dims.
         * @param inBook        - {@link boolean} Does this recipe show up in the Kettle recipe book?
         * @param inputs        - {@link ItemStack} {@code ||} {@link ItemStack}[] of Input Items.
         * @return - {@link boolean} representing whether or not the recipe was added.
         */
        public static synchronized boolean addRecipe(ItemStack output, int hatBonus, int familiarType,
                float powerRequired, int color, int dimension, boolean inBook, ItemStack... inputs) {
            return WitcheryRecipeHandlerInternal
                    .addKettleRecipe(output, hatBonus, familiarType, powerRequired, color, dimension, inBook, inputs);
        }

        /**
         * This Function will remove the first {@link KettleRecipe} it finds that matches the specified output.
         * 
         * @param mOutput - An {@link ItemStack} of the Output, for a recipe you wish to remove.
         * @return - {@link boolean} representing whether or not the recipe was removed.
         */
        public static synchronized boolean removeRecipe(ItemStack mOutput) {
            return WitcheryRecipeHandlerInternal.removeKettleRecipe(mOutput);
        }

        /**
         * @param inputs - An {@link ItemStack} array which contains some if not all of the inputs for the recipe you're
         *               trying to find.
         * @param output - An {@link ItemStack} of the output item for the recipe you are trying to find.
         * @return - The {@link KettleRecipe} closest matching your search parameters.
         */
        public static KettleRecipe findRecipeWithSomeInputsAndAnOutput(ItemStack[] inputs, ItemStack output) {
            return Witchery_Kettle.findRecipeWithSomeInputsAndAnOutput(inputs, output);
        }

        /**
         * @param result - An {@link ItemStack}, used to find a {@link HashMap} full of all {@link KettleRecipe}s with
         *               this output.
         * @return - A {@link List} Which holds all matching recipes containing {@code 'result'} as an output.
         */
        public static List<KettleRecipe> findRecipesFor(final ItemStack result) {
            return Witchery_Kettle.findRecipesFor(result);
        }
    }

    public static class CreaturePowers {

        /**
         * @param power - A new {@link CreaturePower} to be added to the registry.
         * @return - A {@link boolean} which reflects registry addition.
         */
        public static synchronized boolean add(CreaturePower power) {
            return WitcheryRecipeHandlerInternal.addNewCreaturePower(power);
        }

        /**
         * This Function will remove the specified {@link CreaturePower}.
         * 
         * @param power - An {@link CreaturePower} you wish to remove.
         * @return - {@link boolean} representing whether or not the {@link CreaturePower} was removed.
         */
        public static synchronized boolean remove(CreaturePower power) {
            return WitcheryRecipeHandlerInternal.removeCreaturePower(power);
        }

        /**
         * @param entity - The {@link EntityLiving} to find a {@link CreaturePower} for.
         * @return - Matching {@link CreaturePower}.
         */
        public static synchronized CreaturePower getCreaturePower(EntityLiving entity) {
            return Witchery_CreaturePower.getCreaturePower(entity);
        }

        /**
         * @param entity - The Entity ID to use to find a {@link CreaturePower}.
         * @return - Matching {@link CreaturePower}.
         */
        public static synchronized CreaturePower getCreaturePower(int entityID) {
            return Witchery_CreaturePower.getCreaturePower(entityID);
        }
    }

    public static class RitesAndRituals {

        /**
         * @param ritualID         - Byte Value between 0-127.
         * @param bookIndex        - Unsure, possibly look at other recipes in {@link WitcheryRecipes} for better
         *                         examples. Otherwise, just use ID+100 if you are unsure and want recipes to work as
         *                         expected.
         * @param rite             - The {@link Rite} that you wish to add to the registry.
         * @param initialSacrifice - The {@link Sacrifice} that is required.
         * @param traits           - An {@link EnumSet} filled with {@link RitualTraits}.
         * @param circles          - The {@link Circle}s that are required, can be an array or several single objects.
         * @return - A {@link boolean} reflecting the addition of the new Rite to the Registry.
         */
        public static synchronized boolean add(int ritualID, int bookIndex, Rite rite, Sacrifice initialSacrifice,
                EnumSet<RitualTraits> traits, Circle... circles) {
            return WitcheryRecipeHandlerInternal
                    .addNewRiteToRiteRegistry(ritualID, bookIndex, rite, initialSacrifice, traits, circles);
        }

        /**
         * This Function will remove the {@link Rite} specified by it's ID..
         * 
         * @param ritualID - A {@link int} representation of the Rite you wish to remove.
         * @return - {@link boolean} representing whether or not the {@link Rite} was removed.
         */
        public static synchronized boolean remove(int ritualID) {
            return WitcheryRecipeHandlerInternal.removeRiteFromRiteRegistry(ritualID);
        }

        /**
         * Finds the last/highest used ID used by any {@link Rite}.
         * 
         * @return - {@link int} containing the last/highest ID.
         */
        public static int getLastUsedRitualID() {
            return Witchery_Rite.getLastUsedRiteID();
        }
    }

    public static class Predictions {

        /**
         * @param prediction - A new {@link Prediction} to be added to the registry.
         * @return - A {@link boolean} which reflects registry addition.
         */
        public static synchronized boolean add(Prediction prediction) {
            return WitcheryRecipeHandlerInternal.addNewPrediction(prediction);
        }

        /**
         * This Function will remove the specified {@link Prediction}.
         * 
         * @param power - A {@link Prediction} you wish to remove.
         * @return - {@link boolean} representing whether or not the {@link Prediction} was removed.
         */
        public static synchronized boolean remove(Prediction prediction) {
            return WitcheryRecipeHandlerInternal.removePrediction(prediction);
        }

        /**
         * @return - {@link HashTable} containing all currently stored {@link Prediction}s.
         */
        public static synchronized Hashtable<Integer, Prediction> getPredictions() {
            return Witchery_Predictions.getPredictions();
        }

        /**
         * @param aID - The ID the desired prediction is registered to.
         * @return - {@link Prediction} bound to ID.
         */
        public static synchronized Prediction getPrediction(int aID) {
            return getPredictions().get(aID);
        }
    }

    public static class Infusions {

        /**
         * Allows adding a new Infusion.
         * 
         * @param infusion - The {@link Infusion} object you wish to add to the registry.
         * @return - A {@link boolean} that reflects if the new object was added.
         */
        public static synchronized boolean add(Infusion infusion) {
            return WitcheryRecipeHandlerInternal.addNewInfusion(infusion);
        }

        /**
         * This Function will remove the specified {@link Infusion}.
         * 
         * @param power - A {@link Infusion} you wish to remove.
         * @return - {@link boolean} representing whether or not the {@link Infusion} was removed.
         */
        public static synchronized boolean remove(Infusion infusion) {
            return WitcheryRecipeHandlerInternal.removeInfusion(infusion);
        }

        /**
         * @param i - {@link int} - The {@link Infusion}'s ID.
         * @return - The {@link Infusion} found for the ID provided, possibly {@link null}.
         */
        public static Infusion getInfusion(int i) {
            return Witchery_Infusion.getInfusion(i);
        }

        /**
         * @param i - {@link EntityPlayer} - The player you wish to get the {@link Infusion} from.
         * @return - The {@link Infusion} found for the player provided, possibly {@link null}.
         */
        public static Infusion getInfusionOnPlayer(EntityPlayer player) {
            return Witchery_Infusion.getInfusionOnPlayer(player);
        }

        /**
         * Finds the last/highest used ID used by any infusion.
         * 
         * @return - {@link int} containing the last/highest ID.
         */
        public static int getLastUsedInfusionID() {
            return Witchery_Infusion.getLastUsedInfusionID();
        }
    }
}

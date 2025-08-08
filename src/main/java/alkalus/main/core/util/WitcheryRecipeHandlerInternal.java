package alkalus.main.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;
import com.emoniph.witchery.crafting.KettleRecipes;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;
import com.emoniph.witchery.crafting.SpinningRecipes;
import com.emoniph.witchery.crafting.SpinningRecipes.SpinningRecipe;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.predictions.PredictionManager;
import com.emoniph.witchery.ritual.Circle;
import com.emoniph.witchery.ritual.Rite;
import com.emoniph.witchery.ritual.RiteRegistry;
import com.emoniph.witchery.ritual.RiteRegistry.Ritual;
import com.emoniph.witchery.ritual.RitualTraits;
import com.emoniph.witchery.ritual.Sacrifice;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;

public class WitcheryRecipeHandlerInternal {

    /*
     * Oven Recipes
     */
    public static synchronized boolean addOvenRecipe(final ItemStack input1, final String inputString1, final int jars,
        final ItemStack customOutput, final int amt1, final ItemStack outputJarStack, final int amt2) {
        return OvenRecipes.addRecipe(input1, inputString1, jars, customOutput, amt1, outputJarStack, amt2) != null;
    }

    public static synchronized boolean removeOvenRecipe(OvenRecipe mRecipe) {
        if (mRecipe == null) {
            WitcheryExtras.log(
                2,
                "Null Oven Recipe parsed into removeOvenRecipe(). Please check all OvenRecipes are valid before calling this function.");
            return false;
        }
        boolean aDidRemove = false;
        ArrayList<OvenRecipe> aRecipes = OvenRecipes.getRecipeMap();
        if (aRecipes.contains(mRecipe)) {
            if (aRecipes.remove(mRecipe)) {
                aDidRemove = true;
            }
        }
        if (aDidRemove) {
            WitcheryExtras.log(0, "Removed Oven recipe: " + mRecipe.getDescription());
        } else {
            WitcheryExtras.log(0, "Failed to remove Oven recipe: " + mRecipe.getDescription());
        }
        return aDidRemove;
    }

    /*
     * Distillery Recipes
     */

    public static synchronized boolean addDistilleryRecipe(ItemStack input1, ItemStack input2, int jars,
        ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
        return DistilleryRecipes.instance()
            .addRecipe(input1, input2, jars, output1, output2, output3, output4) != null;
    }

    public static synchronized boolean removeDistilleryRecipe(DistilleryRecipe mRecipe) {
        if (mRecipe == null) {
            WitcheryExtras.log(
                2,
                "Null Distillery Recipe parsed into removeDistilleryRecipe(). Please check all DistilleryRecipes are valid before calling this function.");
            return false;
        }
        boolean aDidRemove = false;
        if (DistilleryRecipes.instance().recipes.contains(mRecipe)) {
            if (DistilleryRecipes.instance().recipes.remove(mRecipe)) {
                aDidRemove = true;
            }
        }
        if (aDidRemove) {
            WitcheryExtras.log(0, "Removed Distillery recipe: " + mRecipe.getDescription());
        } else {
            WitcheryExtras.log(0, "Failed to remove Distillery recipe: " + mRecipe.getDescription());
        }
        return aDidRemove;
    }

    /*
     * Kettle Recipes
     */

    public static synchronized boolean addKettleRecipe(ItemStack output, int hatBonus, int familiarType,
        float powerRequired, int color, int dimension, boolean inBook, ItemStack... inputs) {
        return KettleRecipes.instance()
            .addRecipe(output, hatBonus, familiarType, powerRequired, color, dimension, inBook, inputs) != null;
    }

    public static synchronized boolean removeKettleRecipe(ItemStack mOutput) {
        KettleRecipe toRemove = KettleRecipes.instance()
            .findRecipeFor(mOutput);
        if (mOutput == null || toRemove == null) {
            WitcheryExtras.log(
                2,
                "Null Kettle Recipe parsed into removeKettleRecipe(). Please check all ItemStacks are valid before calling this function.");
            return false;
        }
        boolean aDidRemove = false;
        if (KettleRecipes.instance().recipes.contains(toRemove)) {
            if (KettleRecipes.instance().recipes.remove(toRemove)) {
                aDidRemove = true;
            }
        }
        if (aDidRemove) {
            WitcheryExtras.log(
                0,
                "Removed Kettle recipe for output: " + toRemove.output.getDisplayName()
                    + ". "
                    + toRemove.getDescription());
        } else {
            WitcheryExtras.log(0, "Failed to remove Kettle recipe for " + mOutput.getDisplayName());
        }
        return aDidRemove;
    }

    /*
     * Creature Powers
     */

    public static synchronized boolean addNewCreaturePower(CreaturePower power) {
        try {
            CreaturePower.Registry.instance()
                .add(power);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static synchronized boolean removeCreaturePower(CreaturePower power) {
        Field aRegistry = ReflectionUtils.getField(CreaturePower.Registry.class, "registry");
        if (power == null || aRegistry == null
            || ReflectionUtils.getFieldValue(aRegistry, CreaturePower.Registry.instance()) != null) {
            WitcheryExtras.log(
                2,
                "Null CreaturePower parsed into removeCreaturePower(). Please check all CreaturePowers are valid before calling this function.");
            return false;
        }
        ArrayList<CreaturePower> registry = (ArrayList<CreaturePower>) ReflectionUtils
            .getFieldValue(aRegistry, CreaturePower.Registry.instance());
        int aSizeStart = registry.size();
        for (CreaturePower aCreaturePower : registry) {
            if (aCreaturePower != null && aCreaturePower.getCreaturePowerID() == power.getCreaturePowerID()) {
                registry.remove(aCreaturePower);
                WitcheryExtras.log(0, "Removed CreaturePower: " + aCreaturePower.getCreaturePowerID() + ".");
                break;
            }
        }
        if (registry.size() >= aSizeStart) {
            WitcheryExtras.log(0, "Failed to remove CreaturePower: " + power.getCreaturePowerID() + ".");
        }
        return registry.size() < aSizeStart;
    }

    /*
     * Rites
     */

    public static synchronized boolean addNewRiteToRiteRegistry(int ritualID, int bookIndex, Rite rite,
        Sacrifice initialSacrifice, EnumSet<RitualTraits> traits, Circle... circles) {
        if (ritualID > Byte.MAX_VALUE) {
            WitcheryExtras.log(
                1,
                "RitualID for Rite: " + rite
                    .toString() + " has an ID greater than " + Byte.MAX_VALUE + ". Found ID: " + ritualID + ".");
            return false;
        }
        return RiteRegistry.addRecipe((byte) ritualID, bookIndex, rite, initialSacrifice, traits, circles) != null;
    }

    public static synchronized boolean removeRiteFromRiteRegistry(int ritualID) {
        if (ritualID < Byte.MIN_VALUE || ritualID > Byte.MAX_VALUE) {
            WitcheryExtras.log(
                2,
                "Failed to remove Rite, ID exceeded range of a byte. Found: " + ritualID
                    + ", Expected: "
                    + Byte.MIN_VALUE
                    + "-"
                    + Byte.MAX_VALUE
                    + ".");
            return false;
        }
        Ritual toRemove = RiteRegistry.instance()
            .getRitual((byte) ritualID);
        if (toRemove == null) {
            WitcheryExtras.log(
                2,
                "Invalid Rite ID parsed into removeRiteFromRiteRegistry(int). Please check ID is mapped before calling this function.");
            return false;
        }
        boolean aDidRemove = false;
        if (RiteRegistry.instance()
            .getRituals()
            .contains(toRemove)) {
            if (RiteRegistry.instance()
                .getRituals()
                .remove(toRemove)) {
                aDidRemove = true;
            }
        }
        if (aDidRemove) {
            WitcheryExtras.log(0, "Removed Rite: " + toRemove.getDescription());
        } else {
            WitcheryExtras.log(
                0,
                "Failed to remove Rite: " + (toRemove != null
                    ? toRemove.getLocalizedName() + " | " + toRemove.getRitualID() + " | " + toRemove.getDescription()
                    : ritualID));
        }
        return aDidRemove;
    }

    /*
     * Predictions
     */

    public static synchronized boolean addNewPrediction(Prediction prediction) {
        try {
            PredictionManager.instance()
                .addPrediction(prediction);
            WitcheryExtras
                .log(0, "Added Prediction: " + prediction.getTranslationKey() + " | " + prediction.predictionID);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static synchronized boolean removePrediction(Prediction prediction) {
        Field aRegistry = ReflectionUtils.getField(PredictionManager.class, "predictions");
        if (prediction == null) {
            WitcheryExtras.log(
                2,
                "Null Prediction parsed into removePrediction(). Please check all Predictions are valid before calling this function.");
            return false;
        }
        PredictionManager aInstance = PredictionManager.instance();
        Hashtable<Integer, Prediction> predictions = (Hashtable<Integer, Prediction>) ReflectionUtils
            .getFieldValue(aRegistry, aInstance);
        int aSizeStart = predictions.size();
        for (Prediction aPredic : predictions.values()) {
            if (aPredic != null && aPredic.predictionID == prediction.predictionID) {
                predictions.remove(prediction.predictionID);
                WitcheryExtras
                    .log(0, "Removed Prediction: " + aPredic.getTranslationKey() + " | " + aPredic.predictionID);
                break;
            }
        }
        if (predictions.size() >= aSizeStart) {
            WitcheryExtras.log(
                0,
                "Failed to remove Prediction: "
                    + (prediction != null ? prediction.getTranslationKey() + ", " + prediction.predictionID
                        : prediction.predictionID));
        }
        return predictions.size() < aSizeStart;
    }

    /*
     * Infusion Recipes
     */

    public static synchronized boolean addNewInfusion(Infusion infusion) {
        try {
            Infusion.Registry.instance()
                .add(infusion);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static synchronized boolean removeInfusion(Infusion infusion) {
        if (infusion == null) {
            WitcheryExtras.log(
                2,
                "Null Infusion parsed into removeInfusion(). Please check all Infusions are valid before calling this function.");
            return false;
        }
        Field aRegistry = ReflectionUtils.getField(CreaturePower.Registry.class, "registry");
        if (infusion == null || aRegistry == null
            || ReflectionUtils.getFieldValue(aRegistry, Infusion.Registry.instance()) != null) {
            WitcheryExtras.log(
                2,
                "Null Infusion parsed into removeInfusion(). Please check all Infusions are valid before calling this function.");
            return false;
        }
        ArrayList<Infusion> infusions = (ArrayList<Infusion>) ReflectionUtils
            .getFieldValue(aRegistry, Infusion.Registry.instance());
        int aSizeStart = infusions.size();
        for (Infusion aInfusion : infusions) {
            if (aInfusion != null && aInfusion.infusionID == infusion.infusionID) {
                infusions.remove(aInfusion);
                WitcheryExtras.log(0, "Removed Infusion: " + infusion.toString() + " | " + infusion.infusionID);
                break;
            }
        }
        if (infusions.size() >= aSizeStart) {
            WitcheryExtras.log(
                1,
                "Failed to remove Infusion: "
                    + (infusion != null ? infusion.toString() + " | " + infusion.infusionID + " | "
                        : infusion.infusionID));
        }
        return infusions.size() < aSizeStart;
    }

    /*
     * Spinning Recipes
     */

    public static synchronized boolean addSpinningWheelRecipe(ItemStack result, ItemStack fibre,
        ItemStack... modifiers) {
        if (result == null || fibre == null) {
            return false;
        }
        SpinningRecipes.instance()
            .addRecipe(result, fibre, modifiers);
        return true;
    }

    public static boolean removeSpinningWheelRecipe(ItemStack result, ItemStack fibre, ItemStack... modifiers) {
        SpinningRecipe aRecipeToRemove = null;
        for (SpinningRecipe aRecipe : SpinningRecipes.instance().recipes) {
            if (Utils.areStacksEqual(fibre, aRecipe.fibre)) {
                if (Utils.areStacksEqual(result, aRecipe.result)) {
                    if (modifiers.length > 0 && aRecipe.modifiers.length > 0
                        && modifiers.length == aRecipe.modifiers.length) {
                        aRecipeToRemove = aRecipe;
                        break;
                    }
                }
            }
        }
        if (aRecipeToRemove != null) {
            return SpinningRecipes.instance().recipes.remove(aRecipeToRemove);
        }
        return false;
    }

    public static boolean removeSpinningRecipe(SpinningRecipe mRecipe) {
        return removeSpinningWheelRecipe(mRecipe.result, mRecipe.fibre, mRecipe.modifiers);
    }
}

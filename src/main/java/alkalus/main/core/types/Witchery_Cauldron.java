package alkalus.main.core.types;

import java.util.Hashtable;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.brewing.BrewItemKey;
import com.emoniph.witchery.brewing.WitcheryBrewRegistry;
import com.emoniph.witchery.brewing.action.BrewAction;
import com.emoniph.witchery.brewing.action.BrewActionRitualRecipe;
import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;

import alkalus.main.core.util.ReflectionUtils;

public class Witchery_Cauldron {

    public static List<BrewActionRitualRecipe> getRecipes() {
        return WitcheryBrewRegistry.INSTANCE.getRecipes();
    }

    public static Hashtable<BrewItemKey, BrewAction> getIngredients() {
        return (Hashtable<BrewItemKey, BrewAction>) ReflectionUtils.getFieldValue(
                ReflectionUtils.getField(WitcheryBrewRegistry.class, "ingredients"),
                WitcheryBrewRegistry.INSTANCE);
    }

    public static BrewAction getActionForItemStack(final ItemStack stack) {
        return getIngredients().get(BrewItemKey.fromStack(stack));
    }

    private static boolean removeCauldronBrewAction(final ItemStack ingredient) {
        return removeCauldronBrewAction(getActionForItemStack(ingredient));
    }

    private static boolean removeCauldronBrewAction(final BrewAction ingredient) {
        // Bad Recipe to remove
        if (ingredient == null || ingredient.ITEM_KEY == null) {
            return false;
        }
        if (getIngredients().containsKey(ingredient.ITEM_KEY)) {
            return getIngredients().remove(ingredient.ITEM_KEY) != null;
        }
        return false;
    }

    public static synchronized BrewActionRitualRecipe findRecipeForOutput(ItemStack aOutput) {
        for (BrewActionRitualRecipe aRecipe : getRecipes()) {
            if (aRecipe != null) {}
        }

        return null;
    }

    public static synchronized DistilleryRecipe getDistillingResult(ItemStack input1, ItemStack intput2,
            ItemStack jars) {
        return DistilleryRecipes.instance().getDistillingResult(input1, intput2, jars);
    }

    public static synchronized DistilleryRecipe findRecipeFor(ItemStack result) {
        return DistilleryRecipes.instance().findRecipeFor(result);
    }

    public static synchronized DistilleryRecipe findRecipeUsing(ItemStack ingredient) {
        return DistilleryRecipes.instance().findRecipeUsing(ingredient);
    }
}

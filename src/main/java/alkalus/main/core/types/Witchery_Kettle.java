package alkalus.main.core.types;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import alkalus.main.core.util.AutoMap;

import com.emoniph.witchery.crafting.KettleRecipes;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;

public class Witchery_Kettle {

    public static AutoMap<KettleRecipe> findRecipesFor(final ItemStack result) {
        AutoMap<KettleRecipe> recipesOld = new AutoMap<KettleRecipe>();
        ArrayList<KettleRecipe> recipes = new ArrayList<KettleRecipe>();
        recipes = KettleRecipes.instance().recipes;
        for (final KettleRecipe recipe : recipes) {
            if (recipe.output.isItemEqual(result)) {
                recipesOld.put(recipe);
            }
        }
        return recipesOld;
    }

    public static KettleRecipe findRecipeWithSomeInputsAndAnOutput(ItemStack[] inputs, ItemStack output) {
        if (inputs == null || inputs.length < 1 || output == null) {
            return null;
        }
        AutoMap<KettleRecipe> recipesWithMatchingOutput = findRecipesFor(output);
        AutoMap<ItemStack> mMatchingItems = new AutoMap<ItemStack>();
        KettleRecipe result = null;
        // Cycle Recipes
        loop1: for (KettleRecipe K : recipesWithMatchingOutput) {
            // Cycle through inputs we're searching for to match
            loop2: for (ItemStack M : inputs) {
                // Cycle through the recipes inputs
                for (ItemStack I : K.inputs) {
                    if (I.isItemEqual(M)) {
                        mMatchingItems.put(M);
                        continue loop2;
                    }
                }
            }
            if (mMatchingItems.size() >= (Math.max(1, inputs.length - 1))) {
                result = K;
                break loop1;
            }
        }
        return result;
    }
}

package alkalus.main.core.types;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.crafting.KettleRecipes;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;

public class Witchery_Kettle {

    public static List<KettleRecipe> findRecipesFor(final ItemStack result) {
        List<KettleRecipe> recipesOld = new ArrayList<>();
        List<KettleRecipe> recipes = KettleRecipes.instance().recipes;
        for (final KettleRecipe recipe : recipes) {
            if (recipe.output.isItemEqual(result)) {
                recipesOld.add(recipe);
            }
        }
        return recipesOld;
    }

    public static KettleRecipe findRecipeWithSomeInputsAndAnOutput(ItemStack[] inputs, ItemStack output) {
        if (inputs == null || inputs.length < 1 || output == null) {
            return null;
        }
        List<KettleRecipe> recipesWithMatchingOutput = findRecipesFor(output);
        ArrayList<ItemStack> mMatchingItems = new ArrayList<>();
        KettleRecipe result = null;
        // Cycle Recipes
        loop1: for (KettleRecipe K : recipesWithMatchingOutput) {
            // Cycle through inputs we're searching for to match
            loop2: for (ItemStack M : inputs) {
                // Cycle through the recipes inputs
                for (ItemStack I : K.inputs) {
                    if (I.isItemEqual(M)) {
                        mMatchingItems.add(M);
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

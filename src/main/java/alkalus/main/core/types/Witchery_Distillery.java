package alkalus.main.core.types;

import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class Witchery_Distillery {

    public static synchronized DistilleryRecipe getDistillingResult(
            ItemStack input1, ItemStack intput2, ItemStack jars) {
        return DistilleryRecipes.instance().getDistillingResult(input1, intput2, jars);
    }

    public static synchronized DistilleryRecipe findRecipeFor(ItemStack result) {
        return DistilleryRecipes.instance().findRecipeFor(result);
    }

    public static synchronized List<DistilleryRecipes.DistilleryRecipe> findRecipesFor(ItemStack result) {
        List<DistilleryRecipes.DistilleryRecipe> recipes = new ArrayList<>();
        for (DistilleryRecipes.DistilleryRecipe recipe : DistilleryRecipes.instance().recipes) {
            if (recipe.resultsIn(result)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public static synchronized DistilleryRecipe findRecipeUsing(ItemStack ingredient) {
        return DistilleryRecipes.instance().findRecipeUsing(ingredient);
    }
}

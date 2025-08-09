package alkalus.main.core.types;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.crafting.SpinningRecipes;
import com.emoniph.witchery.crafting.SpinningRecipes.SpinningRecipe;

public class Witchery_SpinningWheel {

    public static synchronized SpinningRecipe getRecipe(ItemStack fibre, ItemStack[] modifiers) {
        return SpinningRecipes.instance()
            .getRecipe(fibre, modifiers);
    }

    public static synchronized SpinningRecipe findRecipeFor(ItemStack result) {
        return SpinningRecipes.instance()
            .findRecipeFor(result);
    }

    public static synchronized SpinningRecipe findRecipeUsing(ItemStack ingredient) {
        return SpinningRecipes.instance()
            .findRecipeUsing(ingredient);
    }

    public static synchronized SpinningRecipe findRecipeUsingFibre(ItemStack ingredient) {
        return SpinningRecipes.instance()
            .findRecipeUsingFibre(ingredient);
    }
}

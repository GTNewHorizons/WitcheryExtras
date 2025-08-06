package alkalus.main.core.recipe;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.recipe.fixes.GarlicRecipes;

public class CustomRecipeLoader {

    public CustomRecipeLoader() {
        run();
    }

    private void run() {
        // Fixes recipes that hardcode Witchery Garlic types.
        new GarlicRecipes();
        OvenRecipes.generateDefaultOvenRecipes();
    }
}

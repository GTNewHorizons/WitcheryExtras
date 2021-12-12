package alkalus.main.core.types;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;
import net.minecraft.item.ItemStack;

public class Witchery_Oven {

	public static synchronized OvenRecipe getOvenResult(final ItemStack input1, final int jars){
		return OvenRecipes.getOvenResult(input1, jars);
	}

	public static synchronized OvenRecipe findRecipeFor(ItemStack result){
		return OvenRecipes.findRecipeFor(result);
	}

	public static synchronized OvenRecipe findRecipeUsing(ItemStack ingredient){
		return OvenRecipes.findRecipeUsing(ingredient);
	}

}

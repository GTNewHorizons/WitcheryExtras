package alkalus.main.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.*;
import java.awt.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.*;
import com.emoniph.witchery.brewing.*;
import com.emoniph.witchery.brewing.action.*;
import net.minecraft.item.*;
import codechicken.nei.*;

import java.lang.reflect.Field;
import java.util.*;

public class NEI_Handler_Cauldron extends TemplateRecipeHandler
{
	public void loadTransferRects() {
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(92, 31, 24, 18), "witchery_brewing_plus", new Object[0]));
	}

	public Class<? extends GuiContainer> getGuiClass() {
		return (Class<? extends GuiContainer>)GuiCrafting.class;
	}

	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.witchery:cauldron.name");
	}

	public void loadCraftingRecipes(final String outputId, final Object... results) {
		if (outputId.equals("witchery_brewing_plus") && this.getClass() == NEI_Handler_Cauldron.class) {
			for (final BrewActionRitualRecipe ritual : WitcheryBrewRegistry.INSTANCE.getRecipes()) {
				for (final BrewActionRitualRecipe.Recipe recipe : ritual.getExpandedRecipes()) {
					this.arecipes.add(new CachedKettleRecipe(recipe.result, recipe.ingredients, getPowerCost(ritual)));
				}
			}
		}
		else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	public void loadCraftingRecipes(final ItemStack result) {
		for (final BrewActionRitualRecipe ritual : WitcheryBrewRegistry.INSTANCE.getRecipes()) {
			for (final BrewActionRitualRecipe.Recipe recipe : ritual.getExpandedRecipes()) {
				if (result.isItemEqual(recipe.result)) {
					this.arecipes.add(new CachedKettleRecipe(recipe.result, recipe.ingredients, getPowerCost(ritual)));
				}
			}
		}
	}

	public void loadUsageRecipes(final ItemStack ingredient) {
		for (final BrewActionRitualRecipe ritual : WitcheryBrewRegistry.INSTANCE.getRecipes()) {
			try {
				for (final BrewActionRitualRecipe.Recipe recipe : ritual.getExpandedRecipes()) {
					for (final ItemStack stack : recipe.ingredients) {
						if (stack.isItemEqual(ingredient)) {
							this.arecipes.add(new CachedKettleRecipe(recipe.result, recipe.ingredients, getPowerCost(ritual)));
						}
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public String getGuiTexture() {
		return "witchery:textures/gui/witchesCauldron.png";
	}

	public void drawExtras(final int recipeIndex) {
		int power = ((CachedKettleRecipe) this.arecipes.get(recipeIndex)).getPower();
		if (power == 0) {
			return;
		}

		String text = I18n.format("WitcheryExtras.nei.power", power);
		GuiDraw.drawString(text, 5, 54, 0x000000, false);
	}

	public String getOverlayIdentifier() {
		return "witchery_brewing_plus";
	}

	private AltarPower getPowerCost(BrewAction recipe) {
		try {
			Field powerCostField = BrewAction.class.getDeclaredField("powerCost");
			powerCostField.setAccessible(true);
			return (AltarPower) powerCostField.get(recipe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public class CachedKettleRecipe extends TemplateRecipeHandler.CachedRecipe
	{
		PositionedStack result;
		PositionedStack[] inputs;
		int power;

		public CachedKettleRecipe(final ItemStack result, final ItemStack[] recipe, final AltarPower powerCost) {
			super();
			this.inputs = new PositionedStack[6];
			this.result = new PositionedStack((Object)result, 119, 31);
			for (int i = 0; i < recipe.length; ++i) {
				if (recipe[i] != null) {
					this.inputs[i] = new PositionedStack((Object)recipe[i], i * 18 + 10, 6);
				}
				else {
					this.inputs[i] = null;
				}
			}
			if (powerCost != null) {
				this.power = powerCost.getPower();
			}
		}

		public PositionedStack getResult() {
			return this.result;
		}

		public ArrayList<PositionedStack> getIngredients() {
			final ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
			for (final PositionedStack posStack : this.inputs) {
				if (posStack != null) {
					recipestacks.add(posStack);
				}
			}
			return recipestacks;
		}

		public int getPower() {
			return this.power;
		}
	}
}

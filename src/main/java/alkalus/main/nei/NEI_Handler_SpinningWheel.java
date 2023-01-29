package alkalus.main.nei;

import java.awt.*;
import java.util.*;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import codechicken.nei.*;
import codechicken.nei.recipe.*;

import com.emoniph.witchery.blocks.*;
import com.emoniph.witchery.crafting.*;

public class NEI_Handler_SpinningWheel extends TemplateRecipeHandler {

    public Class<? extends GuiContainer> getGuiClass() {
        return BlockSpinningWheelGUI.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.witchery:spinningwheel.name");
    }

    public void loadTransferRects() {
        this.transferRects.add(
                new TemplateRecipeHandler.RecipeTransferRect(
                        new Rectangle(74, 9, 24, 18),
                        "witchery_spinning",
                        new Object[0]));
    }

    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("witchery_spinning") && this.getClass() == NEI_Handler_SpinningWheel.class) {
            for (final SpinningRecipes.SpinningRecipe recipe : SpinningRecipes.instance().recipes) {
                this.arecipes.add(new CachedSpinningRecipe(recipe.getResult(), recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(final ItemStack result) {
        final SpinningRecipes.SpinningRecipe recipe = SpinningRecipes.instance().findRecipeFor(result);
        if (recipe != null) {
            this.arecipes.add(new CachedSpinningRecipe(result, recipe));
        }
    }

    public void loadUsageRecipes(final ItemStack ingredient) {
        final SpinningRecipes.SpinningRecipe recipe = SpinningRecipes.instance().findRecipeUsing(ingredient);
        if (recipe != null) {
            this.arecipes.add(new CachedSpinningRecipe(ingredient, recipe));
        }
    }

    public String getGuiTexture() {
        return "witchery:textures/gui/spinningwheel.png";
    }

    public void drawExtras(final int recipe) {
        this.drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
    }

    public String getOverlayIdentifier() {
        return "witchery_spinning";
    }

    public class CachedSpinningRecipe extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack fibre;
        PositionedStack output;
        PositionedStack add1;
        PositionedStack add2;
        PositionedStack add3;

        public CachedSpinningRecipe(final ItemStack result, final SpinningRecipes.SpinningRecipe recipe) {
            super();
            this.fibre = new PositionedStack((Object) recipe.fibre, 51, 9);
            this.output = new PositionedStack((Object) recipe.getResult(), 113, 9);
            if (recipe.modifiers.length > 0 && recipe.modifiers[0] != null) {
                this.add1 = new PositionedStack((Object) recipe.modifiers[0], 51, 42);
            }
            if (recipe.modifiers.length > 1 && recipe.modifiers[1] != null) {
                this.add2 = new PositionedStack((Object) recipe.modifiers[1], 69, 42);
            }
            if (recipe.modifiers.length > 2 && recipe.modifiers[2] != null) {
                this.add3 = new PositionedStack((Object) recipe.modifiers[2], 87, 42);
            }
        }

        public PositionedStack getResult() {
            return this.output;
        }

        public ArrayList<PositionedStack> getIngredients() {
            final ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(this.fibre);
            if (this.add1 != null) {
                recipestacks.add(this.add1);
            }
            if (this.add2 != null) {
                recipestacks.add(this.add2);
            }
            if (this.add3 != null) {
                recipestacks.add(this.add3);
            }
            return recipestacks;
        }
    }
}

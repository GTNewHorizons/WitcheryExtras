package alkalus.main.nei;

import java.awt.Rectangle;
import java.util.*;
import java.util.stream.Collectors;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import com.emoniph.witchery.*;
import com.emoniph.witchery.blocks.*;
import com.emoniph.witchery.crafting.*;

import alkalus.main.core.types.Witchery_Distillery;
import codechicken.nei.*;
import codechicken.nei.recipe.*;

public class NEI_Handler_Distillery extends TemplateRecipeHandler {

    public Class<? extends GuiContainer> getGuiClass() {
        return BlockDistilleryGUI.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.witchery:distilleryidle.name");
    }

    public void loadTransferRects() {
        this.transferRects.add(
            new TemplateRecipeHandler.RecipeTransferRect(
                new Rectangle(63, 4, 39, 35),
                "witchery_distilling",
                new Object[0]));
    }

    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("witchery_distilling") && this.getClass() == NEI_Handler_Distillery.class) {

            for (final DistilleryRecipes.DistilleryRecipe recipe : DistilleryRecipes.instance().recipes) {
                this.arecipes.add(new CachedDistillingRecipe(recipe.outputs[0], recipe));
            }

        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(final ItemStack result) {
        List<DistilleryRecipes.DistilleryRecipe> recipes = Witchery_Distillery.findRecipesFor(result);
        for (DistilleryRecipes.DistilleryRecipe recipe : recipes) {
            this.arecipes.add(new CachedDistillingRecipe(result, recipe));
        }
    }

    public void loadUsageRecipes(final ItemStack ingredient) {
        final DistilleryRecipes.DistilleryRecipe recipe = DistilleryRecipes.instance()
            .findRecipeUsing(ingredient);
        if (recipe != null) {
            this.arecipes.add(new CachedDistillingRecipe(ingredient, recipe));
        }
    }

    public String getGuiTexture() {
        return "witchery:textures/gui/distiller.png";
    }

    public void drawExtras(final int recipe) {
        this.drawProgressBar(63, 3, 176, 29, 39, 35, 200, 0);
        this.drawProgressBar(28, 8, 185, -2, 12, 30, 35, 3);
    }

    public String getOverlayIdentifier() {
        return "witchery_distilling";
    }

    public class CachedDistillingRecipe extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack ingred1;
        PositionedStack ingred2;
        PositionedStack jars;
        PositionedStack[] outputs;

        public CachedDistillingRecipe(final ItemStack result, final DistilleryRecipes.DistilleryRecipe recipe) {
            super();
            this.outputs = new PositionedStack[6];
            this.ingred1 = new PositionedStack((Object) recipe.inputs[0], 43, 5);
            if (recipe.inputs[1] != null) {
                this.ingred2 = new PositionedStack((Object) recipe.inputs[1], 43, 23);
            }
            if (recipe.jars > 0) {
                this.jars = new PositionedStack(
                    (Object) Witchery.Items.GENERIC.itemEmptyClayJar.createStack(recipe.jars),
                    43,
                    43);
            }
            if (recipe.outputs[0] != null) {
                this.outputs[0] = new PositionedStack((Object) recipe.outputs[0], 105, 5);
            }
            if (recipe.outputs[1] != null) {
                this.outputs[1] = new PositionedStack((Object) recipe.outputs[1], 123, 5);
            }
            if (recipe.outputs[2] != null) {
                this.outputs[2] = new PositionedStack((Object) recipe.outputs[2], 105, 23);
            }
            if (recipe.outputs[3] != null) {
                this.outputs[3] = new PositionedStack((Object) recipe.outputs[3], 123, 23);
            }
        }

        public PositionedStack getResult() {
            return null;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return Arrays.stream(this.outputs)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        public ArrayList<PositionedStack> getIngredients() {
            final ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(this.ingred1);
            if (this.ingred2 != null) {
                recipestacks.add(this.ingred2);
            }
            if (this.jars != null) {
                recipestacks.add(this.jars);
            }
            return recipestacks;
        }
    }
}

package alkalus.main.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockWitchesOvenGUI;

import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.crafting.OvenRecipes.OvenRecipe;
import alkalus.main.core.util.Utils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEI_Handler_Oven extends TemplateRecipeHandler {

    public Class<? extends GuiContainer> getGuiClass() {
        return BlockWitchesOvenGUI.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.witchery:witchesovenidle.name");
    }

    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("witchery_cooking") && this.getClass() == NEI_Handler_Oven.class) {

            final List<OvenRecipe> recipes = OvenRecipes.getRecipeMap();
            for (final OvenRecipe recipe : recipes) {
                if (recipe.isValid()) {
                    final ItemStack input = recipe.inputs.copy();
                    final ItemStack output = recipe.output.copy();
                    final ItemStack outputJar = recipe.outputJar.copy();
                    final SmeltingPair rec = new SmeltingPair(input, output, outputJar);
                    this.arecipes.add(rec);
                }
            }

        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(final ItemStack result) {
        if (result == null) {
            return;
        }
        final List<OvenRecipe> recipes = OvenRecipes.getRecipeMap();
        for (final OvenRecipe recipe : recipes) {
            if (recipe.isValid()) {
                final ItemStack input = recipe.inputs.copy();
                final ItemStack output = recipe.output.copy();
                final ItemStack outputJar = recipe.outputJar.copy();
                if (!Utils.areStacksEqual(result, output, true) && !Utils.areStacksEqual(result, outputJar, true)) {
                    continue;
                }
                final SmeltingPair rec = new SmeltingPair(input, output, outputJar);
                this.arecipes.add(rec);
            }
        }
    }

    public void loadUsageRecipes(final String inputId, final Object... ingredients) {
        if (inputId.equals("fuel") && this.getClass() == NEI_Handler_Oven.class) {
            this.loadCraftingRecipes("witchery_cooking", new Object[0]);
        } else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    public void loadUsageRecipes(final ItemStack ingredient) {

        final List<OvenRecipe> recipes = OvenRecipes.getRecipeMap();
        if (ingredient != null) {
            // Logger.INFO("Looking up Usage results for "+ItemUtils.getItemName(ingredient));
        }
        for (final OvenRecipe recipe : recipes) {
            if (recipe.isValid()) {
                final ItemStack input = recipe.inputs.copy();
                final ItemStack output = recipe.output.copy();
                final ItemStack outputJar = recipe.outputJar.copy();
                if (!Utils.areStacksEqual(ingredient, input, true) && !Utils
                        .areStacksEqual(ingredient, Witchery.Items.GENERIC.itemEmptyClayJar.createStack(), true)) {
                    continue;
                }
                final SmeltingPair rec = new SmeltingPair(input, output, outputJar);
                this.arecipes.add(rec);
            }
        }
    }

    public String getGuiTexture() {
        return "witchery:textures/gui/witchesOven.png";
    }

    public void loadTransferRects() {
        this.transferRects.add(
                new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel", new Object[0]));
        this.transferRects.add(
                new TemplateRecipeHandler.RecipeTransferRect(
                        new Rectangle(74, 9, 24, 18),
                        "witchery_cooking",
                        new Object[0]));
    }

    @Override
    public String specifyTransferRect() {
        return "witchery_cooking";
    }

    public void drawExtras(final int recipe) {
        this.drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        this.drawProgressBar(74, 9, 176, 14, 24, 16, 48, 0);
    }

    public String getOverlayIdentifier() {
        return "witchery_cooking";
    }

    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }

    public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack ingred;
        PositionedStack result;
        PositionedStack byproduct;
        PositionedStack jar;

        public SmeltingPair(final ItemStack ingred, final ItemStack result, final ItemStack byproduct) {
            super();
            ingred.stackSize = 1;
            this.ingred = new PositionedStack((Object) ingred, 51, 6);
            this.result = new PositionedStack((Object) result, 113, 10);
            this.byproduct = new PositionedStack((Object) byproduct, 113, 42);
            this.jar = new PositionedStack((Object) Witchery.Items.GENERIC.itemEmptyClayJar.createStack(), 78, 42);
        }

        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>) this
                    .getCycledIngredients(NEI_Handler_Oven.this.cycleticks / 48, (List) Arrays.asList(this.ingred));
        }

        public PositionedStack getResult() {
            return this.result;
        }

        public PositionedStack getOtherStack() {
            if (FurnaceRecipeHandler.afuels != null && FurnaceRecipeHandler.afuels.size() > 0) {
                return FurnaceRecipeHandler.afuels
                        .get(NEI_Handler_Oven.this.cycleticks / 48 % FurnaceRecipeHandler.afuels.size()).stack;
            }
            return null;
        }

        public List<PositionedStack> getOtherStacks() {
            final ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            final PositionedStack stack = this.getOtherStack();
            if (stack != null) {
                stacks.add(stack);
            }
            stacks.add(this.byproduct);
            stacks.add(this.jar);
            return stacks;
        }
    }

    public static class FuelPair {

        public PositionedStack stack;
        public int burnTime;

        public FuelPair(final ItemStack ingred, final int burnTime) {
            this.stack = new PositionedStack((Object) ingred, 51, 42);
            this.burnTime = burnTime;
        }
    }
}

package alkalus.main.nei;

import codechicken.nei.recipe.*;
import java.awt.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import com.emoniph.witchery.crafting.*;
import net.minecraft.item.*;
import codechicken.nei.*;
import java.util.*;

public class NEI_Handler_Kettle extends TemplateRecipeHandler
{
    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), "witchery_brewing", new Object[0]));
    }
    
    public Class<? extends GuiContainer> getGuiClass() {
        return (Class<? extends GuiContainer>)GuiCrafting.class;
    }
    
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.witchery:kettle.name");
    }
    
    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("witchery_brewing") && this.getClass() == NEI_Handler_Kettle.class) {
            for (final KettleRecipes.KettleRecipe recipe : KettleRecipes.instance().recipes) {
                this.arecipes.add(new CachedKettleRecipe(recipe.output, recipe));
            }
        }
        else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    public void loadCraftingRecipes(final ItemStack result) {
        final KettleRecipes.KettleRecipe recipe = KettleRecipes.instance().findRecipeFor(result);
        if (recipe != null) {
            this.arecipes.add(new CachedKettleRecipe(recipe.output, recipe));
        }
    }
    
    public void loadUsageRecipes(final ItemStack ingredient) {
    }
    
    public String getGuiTexture() {
        return "textures/gui/container/crafting_table.png";
    }
    
    public void drawExtras(final int recipe) {
    }
    
    public String getOverlayIdentifier() {
        return "witchery_brewing";
    }
    
    public class CachedKettleRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        PositionedStack result;
        PositionedStack[] inputs;
        
        public CachedKettleRecipe(final ItemStack result, final KettleRecipes.KettleRecipe recipe) {
            super();
            this.inputs = new PositionedStack[6];
            this.result = new PositionedStack((Object)result, 119, 24);
            for (int i = 0; i < recipe.inputs.length; ++i) {
                if (recipe.inputs[i] != null) {
                    this.inputs[i] = new PositionedStack((Object)recipe.inputs[i], (i < 3) ? 25 : 43, i * 18 % 54 + 6);
                }
                else {
                    this.inputs[i] = null;
                }
            }
        }
        
        public PositionedStack getResult() {
            return this.result;
        }
        
        public ArrayList<PositionedStack> getIngredients() {
            final ArrayList<PositionedStack> recipestacks = new ArrayList<PositionedStack>();
            recipestacks.add(this.result);
            for (final PositionedStack posStack : this.inputs) {
                if (posStack != null) {
                    recipestacks.add(posStack);
                }
            }
            return recipestacks;
        }
    }
}

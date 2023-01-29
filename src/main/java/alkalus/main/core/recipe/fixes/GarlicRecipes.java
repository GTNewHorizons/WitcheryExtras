package alkalus.main.core.recipe.fixes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.Utils;

import com.emoniph.witchery.Witchery;
import cpw.mods.fml.common.registry.GameRegistry;

public class GarlicRecipes {

    private final ItemStack mGarlics[];

    public GarlicRecipes() {
        WitcheryExtras.log(0, "Attempting to fix Witchery recipes that don't use OreDict for Garlic.");
        mGarlics = Utils.getAllItemsFromOreDictEntry("cropGarlic");
        fixGarlicArmour();
        fixVampireBook();
    }

    private void fixGarlicArmour() {
        Item[][] hunterItemsSilvered = {
                { Witchery.Items.HUNTER_BOOTS_SILVERED, Witchery.Items.HUNTER_BOOTS_GARLICKED },
                { Witchery.Items.HUNTER_LEGS_SILVERED, Witchery.Items.HUNTER_LEGS_GARLICKED },
                { Witchery.Items.HUNTER_COAT_SILVERED, Witchery.Items.HUNTER_COAT_GARLICKED },
                { Witchery.Items.HUNTER_HAT_SILVERED, Witchery.Items.HUNTER_HAT_GARLICKED } };
        for (int i = 0; i < hunterItemsSilvered.length; i++) {
            ItemStack currentStack = new ItemStack(hunterItemsSilvered[i][1]);
            if (Utils.removeAllCraftingRecipesByOutputItem(currentStack)) {
                WitcheryExtras
                        .log(1, "Successfully removed all crafting recipes for " + currentStack.getDisplayName() + ".");
            }
            for (ItemStack garlic : mGarlics) {
                CraftingManager.getInstance()
                        .addRecipe(
                                currentStack,
                                new Object[] { " g ", "g#g", " s ", Character.valueOf('#'),
                                        new ItemStack(hunterItemsSilvered[i][0]), Character.valueOf('s'),
                                        new ItemStack(Items.string), Character.valueOf('g'), garlic.copy() })
                        .func_92100_c();
            }
        }
        WitcheryExtras.log(0, "Fixed Recipes for Witchery Armours.");
    }

    private void fixVampireBook() {
        ItemStack currentStack = new ItemStack(Witchery.Items.VAMPIRE_BOOK);
        if (Utils.removeAllCraftingRecipesByOutputItem(currentStack)) {
            WitcheryExtras
                    .log(1, "Successfully removed all crafting recipes for " + currentStack.getDisplayName() + ".");
        }
        for (ItemStack garlic : mGarlics) {
            GameRegistry.addShapedRecipe(
                    currentStack,
                    new Object[] { "#s#", "#b#", "#g#", Character.valueOf('s'), new ItemStack(Items.nether_star),
                            Character.valueOf('b'), new ItemStack(Items.book), Character.valueOf('g'), garlic.copy(),
                            Character.valueOf('#'), new ItemStack(Items.nether_wart) });
        }
        WitcheryExtras.log(0, "Fixed recipe for the Vampire Book.");
    }
}

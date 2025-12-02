package alkalus.main.core.recipe.fixes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import com.emoniph.witchery.Witchery;

import alkalus.main.core.util.Utils;
import cpw.mods.fml.common.registry.GameRegistry;

public class GarlicRecipes {

    private final ItemStack[] mGarlics;

    public GarlicRecipes() {
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
        for (Item[] items : hunterItemsSilvered) {
            ItemStack currentStack = new ItemStack(items[1]);
            Utils.removeAllCraftingRecipesByOutputItem(currentStack);
            for (ItemStack garlic : mGarlics) {
                CraftingManager.getInstance()
                        .addRecipe(
                                currentStack,
                                new Object[] { " g ", "g#g", " s ", '#', new ItemStack(items[0]), 's',
                                        new ItemStack(Items.string), 'g', garlic.copy() })
                        .func_92100_c();
            }
        }
    }

    private void fixVampireBook() {
        ItemStack currentStack = new ItemStack(Witchery.Items.VAMPIRE_BOOK);
        Utils.removeAllCraftingRecipesByOutputItem(currentStack);
        for (ItemStack garlic : mGarlics) {
            GameRegistry.addShapedRecipe(
                    currentStack,
                    new Object[] { "#s#", "#b#", "#g#", 's', new ItemStack(Items.nether_star), 'b',
                            new ItemStack(Items.book), 'g', garlic.copy(), '#', new ItemStack(Items.nether_wart) });
        }
    }
}

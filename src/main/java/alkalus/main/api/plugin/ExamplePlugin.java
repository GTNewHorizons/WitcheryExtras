package alkalus.main.api.plugin;

import alkalus.main.api.RecipeManager;
import alkalus.main.api.plugin.base.BasePluginWitchery;
import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.util.Dye;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ExamplePlugin extends BasePluginWitchery {

    public ExamplePlugin() {
        super(new LoadPhase[] {LoadPhase.INIT, LoadPhase.POSTINIT});
    }

    @Override
    public String getPluginName() {
        return "Example_Plugin";
    }

    @Override
    public boolean preInit() {
        return false;
    }

    @Override
    public boolean init() {
        // Add Recipes Here
        RecipeManager.Kettle.addRecipe(
                Witchery.Items.GENERIC.itemBrewOfThorns.createStack(3), // Output
                1, // Witch Hat Bonus
                0, // Familiar ID
                0.0f, // Alter Power Required
                -10027232, // Kettle Brew Colour
                0, // Dimension ID Required (Best left 0)
                true, // Does this show in the Kettle Recipes book?
                new ItemStack[] { // Inputs
                    Dye.CACTUS_GREEN.createStack(),
                    Witchery.Items.GENERIC.itemDiamondVapour.createStack(),
                    Witchery.Items.GENERIC.itemOdourOfPurity.createStack(),
                    Witchery.Items.GENERIC.itemMandrakeRoot.createStack()
                });

        RecipeManager.Distillery.addRecipe(
                new ItemStack(Items.diamond), // Input A
                Witchery.Items.GENERIC.itemOilOfVitriol.createStack(), // Input B
                3, // Required Empty Jar Count
                Witchery.Items.GENERIC.itemMandrakeRoot.createStack(), // Output A
                Witchery.Items.GENERIC.itemDiamondVapour.createStack(), // Output B
                Witchery.Items.GENERIC.itemOdourOfPurity.createStack(), // Output C
                (ItemStack) null); // Output D
        return true;
    }

    @Override
    public boolean postInit() {
        // Remove Recipes Here
        RecipeManager.Kettle.removeRecipe(Witchery.Items.GENERIC.itemHappenstanceOil.createStack());
        RecipeManager.Distillery.removeRecipe(
                RecipeManager.Distillery.findRecipeUsingIngredient(new ItemStack(Items.diamond)));
        RecipeManager.RitesAndRituals.remove((byte) 1);
        RecipeManager.Infusions.remove(RecipeManager.Infusions.getInfusion(3));
        for (Prediction H : RecipeManager.Predictions.getPredictions().values()) {
            RecipeManager.Predictions.remove(H);
        }
        RecipeManager.CreaturePowers.remove(RecipeManager.CreaturePowers.getCreaturePower(18));
        return true;
    }
}

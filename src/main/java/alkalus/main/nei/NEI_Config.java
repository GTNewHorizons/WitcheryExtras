package alkalus.main.nei;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import alkalus.main.core.WitcheryExtras;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.IHighlightHandler;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.integration.NEIHighlightHandler;
import com.emoniph.witchery.util.Config;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;

public class NEI_Config implements IConfigureNEI {

    public void loadConfig() {
        if (Loader.isModLoaded("NotEnoughItems") && Config.instance().allowModIntegration
                && Config.instance().allowNotEnoughItems) {
            WitcheryExtras.log(0, "Detected NEI, registering our own custom recipe handlers.");
            API.registerRecipeHandler(new NEI_Handler_Oven());
            API.registerUsageHandler(new NEI_Handler_Oven());
            API.registerRecipeHandler(new NEI_Handler_Distillery());
            API.registerUsageHandler(new NEI_Handler_Distillery());
            API.registerRecipeHandler(new NEI_Handler_Kettle());
            API.registerUsageHandler(new NEI_Handler_Kettle());
            API.registerRecipeHandler(new NEI_Handler_Cauldron());
            API.registerUsageHandler(new NEI_Handler_Cauldron());
            API.registerRecipeHandler(new NEI_Handler_SpinningWheel());
            API.registerUsageHandler(new NEI_Handler_SpinningWheel());
            WitcheryExtras.log(0, "Handling Witchery items and registering highlight handlers.");
            fixWitcheryBrokenNEI();
        }
    }

    private final void fixWitcheryBrokenNEI() {
        API.hideItem(new ItemStack(Witchery.Blocks.OVEN_BURNING));
        API.hideItem(new ItemStack(Witchery.Blocks.DISTILLERY_BURNING));
        API.hideItem(new ItemStack(Witchery.Blocks.BARRIER));
        API.hideItem(new ItemStack(Witchery.Blocks.FORCE));
        API.hideItem(new ItemStack(Witchery.Blocks.CIRCLE));
        API.hideItem(new ItemStack(Witchery.Blocks.GLYPH_RITUAL));
        API.hideItem(new ItemStack(Witchery.Blocks.GLYPH_INFERNAL));
        API.hideItem(new ItemStack(Witchery.Blocks.GLYPH_OTHERWHERE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_BELLADONNA));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_MANDRAKE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_ARTICHOKE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_SNOWBELL));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_WORMWOOD));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_MINDRAKE));
        API.hideItem(new ItemStack(Witchery.Blocks.CHALICE));
        API.hideItem(new ItemStack(Witchery.Blocks.CANDELABRA));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.DREAM_CATCHER));
        API.hideItem(new ItemStack(Witchery.Blocks.DOOR_ALDER));
        API.hideItem(new ItemStack(Witchery.Blocks.DOOR_ROWAN));
        API.hideItem(new ItemStack(Witchery.Blocks.PERPETUAL_ICE_DOOR));
        API.hideItem(new ItemStack(Witchery.Blocks.GLOW_GLOBE));
        API.hideItem(new ItemStack(Witchery.Blocks.PLACED_ITEMSTACK));
        API.hideItem(new ItemStack(Witchery.Blocks.DEMON_HEART));
        API.hideItem(new ItemStack(Witchery.Blocks.FORCE));
        API.hideItem(new ItemStack(Witchery.Blocks.WEB));
        API.hideItem(new ItemStack(Witchery.Blocks.VINE));
        API.hideItem(new ItemStack(Witchery.Blocks.CACTUS));
        API.hideItem(new ItemStack(Witchery.Blocks.LILY));
        API.hideItem(new ItemStack(Witchery.Blocks.BREW_GAS));
        API.hideItem(new ItemStack(Witchery.Blocks.BREW_LIQUID));
        API.hideItem(new ItemStack(Witchery.Blocks.BREW));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.SLURP));
        API.hideItem(new ItemStack(Witchery.Items.BREW));
        API.hideItem(new ItemStack(Witchery.Items.BUCKET_BREW));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_BUTTON_STONE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_BUTTON_WOOD));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_LEVER));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_SNOW_PRESSURE_PLATE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_STONE_PRESSURE_PLATE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_WOODEN_DOOR));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CURSED_WOODEN_PRESSURE_PLATE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_WOLFSBANE));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.CROP_GARLIC));
        API.hideItem(new ItemStack(Witchery.Blocks.WALLGEN));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.LIGHT));
        API.hideItem(new ItemStack(Witchery.Blocks.SHADED_GLASS_ON));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.MIRROR));
        API.hideItem(new ItemStack((Block) Witchery.Blocks.MIRROR_UNBREAKABLE));

        API.registerHighlightIdentifier(
                Witchery.Blocks.TRAPPED_PLANT,
                (IHighlightHandler) new NEIHighlightHandler(Witchery.Blocks.TRAPPED_PLANT));
        API.registerHighlightIdentifier(
                Witchery.Blocks.DOOR_ALDER,
                (IHighlightHandler) new NEIHighlightHandler(Witchery.Blocks.DOOR_ALDER));
        API.registerHighlightIdentifier(
                Witchery.Blocks.PIT_DIRT,
                (IHighlightHandler) new NEIHighlightHandler(Witchery.Blocks.PIT_DIRT));
        API.registerHighlightIdentifier(
                Witchery.Blocks.PIT_GRASS,
                (IHighlightHandler) new NEIHighlightHandler(Witchery.Blocks.PIT_GRASS));
        WitcheryExtras.log(0, "Done handling NEI related content.");
    }

    public String getName() {
        return WitcheryExtras.class.getAnnotation(Mod.class).name();
    }

    public String getVersion() {
        return WitcheryExtras.class.getAnnotation(Mod.class).version();
    }
}

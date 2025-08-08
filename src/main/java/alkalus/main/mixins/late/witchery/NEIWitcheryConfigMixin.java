package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.integration.NEIWitcheryConfig;

import alkalus.main.nei.NEI_Handler_Cauldron;
import alkalus.main.nei.NEI_Handler_Distillery;
import alkalus.main.nei.NEI_Handler_Kettle;
import alkalus.main.nei.NEI_Handler_Oven;
import alkalus.main.nei.NEI_Handler_SpinningWheel;
import codechicken.nei.api.API;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;

@Mixin(value = NEIWitcheryConfig.class, remap = false)
public class NEIWitcheryConfigMixin {

    @Redirect(
            method = "loadConfig",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodechicken/nei/api/API;registerRecipeHandler(Lcodechicken/nei/recipe/ICraftingHandler;)V"))
    private void cancelRecipeHandlerRegister(ICraftingHandler handler) {
        // do nothing
    }

    @Redirect(
            method = "loadConfig",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodechicken/nei/api/API;registerUsageHandler(Lcodechicken/nei/recipe/IUsageHandler;)V"))
    private void cancelUsageHandlerRegister(IUsageHandler handler) {
        // do nothing
    }

    @Inject(
            method = "loadConfig",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodechicken/nei/api/API;registerHighlightIdentifier(Lnet/minecraft/block/Block;Lcodechicken/nei/api/IHighlightHandler;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0))
    private void registerHandler(CallbackInfo ci) {
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
    }

}

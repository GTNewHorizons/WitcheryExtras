package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.emoniph.witchery.brewing.potions.PotionResizing;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(value = PotionResizing.class, remap = false)
public class PotionResizingMixin {

    @Expression("? * 0.92")
    @ModifyExpressionValue(method = "onLivingUpdate", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float fixEyeHeight(float original, @Local(name = "currentHeight") float currentHeight,
            @Local(name = "reductionFactor") float reductionFactor,
            @Local(name = "requiredHeight") float requiredHeight) {
        return Math.max(currentHeight - reductionFactor, requiredHeight) * 0.92F;
    }
}

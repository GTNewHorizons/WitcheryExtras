package alkalus.main.mixins.early.minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyExpressionValue(method = "isEntityInsideOpaqueBlock", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
    private float applyOffset(float origin) {
        if ((Object) this instanceof EntityPlayer player) {
            return origin * player.height / 1.8F;
        }
        return origin;
    }

    @ModifyConstant(method = "handleWaterMovement", constant = @Constant(doubleValue = -0.4000000059604645D))
    private double etfu$keepSwimmingPlayersInWater(double origin) {
        if ((Object) this instanceof EntityPlayer player) {
            return origin * player.height / 1.8F;
        }
        return origin;
    }
}

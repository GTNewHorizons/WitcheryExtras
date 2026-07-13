package alkalus.main.mixins.early.minecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import alkalus.main.proxy.CommonProxy;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyExpressionValue(method = "isEntityInsideOpaqueBlock", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
    private float applyOffset(float origin) {
        if (!CommonProxy.usingEFR && (Object) this instanceof EntityPlayer player) {
            return origin * player.height / 1.8F;
        }
        return origin;
    }

    @ModifyExpressionValue(
            method = "handleWaterMovement",
            at = @At(value = "CONSTANT", args = "doubleValue=-0.4000000059604645D"))
    private double etfu$keepSwimmingPlayersInWater(double origin) {
        if (!CommonProxy.usingEFR && (Object) this instanceof EntityPlayer player) {
            return origin * player.height / 1.8F;
        }
        return origin;
    }
}

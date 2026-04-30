package alkalus.main.mixins.early.minecraft;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import alkalus.main.mixins.hooks.EntitySizeManager;

@Mixin(NetHandlerPlayServer.class)
public class NetHandlerPlayServerMixin {

    @Shadow
    public EntityPlayerMP playerEntity;

    @ModifyExpressionValue(method = "processPlayer", at = @At(value = "CONSTANT", args = "doubleValue=1.65D"))
    private double applyOffset(double origin) {
        // Because shapeshift is a slow process.
        return origin - Math.min(EntitySizeManager.OffsetContents.getTargetOffset(playerEntity), 0f);
    }
}

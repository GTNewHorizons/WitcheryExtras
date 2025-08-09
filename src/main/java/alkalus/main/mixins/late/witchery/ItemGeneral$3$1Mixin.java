package alkalus.main.mixins.late.witchery;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@SuppressWarnings("all") // intellij has a hard time connecting the dots here
@Mixin(targets = "com.emoniph.witchery.item.ItemGeneral$3$1")
public class ItemGeneral$3$1Mixin {

    @Shadow(remap = false)
    @Final
    int val$refMeta;

    @ModifyExpressionValue(
        at = @At(
            ordinal = 0,
            remap = false,
            target = "Lcom/emoniph/witchery/util/BlockUtil;isReplaceableBlock(Lnet/minecraft/world/World;III)Z",
            value = "INVOKE"),
        method = "onSpiralBlockAction",
        remap = false)
    private boolean checkMetaY(boolean replaceable, World world, int posX, int posY, int posZ) {
        return replaceable && world.getBlockMetadata(posX, posY, posZ) == this.val$refMeta;
    }

    @ModifyExpressionValue(
        at = @At(
            ordinal = 1,
            remap = false,
            target = "Lcom/emoniph/witchery/util/BlockUtil;isReplaceableBlock(Lnet/minecraft/world/World;III)Z",
            value = "INVOKE"),
        method = "onSpiralBlockAction",
        remap = false)
    private boolean checkMetaY1(boolean replaceable, World world, int posX, int posY, int posZ) {
        return replaceable && world.getBlockMetadata(posX, posY + 1, posZ) == this.val$refMeta;
    }

    @ModifyExpressionValue(
        at = @At(
            ordinal = 2,
            remap = false,
            target = "Lcom/emoniph/witchery/util/BlockUtil;isReplaceableBlock(Lnet/minecraft/world/World;III)Z",
            value = "INVOKE"),
        method = "onSpiralBlockAction",
        remap = false)
    private boolean checkMetaYMinus1(boolean replaceable, World world, int posX, int posY, int posZ) {
        return replaceable && world.getBlockMetadata(posX, posY - 1, posZ) == this.val$refMeta;
    }

    @ModifyExpressionValue(
        at = @At(
            ordinal = 3,
            remap = false,
            target = "Lcom/emoniph/witchery/util/BlockUtil;isReplaceableBlock(Lnet/minecraft/world/World;III)Z",
            value = "INVOKE"),
        method = "onSpiralBlockAction",
        remap = false)
    private boolean checkMetaY2(boolean replaceable, World world, int posX, int posY, int posZ) {
        return replaceable && world.getBlockMetadata(posX, posY + 2, posZ) == this.val$refMeta;
    }

    @ModifyExpressionValue(
        at = @At(
            ordinal = 4,
            remap = false,
            target = "Lcom/emoniph/witchery/util/BlockUtil;isReplaceableBlock(Lnet/minecraft/world/World;III)Z",
            value = "INVOKE"),
        method = "onSpiralBlockAction",
        remap = false)
    private boolean checkMetaYMinus2(boolean replaceable, World world, int posX, int posY, int posZ) {
        return replaceable && world.getBlockMetadata(posX, posY - 2, posZ) == this.val$refMeta;
    }
}

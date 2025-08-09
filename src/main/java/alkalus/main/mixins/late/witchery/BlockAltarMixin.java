package alkalus.main.mixins.late.witchery;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.emoniph.witchery.blocks.BlockAltar;
import com.emoniph.witchery.blocks.BlockBaseContainer;

import io.github.tox1cozz.mixinextras.injector.ModifyExpressionValue;

@SuppressWarnings("UnusedMixin")
@Mixin(BlockAltar.class)
public class BlockAltarMixin extends BlockBaseContainer {

    public BlockAltarMixin(Material material, Class<? extends TileEntity> clazzTile) {
        super(material, clazzTile);
    }

    // Essentially removes the te > 3 check
    @ModifyConstant(method = "updateMultiblock", constant = @Constant(intValue = 3, ordinal = 1), remap = false)
    private int witcheryextras$removeTECheck(int original) {
        return 999;
    }

    // validates altars with size 9
    @ModifyExpressionValue(
            method = "updateMultiblock",
            at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;size()I", ordinal = 1),
            remap = false)
    private int witcheryextras$addMultiblockSize(int original) {
        return (original == 9) ? 6 : original;
    }
}

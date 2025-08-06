package alkalus.main.mixins.late.witchery;

import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;

import alkalus.main.core.util.WitchesOvenUtils;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityWitchesOven.class)
public class TileEntityWitchesOvenMixin extends TileEntity {

    @Inject(
            method = "updateEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;markBlockForUpdate(III)V",
            shift = At.Shift.AFTER))
    private void saveTile(CallbackInfo ci) {
        this.markDirty();
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private boolean canSmelt() {
        return WitchesOvenUtils.canSmelt((TileEntityWitchesOven) (Object) this);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    public void smeltItem() {
        WitchesOvenUtils.smeltItem((TileEntityWitchesOven) (Object) this);
    }

}

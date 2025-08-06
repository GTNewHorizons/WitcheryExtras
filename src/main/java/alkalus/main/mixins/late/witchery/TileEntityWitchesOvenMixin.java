package alkalus.main.mixins.late.witchery;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.emoniph.witchery.blocks.BlockWitchesOven.TileEntityWitchesOven;

import alkalus.main.core.util.WitchesOvenUtils;

@Mixin(TileEntityWitchesOven.class)
public class TileEntityWitchesOvenMixin extends TileEntity {

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite
    public void updateEntity() {
        WitchesOvenUtils.updateEntity((TileEntityWitchesOven) (Object) this);
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

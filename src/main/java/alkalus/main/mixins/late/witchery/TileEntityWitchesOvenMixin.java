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

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private int getFumeFunnels() {
        return WitchesOvenUtils.getFumeFunnels((TileEntityWitchesOven) (Object) this);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private boolean isFumeFunnel(int xCoord, int yCoord, int zCoord, int meta) {
        return WitchesOvenUtils.isFumeFunnel((TileEntityWitchesOven) (Object) this, xCoord, yCoord, zCoord, meta);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private double getFumeFunnelsChance() {
        return WitchesOvenUtils.getFumeFunnelsChance((TileEntityWitchesOven) (Object) this);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private double getFumeFunnelChance(int x, int y, int z, int meta) {
        return WitchesOvenUtils.getFumeFunnelChance(
                (TileEntityWitchesOven) (Object) this,
                this.xCoord,
                this.yCoord,
                this.zCoord,
                meta);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private int getCookTime() {
        return WitchesOvenUtils.getCookTime((TileEntityWitchesOven) (Object) this);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private void generateByProduct(ItemStack itemstack) {
        WitchesOvenUtils.generateByProduct((TileEntityWitchesOven) (Object) this, itemstack);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    private void createByProduct(ItemStack byProduct) {
        WitchesOvenUtils.createByProduct((TileEntityWitchesOven) (Object) this, byProduct);
    }

}

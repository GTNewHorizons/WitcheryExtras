package alkalus.main.mixins.late.witchery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.emoniph.witchery.blocks.BlockPoppetShelf.TileEntityPoppetShelf;
import com.emoniph.witchery.blocks.TileEntityBase;

import alkalus.main.core.util.PoppetShelfUtils;

@Mixin(TileEntityPoppetShelf.class)
public class TileEntityPoppetShelfMixin extends TileEntityBase {

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite(remap = false)
    protected void initiate() {
        super.initiate();
        PoppetShelfUtils.initiate((TileEntityPoppetShelf) (Object) this);
    }

    /**
     * @author - Alkalus
     * @reason reasons
     */
    @Overwrite
    public void invalidate() {
        PoppetShelfUtils.invalidate((TileEntityPoppetShelf) (Object) this);
        super.invalidate();
    }
}

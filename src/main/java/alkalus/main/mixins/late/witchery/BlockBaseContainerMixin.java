package alkalus.main.mixins.late.witchery;

import alkalus.main.config.AsmConfig;
import com.emoniph.witchery.blocks.BlockBaseContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBaseContainer.class)
public abstract class BlockBaseContainerMixin extends BlockContainer {
    protected BlockBaseContainerMixin(Material p_i45386_1_) {
        super(p_i45386_1_);
    }

    /// Disables creative tab, block and TE registration
    @Inject(method = "setBlockName", at=@At("HEAD"), cancellable = true)
    private void witcheryextras$setBlockName(String blockName, CallbackInfoReturnable<Block> cir) {
        if (AsmConfig.disableSilverVat && blockName.equals("witchery:silvervat")) {
            cir.setReturnValue(super.setBlockName(blockName));
        }
    }
}

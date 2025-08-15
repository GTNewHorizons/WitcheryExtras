package alkalus.main.mixins.late.witchery;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.util.BlockProtect;

@SuppressWarnings("UnusedMixin")
@Mixin(targets = "com.emoniph.witchery.infusion.infusions.InfusionOverworld$ShockwaveTask")
public class ShockwaveTaskMixin {

    @Inject(
            method = "drawPixel(Lnet/minecraft/world/World;IIIII)V",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    private void drawPixel(World world, int x, int y, int z, int blocksToMove, int direction, CallbackInfo ci) {

        ci.cancel();
        // Overrides the original method error logic.
        int i;
        Block blockID;
        int blockMetadata;
        if (direction > 0) {
            if (world.isAirBlock(x, y - blocksToMove + 1, z) || world.getBlock(x, y + 1, z).getMaterial().isSolid()) {
                return;
            }

            for (i = 0; i < blocksToMove; ++i) if (!BlockProtect.canBreak(x, y - i, z, world)
                    || !BlockProtect.canBreak(x, y - i + direction, z, world))
                return;
            for (i = 0; i < blocksToMove; ++i) {
                blockID = world.getBlock(x, y - i, z);
                blockMetadata = world.getBlockMetadata(x, y - i, z);

                world.setBlockToAir(x, y - i, z);
                world.setBlock(x, y - i + direction, z, blockID, blockMetadata, 3);
            }
        } else {
            if (world.isAirBlock(x, y, z) || world.getBlock(x, y + direction - 1, z).getMaterial().isSolid()) {
                return;
            }
            for (i = blocksToMove - 1; i >= 0; --i) if (!BlockProtect.canBreak(x, y - i, z, world)
                    || !BlockProtect.canBreak(x, y - i + direction, z, world))
                return;

            for (i = blocksToMove - 1; i >= 0; --i) {
                blockID = world.getBlock(x, y - i, z);
                blockMetadata = world.getBlockMetadata(x, y - i, z);

                world.setBlockToAir(x, y - i, z);
                world.setBlock(x, y - i + direction, z, blockID, blockMetadata, 3);
            }
        }
    }
}

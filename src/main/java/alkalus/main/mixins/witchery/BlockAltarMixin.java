package alkalus.main.mixins.witchery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.emoniph.witchery.blocks.BlockAltar;
import com.emoniph.witchery.blocks.BlockBaseContainer;
import com.emoniph.witchery.util.Coord;

@Mixin(value = BlockAltar.class)
public class BlockAltarMixin extends BlockBaseContainer {

    private static final Logger log = LogManager.getLogger(BlockAltarMixin.class);

    public BlockAltarMixin(Material material, Class<? extends TileEntity> clazzTile) {
        super(material, clazzTile);
    }

    @Inject(method = "updateMultiblock", at = @At("HEAD"), remap = false, cancellable = true)
    public void updateMultiblock(World world, int x, int y, int z, Coord exclude, CallbackInfo ci) {
        if (!world.isRemote) {

            ArrayList<Coord> visited = new ArrayList<>();
            ArrayList<Coord> toVisit = new ArrayList<>();
            toVisit.add(new Coord(x, y, z));
            boolean valid = true;

            while (!toVisit.isEmpty()) {
                Coord coord = (Coord) toVisit.get(0);
                toVisit.remove(0);
                int neighbours = 0;

                for (Coord newCoord : new Coord[] { coord.north(), coord.south(), coord.east(), coord.west() }) {
                    if (newCoord.getBlock(world) == this) {
                        if (!visited.contains(newCoord) && !toVisit.contains(newCoord)) {
                            toVisit.add(newCoord);
                        }

                        ++neighbours;
                    }
                }

                if (!coord.equals(exclude)) {
                    if (neighbours < 2 || neighbours > 4) {
                        valid = false;
                    }

                    visited.add(coord);
                }
            }

            Coord newCore = valid && (visited.size() == 6 || visited.size() == 9) ? (Coord) visited.get(0) : null;

            for (Coord coord : visited) {
                TileEntity te = coord.getBlockTileEntity(world);
                if (te instanceof BlockAltar.TileEntityAltar) {
                    BlockAltar.TileEntityAltar tile = (BlockAltar.TileEntityAltar) te;

                    // Workaround to access a Private Method
                    Method method = null;
                    try {
                        method = tile.getClass().getDeclaredMethod("setCore", Coord.class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    method.setAccessible(true);

                    try {
                        method.invoke(tile, newCore);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (exclude != null) {
                TileEntity te = exclude.getBlockTileEntity(world);
                if (te instanceof BlockAltar.TileEntityAltar) {
                    BlockAltar.TileEntityAltar tile = (BlockAltar.TileEntityAltar) te;

                    Method method = null;
                    try {
                        method = tile.getClass().getDeclaredMethod("setCore", Coord.class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    method.setAccessible(true);

                    try {
                        method.invoke(tile, newCore);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        ci.cancel();
    }
}

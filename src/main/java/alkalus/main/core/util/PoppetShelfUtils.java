package alkalus.main.core.util;

import java.lang.reflect.Field;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockPoppetShelf.TileEntityPoppetShelf;
import com.emoniph.witchery.util.Log;

import alkalus.main.asm.AsmConfig;

public class PoppetShelfUtils {

    private static Ticket getShelfTicket(TileEntityPoppetShelf aShelf) {
        Field aChunkTicket = ReflectionUtils.getField(TileEntityPoppetShelf.class, "");
        if (aChunkTicket != null) {
            return (Ticket) ReflectionUtils.getFieldValue(aChunkTicket, aShelf);
        }
        return null;
    }

    public static void initiate(TileEntityPoppetShelf aShelf) {
        if (AsmConfig.allowPoppetShelfChunkLoading) {
            Ticket chunkTicket = getShelfTicket(aShelf);
            if (!aShelf.getWorldObj().isRemote && chunkTicket == null) {
                chunkTicket = ForgeChunkManager.requestTicket(Witchery.instance, aShelf.getWorldObj(), Type.NORMAL);
                if (chunkTicket != null) {
                    chunkTicket.getModData().setInteger("poppetX", aShelf.xCoord);
                    chunkTicket.getModData().setInteger("poppetY", aShelf.yCoord);
                    chunkTicket.getModData().setInteger("poppetZ", aShelf.zCoord);
                    aShelf.forceChunkLoading(chunkTicket);
                } else {
                    Log.instance().warning(
                            String.format(
                                    "The poppet shelf at %d, %d, %d failed to register a chunk loader.",
                                    new Object[] { Integer.valueOf(aShelf.xCoord), Integer.valueOf(aShelf.yCoord),
                                            Integer.valueOf(aShelf.zCoord) }));
                }
            }
        }
    }

    public static void invalidate(TileEntityPoppetShelf aShelf) {
        if (!aShelf.getWorldObj().isRemote) {
            if (AsmConfig.allowPoppetShelfChunkLoading) {
                Ticket chunkTicket = getShelfTicket(aShelf);
                if (chunkTicket != null) {
                    ForgeChunkManager.releaseTicket(chunkTicket);
                } else {
                    Log.instance().warning(
                            String.format(
                                    "Chunk loader ticket is null for poppet shelf at %d, %d, %d.",
                                    new Object[] { Integer.valueOf(aShelf.xCoord), Integer.valueOf(aShelf.yCoord),
                                            Integer.valueOf(aShelf.zCoord) }));
                }
            }
        }
    }
}

package alkalus.main.network;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.player.EntityPlayerMP;

import alkalus.main.core.biomebook.BiomeBookData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/** Client -> server: trigger asking for the authoritative biome list. Empty payload. */
public class BiomeBookRequestPacket implements IMessage {

    public BiomeBookRequestPacket() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<BiomeBookRequestPacket, IMessage> {

        /** Minimum time between served requests per player. */
        private static final long RATE_LIMIT_MS = 1000L;
        /** Drop tracked UUIDs whose last request is older than this. */
        private static final long PRUNE_AFTER_MS = 60_000L;

        private static final Map<UUID, Long> LAST_REQUEST = new ConcurrentHashMap<>();

        @Override
        public IMessage onMessage(BiomeBookRequestPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            if (player == null) {
                return null;
            }
            UUID id = player.getUniqueID();
            long now = System.currentTimeMillis();
            Long last = LAST_REQUEST.get(id);
            if (last != null && now - last < RATE_LIMIT_MS) {
                return null;
            }
            LAST_REQUEST.put(id, now);
            prune(now);
            return new BiomeBookSyncPacket(BiomeBookData.getSections());
        }

        private static void prune(long now) {
            Iterator<Map.Entry<UUID, Long>> it = LAST_REQUEST.entrySet().iterator();
            while (it.hasNext()) {
                if (now - it.next().getValue() > PRUNE_AFTER_MS) {
                    it.remove();
                }
            }
        }
    }
}

package alkalus.main.network;

import static alkalus.main.mixins.hooks.EntitySizeManager.OFFSET_PROPERTY;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

import alkalus.main.mixins.hooks.EntitySizeManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class EntitySizeSyncPacket implements IMessage {

    public EntitySizeSyncPacket() {}

    public EntitySizeSyncPacket(float targetOffset) {
        this.targetOffset = targetOffset;
        this.isInstant = false;
    }

    public EntitySizeSyncPacket(float targetOffset, boolean isInstant) {
        this.targetOffset = targetOffset;
        this.isInstant = isInstant;
    }

    float targetOffset;
    boolean isInstant;

    @Override
    public void fromBytes(ByteBuf buf) {
        targetOffset = buf.readFloat();
        isInstant = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(targetOffset);
        buf.writeBoolean(isInstant);
    }

    public static class Handler implements IMessageHandler<EntitySizeSyncPacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(EntitySizeSyncPacket message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player == null) {
                return null;
            }
            IExtendedEntityProperties prop = player.getExtendedProperties(OFFSET_PROPERTY);
            if (prop instanceof EntitySizeManager.OffsetContents contents) {
                contents.targetOffset = message.targetOffset;
                if (message.isInstant) contents.currentOffset = contents.targetOffset;
            }
            return null;
        }
    }
}

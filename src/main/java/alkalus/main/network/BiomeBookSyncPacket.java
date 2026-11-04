package alkalus.main.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import alkalus.main.mixins.IBiomeBookScreen;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

/** Server -> client: the authoritative biome list, grouped into sections (BIOME_TYPES order). */
public class BiomeBookSyncPacket implements IMessage {

    public List<List<BiomeBookEntry>> sections = new ArrayList<>();

    public BiomeBookSyncPacket() {}

    public BiomeBookSyncPacket(List<List<BiomeBookEntry>> sections) {
        this.sections = sections;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int sectionCount = buf.readInt();
        this.sections = new ArrayList<>(sectionCount);
        for (int s = 0; s < sectionCount; s++) {
            int count = buf.readInt();
            List<BiomeBookEntry> section = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                int id = buf.readInt();
                String name = ByteBufUtils.readUTF8String(buf);
                float temperature = buf.readFloat();
                float rainfall = buf.readFloat();
                boolean enableSnow = buf.readBoolean();
                boolean enableRain = buf.readBoolean();
                section.add(new BiomeBookEntry(id, name, temperature, rainfall, enableSnow, enableRain));
            }
            this.sections.add(section);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.sections.size());
        for (List<BiomeBookEntry> section : this.sections) {
            buf.writeInt(section.size());
            for (BiomeBookEntry e : section) {
                buf.writeInt(e.id);
                ByteBufUtils.writeUTF8String(buf, e.name);
                buf.writeFloat(e.temperature);
                buf.writeFloat(e.rainfall);
                buf.writeBoolean(e.enableSnow);
                buf.writeBoolean(e.enableRain);
            }
        }
    }

    public static class Handler implements IMessageHandler<BiomeBookSyncPacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(BiomeBookSyncPacket message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.func_152344_a(() -> {
                GuiScreen screen = mc.currentScreen;
                if (screen instanceof IBiomeBookScreen) {
                    ((IBiomeBookScreen) screen).witcheryextras$populate(message.sections);
                }
            });
            return null;
        }
    }
}

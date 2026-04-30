package alkalus.main.mixins.hooks;

import static alkalus.main.core.WitcheryExtras.NETWORK;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.brewing.potions.PotionResizing;
import com.emoniph.witchery.network.PacketSyncEntitySize;
import com.emoniph.witchery.util.EntitySizeInfo;

import alkalus.main.network.EntitySizeSyncPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntitySizeManager {

    public static float getTargetYOffset(EntityPlayer player) {
        EntitySizeInfo size = new EntitySizeInfo(player);
        PotionEffect shrunk = Witchery.Potions.RESIZING != null
                ? player.getActivePotionEffect(Witchery.Potions.RESIZING)
                : null;
        if (shrunk == null && size.isDefault) {
            return 0;
        }
        float amp = shrunk != null ? PotionResizing.getScaleFactor(shrunk.getAmplifier()) : 1.0F;
        float scale = size.defaultHeight / 1.8F * amp;
        return 1.8F * (1.0F - scale);
    }

    public static final String OFFSET_PROPERTY = "ShapeShiftOffset";

    public static class OffsetContents implements IExtendedEntityProperties {

        public float currentOffset;
        public float targetOffset;

        @Override
        public void saveNBTData(NBTTagCompound compound) {
            compound.setFloat("currentOffset", currentOffset);
            compound.setFloat("targetOffset", targetOffset);
        }

        @Override
        public void loadNBTData(NBTTagCompound compound) {
            currentOffset = compound.getFloat("currentOffset");
            targetOffset = compound.getFloat("targetOffset");
        }

        @Override
        public void init(Entity entity, World world) {}

        public static OffsetContents get(EntityPlayer player) {
            return (OffsetContents) player.getExtendedProperties(OFFSET_PROPERTY);
        }

        public static float getCurrentOffset(EntityPlayer player) {
            OffsetContents prop = get(player);
            return prop != null ? prop.currentOffset : 0f;
        }

        public static float getTargetOffset(EntityPlayer player) {
            OffsetContents prop = get(player);
            return prop != null ? prop.targetOffset : 0f;
        }
    }

    @SubscribeEvent
    public void onEntityConstruction(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            event.entity.registerExtendedProperties(OFFSET_PROPERTY, new OffsetContents());
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.world.isRemote && event.entity instanceof EntityPlayerMP player) {
            NETWORK.sendTo(new EntitySizeSyncPacket(EntitySizeManager.getTargetYOffset(player), true), player);
            Witchery.packetPipeline.sendToAll(new PacketSyncEntitySize(player));
            Witchery.packetPipeline.sendTo(new PacketSyncEntitySize(player), player);
        }
    }

    @SubscribeEvent
    public void onClonePlayer(PlayerEvent.Clone event) {
        if (event.wasDeath) return;
        OffsetContents contents = OffsetContents.get(event.entityPlayer);
        OffsetContents contentsOrigin = OffsetContents.get(event.original);
        if (contents != null && contentsOrigin != null) {
            contents.currentOffset = contentsOrigin.currentOffset;
            contents.targetOffset = contentsOrigin.targetOffset;
        }
    }
}

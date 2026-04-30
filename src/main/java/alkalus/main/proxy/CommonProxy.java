package alkalus.main.proxy;

import net.minecraftforge.common.MinecraftForge;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.handlers.ServerSizeHandler;
import alkalus.main.mixins.hooks.EntitySizeManager;
import alkalus.main.network.EntitySizeSyncPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new ServerSizeHandler());
        MinecraftForge.EVENT_BUS.register(new EntitySizeManager());
        WitcheryExtras.NETWORK
                .registerMessage(EntitySizeSyncPacket.Handler.class, EntitySizeSyncPacket.class, 0, Side.CLIENT);
    }

    public void postInit(FMLPostInitializationEvent event) {}
}

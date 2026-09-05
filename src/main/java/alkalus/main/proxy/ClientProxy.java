package alkalus.main.proxy;

import alkalus.main.handlers.ClientSizeHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        if (usingEFR) {
            return;
        }
        FMLCommonHandler.instance().bus().register(new ClientSizeHandler());
    }
}

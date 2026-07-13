package alkalus.main.proxy;

import alkalus.main.compat.EFRCompat;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    static public boolean usingEFR = false;

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        if (Loader.isModLoaded("etfuturum")) {
            if (EFRCompat.isPoseSystemEnabled()) {
                usingEFR = true;
            }
        }
    }

    public void postInit(FMLPostInitializationEvent event) {}
}

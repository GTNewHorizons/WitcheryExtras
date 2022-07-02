package alkalus.main.asm;

import alkalus.main.core.util.Logger;
import alkalus.main.core.util.ReflectionUtils;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import java.util.Arrays;
import java.util.List;

public class WE_CORE_Container extends DummyModContainer {

    public WE_CORE_Container() {

        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = WE_CORE.MODID;
        meta.name = WE_CORE.NAME;
        meta.version = WE_CORE.VERSION;
        meta.credits = "Roll Credits ...";
        meta.authorList = Arrays.asList("Alkalus");
        meta.description = "";
        meta.url = "";
        meta.updateUrl = "";
        meta.screenshots = new String[0];
        meta.logoFile = "";
        meta.dependencies = (List<ArtifactVersion>) WE_CORE.DEPENDENCIES;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void modConstruction(FMLConstructionEvent evt) {}

    @Subscribe
    public void init(FMLInitializationEvent evt) {}

    @EventHandler
    public void load(final FMLInitializationEvent e) {
        Logger.ASM("Begin resource allocation for " + WE_CORE.MODID + " V" + WE_CORE.VERSION);
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        Logger.ASM("Loading " + WE_CORE.MODID + " V" + WE_CORE.VERSION);
        if (AsmConfig.enablePatchNEI) {
            // Pre-load this class so it can be transformed into an empty class. Fixes NEI catching and registering it.
            ReflectionUtils.doesClassExist("com.emoniph.witchery.integration.NEIWitcheryConfig");
        }
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent evt) {
        Logger.ASM("Finished loading Witchery++ Pre-Loader.");
    }
}

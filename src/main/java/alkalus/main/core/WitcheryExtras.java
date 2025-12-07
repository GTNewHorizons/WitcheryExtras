package alkalus.main.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.MinecraftForge;

import alkalus.main.api.plugin.base.BasePluginWitchery;
import alkalus.main.core.crafting.OvenRecipes;
import alkalus.main.core.entities.PredictionHandler;
import alkalus.main.core.recipe.fixes.GarlicRecipes;
import alkalus.main.core.util.Logger;
import alkalus.main.core.util.TooltipHandler;
import baubles.api.expanded.BaubleExpandedSlots;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = WitcheryExtras.MODID,
        name = WitcheryExtras.NAME,
        version = WitcheryExtras.VERSION,
        dependencies = "required-after:witchery;required-after:spongemixins;")
public class WitcheryExtras {

    public static final String MODID = "WitcheryExtras";
    public static final String NAME = "Witchery++";
    public static final String VERSION = WitcheryExtrasTags.VERSION;

    private static final Map<Integer, BasePluginWitchery> mPreInitEvents = new HashMap<>();
    private static final Map<Integer, BasePluginWitchery> mInitEvents = new HashMap<>();
    private static final Map<Integer, BasePluginWitchery> mPostInitEvents = new HashMap<>();

    @Mod.Instance(MODID)
    public static WitcheryExtras instance;

    @Mod.EventHandler
    public synchronized void preInit(final FMLPreInitializationEvent e) {
        for (BasePluginWitchery bwp : getMpreinitevents()) {
            bwp.preInit();
        }

        if (Loader.isModLoaded("Baubles|Expanded")) {
            BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.beltType, 1);
            BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.charmType, 1);
        }
    }

    @Mod.EventHandler
    public synchronized void init(final FMLInitializationEvent e) {
        new GarlicRecipes();
        OvenRecipes.generateDefaultOvenRecipes();
        for (BasePluginWitchery bwp : getMinitevents()) {
            bwp.init();
        }
    }

    @Mod.EventHandler
    public synchronized void postInit(final FMLPostInitializationEvent event) {
        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new TooltipHandler());
        }
        for (BasePluginWitchery bwp : getMpostinitevents()) {
            bwp.postInit();
        }
        PredictionHandler.adjustPredictions();
    }

    public static void log(int level, String text) {
        if (level <= 0) {
            Logger.INFO(text);
        } else if (level == 1) {
            Logger.WARNING(text);
        } else {
            Logger.ERROR(text);
        }
    }

    // Custom Content Loader
    public static synchronized Collection<BasePluginWitchery> getMpreinitevents() {
        return mPreInitEvents.values();
    }

    public static synchronized Collection<BasePluginWitchery> getMinitevents() {
        return mInitEvents.values();
    }

    public static synchronized Collection<BasePluginWitchery> getMpostinitevents() {
        return mPostInitEvents.values();
    }

    private static int mID_1 = 0;

    public static synchronized void addEventPreInit(BasePluginWitchery basePluginWitchery) {
        mPreInitEvents.put(mID_1++, basePluginWitchery);
    }

    private static int mID_2 = 0;

    public static synchronized void addEventInit(BasePluginWitchery minitevents) {
        mInitEvents.put(mID_2++, minitevents);
    }

    private static int mID_3 = 0;

    public static synchronized void addEventPostInit(BasePluginWitchery mpostinitevents) {
        mPostInitEvents.put(mID_3++, mpostinitevents);
    }
}

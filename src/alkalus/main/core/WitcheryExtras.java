package alkalus.main.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import alkalus.main.api.plugin.base.BasePluginWitchery;
import alkalus.main.core.proxy.Proxy_Common;
import alkalus.main.core.util.Logger;

@Mod(modid = WitcheryExtras.MODID, name = WitcheryExtras.NAME, version = WitcheryExtras.VERSION, dependencies = "required-after:Forge; after:witchery;")
public class WitcheryExtras {
	
	
	public static final String MODID = "WitcheryExtras";
	public static final String NAME = "Witches Cauldron";
	public static final String VERSION = "0.4";
	private static final Logger log4j = new Logger();

	private static final Map<Integer, BasePluginWitchery> mPreInitEvents;
	private static final Map<Integer, BasePluginWitchery> mInitEvents;
	private static final Map<Integer, BasePluginWitchery> mPostInitEvents;

	//Static Initialization block
	static {
		mPreInitEvents = new HashMap<Integer, BasePluginWitchery>();
		mInitEvents = new HashMap<Integer, BasePluginWitchery>();
		mPostInitEvents = new HashMap<Integer, BasePluginWitchery>();
	}
	
	@Mod.Instance(MODID)
	public static WitcheryExtras instance;
	
	@SidedProxy(clientSide = "alkalus.main.core.proxy.Proxy_Client", serverSide = "alkalus.main.core.proxy.Proxy_Server")
	public static Proxy_Common proxy;

	@Mod.EventHandler
	public synchronized void preInit(final FMLPreInitializationEvent e) {
    	log(0, "Loading "+NAME+" - v"+VERSION);
		proxy.preInit(e);
		for (BasePluginWitchery bwp : getMpreinitevents()) {
			bwp.PHASE_PRE();
		}
	}

	@Mod.EventHandler
	public synchronized void init(final FMLInitializationEvent e) {
		proxy.init(e);	
		for (BasePluginWitchery bwp : getMinitevents()) {
			bwp.PHASE_PRE();
		}	
	}	

	@Mod.EventHandler
	public synchronized void postInit(final FMLPostInitializationEvent e) {
		proxy.postInit(e);	
		for (BasePluginWitchery bwp : getMpostinitevents()) {
			bwp.PHASE_PRE();
		}
	}
	
	public static final void log(int level, String text) {
		if (level<=0) {
			log4j.INFO(text);
		}
		else if (level==1) {
			log4j.WARNING(text);
		}
		else {
			log4j.ERROR(text);
		}
	}

	
	//Custom Content Loader	
	public static synchronized final Collection<BasePluginWitchery> getMpreinitevents() {
		return mPreInitEvents.values();
	}

	public static synchronized final Collection<BasePluginWitchery> getMinitevents() {
		return mInitEvents.values();
	}

	public static synchronized final Collection<BasePluginWitchery> getMpostinitevents() {
		return mPostInitEvents.values();
	}

	private static int mID_1 = 0;
	public static synchronized final void addEventPreInit(BasePluginWitchery basePluginWitchery) {
		mPreInitEvents.put(mID_1++, basePluginWitchery);
	}

	private static int mID_2 = 0;
	public static synchronized final void addEventInit(BasePluginWitchery minitevents) {
		mInitEvents.put(mID_2++, minitevents);
	}

	private static int mID_3 = 0;
	public static synchronized final void addEventPostInit(BasePluginWitchery mpostinitevents) {
		mPostInitEvents.put(mID_3++, mpostinitevents);
	}
	
}

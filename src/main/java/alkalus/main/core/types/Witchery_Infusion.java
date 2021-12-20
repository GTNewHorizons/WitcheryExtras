package alkalus.main.core.types;

import java.util.ArrayList;

import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import alkalus.main.core.util.ReflectionUtils;

public class Witchery_Infusion {

	/**
	 * 
	 * @return - Infusion object.
	 */
	public static synchronized Infusion createNewInfusion() {
		return createNewInfusion(getLastUsedInfusionID()+1);
	}
	
	/**
	 * 
	 * @param mID - The Unique ID for this new Infusion
	 * @return - Infusion object.
	 */
	public static synchronized Infusion createNewInfusion(final int mID) {
		Infusion h = new Infusion(mID);
		return h != null ? h : null;
	}
	
	private static int mCachedLastRegistryValue;
	public static synchronized int getLastUsedInfusionID() {
		Object f = ReflectionUtils.getField(Infusion.Registry.instance(), "registry");
		ArrayList<Infusion> registry;
		try {
			if (f != null) {
				registry = (ArrayList<Infusion>) f;
				if (registry != null) {
					return mCachedLastRegistryValue = registry.size();
				}
			}
		}
		catch (Throwable t) {}
		return mCachedLastRegistryValue+1;		
	}
	
	/**
	 * 
	 * @param entity - The Player to find a Infusion for.
	 * @return
	 */
	public static synchronized Infusion getInfusionOnPlayer(EntityPlayer entity) {
		return Infusion.Registry.instance().get(entity);
	}
	/**
	 * 
	 * @param entity - The Infusion ID to use to find an Infusion.
	 * @return
	 */
	public static synchronized Infusion getInfusion(int infusionID) {
		return Infusion.Registry.instance().get(infusionID);	
	}	
	
}

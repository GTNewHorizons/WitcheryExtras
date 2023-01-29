package alkalus.main.core.types;

import java.util.ArrayList;

import net.minecraft.entity.EntityLiving;

import alkalus.main.core.util.ReflectionUtils;

import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;

public class Witchery_CreaturePower {

    /**
     *
     * @param creatureType - An Entity that Extends EntityLiving.
     * @return - CreaturePower object.
     */
    public static synchronized CreaturePower createNewCreaturePower(final Class<? extends EntityLiving> creatureType) {
        CreaturePower h = new CreaturePower(getLastUsedCreaturePowerID() + 1, creatureType);
        return h != null ? h : null;
    }

    /**
     *
     * @param mID          - The Unique ID for this new CreaturePower
     * @param creatureType - An Entity that Extends EntityLiving.
     * @return - CreaturePower object.
     */
    public static synchronized CreaturePower createNewCreaturePower(final int mID,
            final Class<? extends EntityLiving> creatureType) {
        CreaturePower h = new CreaturePower(mID, creatureType);
        return h != null ? h : null;
    }

    private static int mCachedLastRegistryValue;

    @SuppressWarnings("unchecked")
    public static synchronized int getLastUsedCreaturePowerID() {
        Object f = ReflectionUtils.getField(CreaturePower.Registry.instance(), "registry");
        ArrayList<CreaturePower> registry;
        try {
            if (f != null) {
                registry = (ArrayList<CreaturePower>) f;
                if (registry != null) {
                    return mCachedLastRegistryValue = registry.size();
                }
            }
        } catch (Throwable t) {}
        return mCachedLastRegistryValue + 1;
    }

    /**
     *
     * @param entity - The Entity to find a CreaturePower for.
     * @return
     */
    public static synchronized CreaturePower getCreaturePower(EntityLiving entity) {
        return CreaturePower.Registry.instance().get(entity);
    }

    /**
     *
     * @param entity - The Entity ID to use to find a CreaturePower.
     * @return
     */
    public static synchronized CreaturePower getCreaturePower(int entityID) {
        return CreaturePower.Registry.instance().get(entityID);
    }
}

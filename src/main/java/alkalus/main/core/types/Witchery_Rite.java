package alkalus.main.core.types;

import alkalus.main.core.util.ReflectionUtils;
import com.emoniph.witchery.ritual.RiteRegistry;
import com.emoniph.witchery.ritual.RiteRegistry.Ritual;
import java.util.ArrayList;

public class Witchery_Rite {

    private static int mCachedLastRegistryValue;

    public static synchronized int getLastUsedRiteID() {
        Object f = ReflectionUtils.getField(RiteRegistry.instance(), "rituals");
        ArrayList<Ritual> registry;
        try {
            if (f != null) {
                registry = (ArrayList<Ritual>) f;
                if (registry != null) {
                    return mCachedLastRegistryValue = registry.size();
                }
            }
        } catch (Throwable t) {
        }
        return mCachedLastRegistryValue + 1;
    }
}

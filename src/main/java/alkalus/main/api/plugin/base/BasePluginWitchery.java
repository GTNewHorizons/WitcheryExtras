package alkalus.main.api.plugin.base;

import alkalus.main.api.RecipeManager;
import alkalus.main.api.plugin.interfaces.WitcheryPlugin;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class should be extended, then the three phase methods should be {@link Override}n as required.
 * Constructor takes {@link LoadPhase}s, Feel free to leave {@link Override}n methods empty if they're not being use.
 * @author Alkalus
 *
 */
public abstract class BasePluginWitchery implements WitcheryPlugin {

    /** Just a simple {@link Enum} containing the three main Forge load phases.
     * @author Alkalus
     */
    public static enum LoadPhase {
        PREINIT,
        INIT,
        POSTINIT
    }

    /**
     *  Small Field used to maintain validation of the plugin.
     */
    private volatile boolean isValid = true;

    /** Dictates which stages your plugin will load in.
     * @param initStage - Multiple {@link LoadPhase}s. Maximum 3.
     */
    public BasePluginWitchery(Set<LoadPhase> phases) {
        this(phases, true);
    }

    /** Dictates which stages your plugin will load in.
     * @param initStage - Multiple {@link LoadPhase}s. Maximum 3.
     */
    public BasePluginWitchery(LoadPhase[] phases) {
        this(new HashSet<LoadPhase>(Arrays.asList(phases)), true);
    }

    /**
     * Internal Constructor for Plugins.
     * @param phases - Multiple {@link LoadPhase}s. Maximum 3.
     * @param internal - {@link boolean that dictates whether or not this was an internal call.
     * Please use false if you reflectively call this, for future compatibility.}
     */
    private BasePluginWitchery(Set<LoadPhase> phases, boolean internal) {
        if (phases == null || phases.size() <= 0 || phases.size() >= 4) {
            isValid = false;
        }
        if (isValid) {
            for (LoadPhase p : phases) {
                if (p == LoadPhase.PREINIT) {
                    RecipeManager.PluginManager.loadPluginForPreInit(this);
                } else if (p == LoadPhase.INIT) {
                    RecipeManager.PluginManager.loadPluginForInit(this);
                } else if (p == LoadPhase.POSTINIT) {
                    RecipeManager.PluginManager.loadPluginForPostInit(this);
                }
            }
        }
    }

    /** If you wish to utilize this phase, {@link Override} the behaviour. Be sure to enable this load phase during {@link WitcheryPlugin} construction.
     * @return - You should return True/False depending upon if the code completes as expected or not.
     */
    public abstract boolean preInit();

    /** If you wish to utilize this phase, {@link Override} the behaviour. Be sure to enable this load phase during {@link WitcheryPlugin} construction.
     * @return - You should return True/False depending upon if the code completes as expected or not.
     */
    public abstract boolean init();

    /** If you wish to utilize this phase, {@link Override} the behaviour. Be sure to enable this load phase during {@link WitcheryPlugin} construction.
     * @return - You should return True/False depending upon if the code completes as expected or not.
     */
    public abstract boolean postInit();
}

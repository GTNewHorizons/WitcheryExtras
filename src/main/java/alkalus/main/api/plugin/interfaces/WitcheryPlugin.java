package alkalus.main.api.plugin.interfaces;

public interface WitcheryPlugin {

    String getPluginName();

    boolean preInit();

    boolean init();

    boolean postInit();
}

package alkalus.main.api.plugin.interfaces;

public interface WitcheryPlugin {

	public abstract String getPluginName();
	
	public abstract boolean preInit();
	
	public abstract boolean init();
	
	public abstract boolean postInit();
	
}

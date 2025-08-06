package alkalus.main.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@SuppressWarnings("unused")
@IFMLLoadingPlugin.SortingIndex(10097)
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class WE_CORE_FMLLoadingPlugin implements IFMLLoadingPlugin {

    private static boolean isObf;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { WE_CORE_Handler.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return WE_CORE_Container.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isObf = (boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static boolean isIsObf() {
        return isObf;
    }
}

package alkalus.main.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.WitcheryExtras.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        for (Mixin mixin : Mixin.values()) {
            if (mixin.shouldLoad(loadedMods)) {
                mixins.add(mixin.mixinClass);
            }
        }
        return mixins;
    }
}

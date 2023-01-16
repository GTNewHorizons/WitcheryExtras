package alkalus.main.mixinplugin;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum Mixin {
    TileEntitySpinningWheelMixin("witchery.TileEntitySpinningWheelMixin", Side.BOTH, TargetedMod.WITCHERY),
    SpinningRecipeMixin("witchery.SpinningRecipeMixin", Side.BOTH, TargetedMod.WITCHERY),
    ShockwaveTaskMixin("witchery.ShockwaveTaskMixin", Side.BOTH, TargetedMod.WITCHERY);

    public final String mixinClass;
    public final List<TargetedMod> targetedMods;
    private final Side side;

    Mixin(String mixinClass, Side side, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = side;
    }

    Mixin(String mixinClass, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = Side.BOTH;
    }

    public boolean shouldLoad(Set<String> loadedMods) {
        return (side == Side.BOTH
                        || side == Side.SERVER && FMLLaunchHandler.side().isServer()
                        || side == Side.CLIENT && FMLLaunchHandler.side().isClient())
                && allModsloaded(loadedMods);
    }
    
    private boolean allModsloaded(Set<String> loadedMods) {
        if(targetedMods.isEmpty()) return true;
        
        for(TargetedMod target : targetedMods) {
            if(target == TargetedMod.VANILLA) continue;
            if(!loadedMods.isEmpty() && target.modId != null && !loadedMods.contains(target.modId)) return false;
        }
        
        return true;
    }
}

enum Side {
    BOTH,
    CLIENT,
    SERVER;
}

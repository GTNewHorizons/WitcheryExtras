package alkalus.main.mixins.late.minecraft;

import net.minecraft.world.biome.BiomeGenBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeGenBase.class)
public interface BiomeGenBaseAccessor {

    @Accessor("temperature")
    float witcheryextras$getTemperature();

    @Accessor("temperature")
    void witcheryextras$setTemperature(float value);

    @Accessor("rainfall")
    float witcheryextras$getRainfall();

    @Accessor("rainfall")
    void witcheryextras$setRainfall(float value);

    @Accessor("enableSnow")
    boolean witcheryextras$getEnableSnow();

    @Accessor("enableSnow")
    void witcheryextras$setEnableSnow(boolean value);

    @Accessor("enableRain")
    boolean witcheryextras$getEnableRain();

    @Accessor("enableRain")
    void witcheryextras$setEnableRain(boolean value);
}

package alkalus.main.core.biomebook;

import net.minecraft.world.biome.BiomeGenBase;

import alkalus.main.mixins.late.minecraft.BiomeGenBaseAccessor;
import alkalus.main.network.BiomeBookEntry;

/**
 * Builds client-side, non-registering {@link BiomeGenBase} stand-ins from server-sent display data so the Book of
 * Biomes GUI can render the server's biome list without touching the real biome registry. Kept out of the GUI mixin so
 * the concrete subclass is a normal class file (no anonymous class to relocate into the mixin target).
 */
public final class BiomeBookStandIn {

    private BiomeBookStandIn() {}

    /** Non-registering biome: passes {@code register = false} so {@code BiomeGenBase.biomeList} is never modified. */
    private static final class StandInBiome extends BiomeGenBase {

        StandInBiome(int id) {
            super(id, false);
        }
    }

    public static BiomeGenBase create(BiomeBookEntry entry) {
        BiomeGenBase biome = new StandInBiome(entry.id);
        biome.setBiomeName(entry.name);
        BiomeGenBaseAccessor acc = (BiomeGenBaseAccessor) biome;
        acc.witcheryextras$setTemperature(entry.temperature);
        acc.witcheryextras$setRainfall(entry.rainfall);
        acc.witcheryextras$setEnableSnow(entry.enableSnow);
        acc.witcheryextras$setEnableRain(entry.enableRain);
        return biome;
    }
}

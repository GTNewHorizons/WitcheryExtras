package alkalus.main.core.biomebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import com.emoniph.witchery.item.ItemBook;

import alkalus.main.mixins.late.minecraft.BiomeGenBaseAccessor;
import alkalus.main.network.BiomeBookEntry;

/**
 * Server-authoritative biome list for the Book of Biomes, grouped into the fixed {@link ItemBook#BIOME_TYPES} sections.
 * The concatenated order matches {@link ItemBook#getSelectedBiome(int)}, so page index N resolves to the same biome on
 * both sides. Cached for the server session because the biome dictionary is fixed after mod init.
 */
public final class BiomeBookData {

    private static volatile List<List<BiomeBookEntry>> cache;

    private BiomeBookData() {}

    public static List<List<BiomeBookEntry>> getSections() {
        List<List<BiomeBookEntry>> local = cache;
        if (local == null) {
            synchronized (BiomeBookData.class) {
                local = cache;
                if (local == null) {
                    local = build();
                    cache = local;
                }
            }
        }
        return local;
    }

    private static List<List<BiomeBookEntry>> build() {
        List<List<BiomeBookEntry>> sections = new ArrayList<>();
        for (BiomeDictionary.Type type : ItemBook.BIOME_TYPES) {
            List<BiomeBookEntry> section = new ArrayList<>();
            for (BiomeGenBase biome : BiomeDictionary.getBiomesForType(type)) {
                if (biome == null) {
                    continue;
                }
                BiomeGenBaseAccessor acc = (BiomeGenBaseAccessor) biome;
                section.add(
                        new BiomeBookEntry(
                                biome.biomeID,
                                biome.biomeName,
                                acc.witcheryextras$getTemperature(),
                                acc.witcheryextras$getRainfall(),
                                acc.witcheryextras$getEnableSnow(),
                                acc.witcheryextras$getEnableRain()));
            }
            sections.add(Collections.unmodifiableList(section));
        }
        return Collections.unmodifiableList(sections);
    }
}

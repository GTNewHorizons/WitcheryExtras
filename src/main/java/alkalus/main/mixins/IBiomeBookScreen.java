package alkalus.main.mixins;

import java.util.List;

import alkalus.main.network.BiomeBookEntry;

/** Implemented by the biome book GUI mixin so the client packet handler can repopulate it. */
public interface IBiomeBookScreen {

    void witcheryextras$populate(List<List<BiomeBookEntry>> sections);
}

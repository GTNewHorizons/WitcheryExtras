package alkalus.main.network;

/** Immutable display data for one biome page, sent server -> client. */
public final class BiomeBookEntry {

    public final int id;
    public final String name;
    public final float temperature;
    public final float rainfall;
    public final boolean enableSnow;
    public final boolean enableRain;

    public BiomeBookEntry(int id, String name, float temperature, float rainfall, boolean enableSnow,
            boolean enableRain) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.enableSnow = enableSnow;
        this.enableRain = enableRain;
    }
}

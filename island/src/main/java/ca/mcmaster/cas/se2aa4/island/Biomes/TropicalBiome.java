package ca.mcmaster.cas.se2aa4.island.Biomes;

import java.util.HashMap;
import java.util.Map;

public enum TropicalBiome {
    SAVANNA("savanna", "191,199,34"),
    DESERT("desert", "224,213,85"),
    LUSHFOREST("temp_forest", "12,173,36"),
    MANGROVE("mangrove", "98,124,61"),
    RAINFOREST("rainforest", "8,240,0");
    private static final Map<String, TropicalBiome> BIOME_NAME = new HashMap<>();
    private static final Map<String, TropicalBiome> BIOME_COLOR = new HashMap<>();
    static {
        for (TropicalBiome b : values()) {
            BIOME_NAME.put(b.biome_name, b);
            BIOME_COLOR.put(b.rgb_color, b);
        }
    }

    public final String biome_name;
    public final String rgb_color;

    TropicalBiome(String biome_name, String rgb_color) {
        this.biome_name = biome_name;
        this.rgb_color = rgb_color;
    }
    public static TropicalBiome valueOfBiomeName(String biome_name) {
        return BIOME_NAME.get(biome_name);
    }

    public static TropicalBiome valueOfRGBColor(String rgb_color) {
        return BIOME_COLOR.get(rgb_color);
    }
}

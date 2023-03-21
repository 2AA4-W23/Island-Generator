package ca.mcmaster.cas.se2aa4.island.Biomes;

import java.util.HashMap;
import java.util.Map;

public enum ArcticBiome {
    TUNDRA("tundra", "195,210,235"),
    TAIGA("taiga", "2,48,32"),
    BOREAL("boreal_forest", "0,100,0"),
    FOREST("deciduous_forest", "34,139,34"),
    STEPPES("steppes", "228,192,122");
    private static final Map<String, ArcticBiome> BIOME_NAME = new HashMap<>();
    private static final Map<String, ArcticBiome> BIOME_COLOR = new HashMap<>();
    static {
        for (ArcticBiome b : values()) {
            BIOME_NAME.put(b.biome_name, b);
            BIOME_COLOR.put(b.rgb_color, b);
        }
    }

    public final String biome_name;
    public final String rgb_color;

    ArcticBiome(String biome_name, String rgb_color) {
        this.biome_name = biome_name;
        this.rgb_color = rgb_color;
    }
    public static ArcticBiome valueOfBiomeName(String biome_name) {
        return BIOME_NAME.get(biome_name);
    }

    public static ArcticBiome valueOfRGBColor(String rgb_color) {
        return BIOME_COLOR.get(rgb_color);
    }

}

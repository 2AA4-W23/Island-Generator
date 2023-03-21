package ca.mcmaster.cas.se2aa4.island.Biomes;

import java.util.HashMap;
import java.util.Map;

public enum TemperateBiome {
    PRAIRIES("prairies", "156,240,101"),
    TEMPDESERT("desert", "252,134,23"),
    FOREST("forest", "45,196,65"),
    TEMPRAINFOREST("rainforest", "1,130,18"),
    WETLANDS("wetlands", "68,110,41");
    private static final Map<String, TemperateBiome> BIOME_NAME = new HashMap<>();
    private static final Map<String, TemperateBiome> BIOME_COLOR = new HashMap<>();
    static {
        for (TemperateBiome b : values()) {
            BIOME_NAME.put(b.biome_name, b);
            BIOME_COLOR.put(b.rgb_color, b);
        }
    }

    public final String biome_name;
    public final String rgb_color;

    TemperateBiome(String biome_name, String rgb_color) {
        this.biome_name = biome_name;
        this.rgb_color = rgb_color;
    }
    public static TemperateBiome valueOfBiomeName(String biome_name) {
        return BIOME_NAME.get(biome_name);
    }

    public static TemperateBiome valueOfRGBColor(String rgb_color) {
        return BIOME_COLOR.get(rgb_color);
    }
}

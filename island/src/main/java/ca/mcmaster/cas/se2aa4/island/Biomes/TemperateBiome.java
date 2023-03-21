package ca.mcmaster.cas.se2aa4.island.Biomes;

import java.util.HashMap;
import java.util.Map;

public enum TemperateBiome {
    SAVANNA("savanna", "151,168,62"),
    PRAIRIES("prairies", "75,219,59"),
    DESERT("desert", "224,213,85"),
    FOREST("forest", "25,112,17"),
    TUNDRA("tundra", "195,210,235"),
    RAINFOREST("rainforest", "23,250,2");
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

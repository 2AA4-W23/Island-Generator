package ca.mcmaster.cas.se2aa4.island.Biomes;


public class BaseBiomeProfile extends BiomeTemplate{
    enum Biome{
        SAVANNA("savanna", "186,188,114"),
        PRAIRIES("prairies", "75,219,59"),
        DESERT("desert", "224,213,85"),
        FOREST("forest", "25,112,17"),
        TUNDRA("tundra", "195,210,235"),
        RAINFOREST("rainforest", "78,138,62");
        public final String biome_name;
        public final String rgb_color;
        Biome(String biome_name, String rgb_color) {
            this.biome_name = biome_name;
            this.rgb_color = rgb_color;
        }
    }

    @Override
    public void setBiomeRange() {
        this.hmin = 0;
        this.hmax = 400;
        this.amin = -10;
        this.amax = 30;
    }

    @Override
    public String[] identifyBiome(double[] modifiedVals) {
        double altMod = modifiedVals[0];
        double humidMod = modifiedVals[1];
        Biome b;
        if (humidMod > 280 && altMod > 20) {
            b = Biome.RAINFOREST;
        } else if (humidMod > 100 && altMod > 15) {
            b = Biome.SAVANNA;
        } else if (humidMod < 100 && altMod > 15) {
            b = Biome.DESERT;
        } else if (humidMod > 150 && altMod < 20 && altMod > 0) {
            b = Biome.FOREST;
        } else if (humidMod < 150 && altMod < 20 && altMod > -5) {
            b = Biome.PRAIRIES;
        } else {
            b = Biome.TUNDRA;
        }
        return new String[]{b.biome_name, b.rgb_color};
    }
}

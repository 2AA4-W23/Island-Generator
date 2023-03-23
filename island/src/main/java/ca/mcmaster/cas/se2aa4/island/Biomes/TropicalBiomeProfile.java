package ca.mcmaster.cas.se2aa4.island.Biomes;


public class TropicalBiomeProfile extends BiomeTemplate{
    enum Biome{
        SAVANNA("savanna", "191,199,34"),
        DESERT("desert", "224,213,85"),
        LUSHFOREST("temp_forest", "12,173,36"),
        MANGROVE("mangrove", "98,124,61"),
        RAINFOREST("rainforest", "8,240,0");
        public final String biome_name;
        public final String rgb_color;
        Biome(String biome_name, String rgb_color) {
            this.biome_name = biome_name;
            this.rgb_color = rgb_color;
        }
    }

    @Override
    public String[] identifyBiome(double[] modifiedVals) {
        double altMod = modifiedVals[0];
        double humidMod = modifiedVals[1];
        Biome b;
        if(humidMod < 50){
            b = Biome.DESERT;
        } else if (altMod > 20 && humidMod > 280) {
            b = Biome.RAINFOREST;
        } else if (altMod > 15 && humidMod > 250) {
            b = Biome.LUSHFOREST;
        } else if (humidMod > 150) {
            b = Biome.MANGROVE;
        } else {
            b = Biome.SAVANNA;
        }
        return new String[]{b.biome_name, b.rgb_color};
    }

    @Override
    public void setBiomeRange() {
        this.hmin = 0;
        this.hmax = 450;
        this.amin = 15;
        this.amax = 30;
    }
}

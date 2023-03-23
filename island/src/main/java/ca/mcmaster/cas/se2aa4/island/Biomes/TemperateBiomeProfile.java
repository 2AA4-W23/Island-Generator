package ca.mcmaster.cas.se2aa4.island.Biomes;

public class TemperateBiomeProfile extends BiomeTemplate{
    enum Biome{
        PRAIRIES("prairies", "156,240,101"),
        TEMPDESERT("desert", "252,134,23"),
        FOREST("forest", "45,196,65"),
        TEMPRAINFOREST("rainforest", "1,130,18"),
        WETLANDS("wetlands", "68,110,41");
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
            b = Biome.TEMPDESERT;
        } else if (humidMod > 250) {
            b = Biome.TEMPRAINFOREST;
        } else if (10>altMod && altMod>5 && humidMod > 150) {
            b = Biome.WETLANDS;
        } else if (altMod > 10 && humidMod > 125) {
            b = Biome.FOREST;
        } else {
            b = Biome.PRAIRIES;
        }
        return new String[]{b.biome_name, b.rgb_color};
    }

    @Override
    public void setBiomeRange() {
        this.hmin = 0;
        this.hmax = 350;
        this.amin = 5;
        this.amax = 1;
    }
}

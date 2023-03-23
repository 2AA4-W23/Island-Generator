package ca.mcmaster.cas.se2aa4.island.Biomes;

public class ArcticBiomeProfile extends BiomeTemplate{
    enum Biome{
        TUNDRA("tundra", "195,210,235"),
        TAIGA("taiga", "2,48,32"),
        BOREAL("boreal_forest", "0,100,0"),
        FOREST("deciduous_forest", "34,139,34"),
        STEPPES("steppes", "228,192,122");
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
        if (altMod < -5){
            b = Biome.TUNDRA;
        } else if (humidMod < 100) {
            b = Biome.STEPPES;
        } else if (altMod > 5) {
            b = Biome.FOREST;
        } else if (altMod > 0) {
            b = Biome.BOREAL;
        } else {
            b = Biome.TAIGA;
        }
        return new String[]{b.biome_name, b.rgb_color};
    }

    @Override
    public void setBiomeRange() {
        this.hmin = 0;
        this.hmax = 250;
        this.amin = -10;
        this.amax = 10;
    }

}

package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

import java.util.List;

public interface BiomeProfile {
    String[] identifyBiome(double[] modifiedVals);
    void setBiomeRange();
    List<Structs.Polygon> addBiomes(List<Structs.Polygon> pList);
    Extractor tileTagEx = new TileTagExtractor();
    Extractor altEx = new AltitudeExtractor();
    Extractor humidEx = new HumidityExtractor();
}

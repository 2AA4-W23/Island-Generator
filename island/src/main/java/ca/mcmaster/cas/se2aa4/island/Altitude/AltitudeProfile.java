package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

import java.util.List;
import java.util.Random;

public interface AltitudeProfile {
    Extractor altEx = new AltitudeExtractor();
    Extractor tagEx = new TileTagExtractor();
    Random rng = new Random();
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist);
}

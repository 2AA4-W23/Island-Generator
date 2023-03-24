package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class RGBExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties) {
        return Extractor.getValue(properties, "rgb_color");
    }
}

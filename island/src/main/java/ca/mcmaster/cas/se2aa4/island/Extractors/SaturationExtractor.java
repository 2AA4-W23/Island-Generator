package ca.mcmaster.cas.se2aa4.island.Extractors;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class SaturationExtractor implements Extractor {

    @Override
    public String extractValues(List<Property> properties) {
        return Extractor.getValue(properties, "saturation");
    }
    
}

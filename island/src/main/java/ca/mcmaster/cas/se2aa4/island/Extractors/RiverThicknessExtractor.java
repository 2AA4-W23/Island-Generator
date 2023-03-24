package ca.mcmaster.cas.se2aa4.island.Extractors;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class RiverThicknessExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties) {
        return Extractor.getValue(properties, "thickness");
    }
    
}

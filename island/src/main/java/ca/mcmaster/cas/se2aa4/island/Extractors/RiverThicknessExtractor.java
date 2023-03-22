package ca.mcmaster.cas.se2aa4.island.Extractors;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class RiverThicknessExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties) {
        String thickness = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("thickness")) {
                thickness = p.getValue();
            }
        }
        if (thickness == null)
            return "null";
        return thickness;
    }
    
}

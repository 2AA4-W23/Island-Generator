package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class RGBExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties) {
        String color = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                color = p.getValue();
            }
        }
        if (color == null)
            return "null";
        return color;
    }
}

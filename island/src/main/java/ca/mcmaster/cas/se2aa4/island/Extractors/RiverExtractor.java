package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class RiverExtractor implements Extractor{
    @Override
    public String extractValues(List<Structs.Property> properties) {
        String riverNum = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("river_num")) {
                riverNum = p.getValue();
            }
        }
        if (riverNum == null)
            return "null";
        return riverNum;
    }
}

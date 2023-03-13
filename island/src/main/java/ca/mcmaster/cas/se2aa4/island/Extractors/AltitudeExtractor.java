package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class AltitudeExtractor implements Extractor{
    @Override
    public String extractValues(List<Structs.Property> properties) {
        String altitude = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("altitude")) {
                altitude = p.getValue();
            }
        }
        if (altitude == null)
            return "null";
        return altitude;
    }
}

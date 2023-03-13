package ca.mcmaster.cas.se2aa4.island.Extractors;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class HumidityExtractor implements Extractor {

    @Override
    public String extractValues(List<Property> properties) {
        String humidity = "null";
        for(Property p: properties) {
            if (p.getKey().equals("humidity")) {
                humidity = p.getValue();
            }
        }
        return humidity;
        
    }
    
}

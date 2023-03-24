package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface Extractor {
    static String getValue(List<Structs.Property> properties, String key) {
        String value = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals(key)) {
                value = p.getValue();
            }
        }
        if (value == null)
            return "null";
        return value;
    }

    String extractValues(List<Structs.Property> properties);
}

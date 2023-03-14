package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class EdgeTagExtractor implements Extractor{
    @Override
    public String extractValues(List<Structs.Property> properties) {
        String tag = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("seg_tag")) {
                tag = p.getValue();
            }
        }
        if (tag == null)
            return "null";
        return tag;
    }
}

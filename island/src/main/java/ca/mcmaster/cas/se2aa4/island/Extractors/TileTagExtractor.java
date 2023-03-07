package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class TileTagExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties) {
        String tag = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("tile_tag")) {
                tag = p.getValue();
            }
        }
        if (tag == null)
            return "null";
        return tag;
    }
}

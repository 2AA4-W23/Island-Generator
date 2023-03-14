package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class BiomeExtractor implements Extractor{
    @Override
    public String extractValues(List<Structs.Property> properties) {
        String biome = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("biome")) {
                biome = p.getValue();
            }
        }
        if (biome == null)
            return "null";
        return biome;
    }
}

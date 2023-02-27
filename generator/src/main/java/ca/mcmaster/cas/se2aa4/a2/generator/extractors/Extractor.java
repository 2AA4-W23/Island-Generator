package ca.mcmaster.cas.se2aa4.a2.generator.extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface Extractor {
    String extractValues(List<Structs.Property> properties1, List<Structs.Property> properties2, String key);
    String extractValues(List<List<Structs.Property>> properties, String key);

}

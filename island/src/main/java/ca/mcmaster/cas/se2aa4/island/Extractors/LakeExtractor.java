package ca.mcmaster.cas.se2aa4.island.Extractors;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class LakeExtractor implements Extractor{
    @Override
    public String extractValues(List<Structs.Property> properties) {
        String lakeNum = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("lake_num")) {
                lakeNum = p.getValue();
            }
        }
        if (lakeNum == null)
            return "null";
        return lakeNum;
    }
}

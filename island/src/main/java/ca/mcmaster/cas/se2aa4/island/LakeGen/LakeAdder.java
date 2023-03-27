package ca.mcmaster.cas.se2aa4.island.LakeGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.LakeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LakeAdder extends WaterBody{
    static Random rng = RandomNumber.getRandomInstance();
    private static final Extractor tileTagsEx = new TileTagExtractor();
    private static final Extractor lakeEx = new LakeExtractor();
    private static final Extractor altEx = new AltitudeExtractor();

    public static List<Structs.Polygon> addLakes(List<Structs.Polygon> newList, int numLakes){
        for(int i = 0; i < numLakes; i++){
            List<Structs.Polygon> tilesInLake = addBody(newList, rng);
            tilesInLake = addProperties(newList, tilesInLake, i + i, "lake", "lake_num");
            newList = addColor(newList, tilesInLake);
        }
        return newList;
    }

    public static List<Object> fixLakeAltitudes(int numLakes, List<Structs.Polygon> newList, List<Structs.Segment> sList, List<Structs.Vertex> vList){
        Map<Integer, List<Structs.Polygon>> lakes = new HashMap<>();
        Map<Integer, Integer> minAlt = new HashMap<>();
        
        for(Structs.Polygon p : newList) {
            String tag = tileTagsEx.extractValues(p.getPropertiesList());
            if(!tag.equals("lake")) continue;
            int lake_num = Integer.parseInt(lakeEx.extractValues(p.getPropertiesList()));
            if(!lakes.containsKey(lake_num)) {
                lakes.put(lake_num, new ArrayList<>());
                minAlt.put(lake_num, 100000);
            }
            lakes.get(lake_num).add(p);
            try {
                int alt = Integer.parseInt(altEx.extractValues(p.getPropertiesList()));
                minAlt.put(lake_num,  Math.min(minAlt.get(lake_num), alt));
            } catch (Exception e) {}
        }
        for(List<Structs.Polygon> lake : lakes.values()){
            for(Structs.Polygon p : lake){
                int lake_num = Integer.parseInt(lakeEx.extractValues(p.getPropertiesList()));
                Structs.Polygon newP = PropertyAdder.addProperty(p, "altitude", minAlt.get(lake_num) + "");
                newList.set(newList.indexOf(p), newP);
                for(Integer i : p.getSegmentIdxsList()){
                    Structs.Segment s = sList.get(i);
                    Structs.Vertex v1 = vList.get(s.getV1Idx());
                    Structs.Vertex v2 = vList.get(s.getV2Idx());
                    Structs.Vertex newV1 = PropertyAdder.addProperty(v1, "altitude", minAlt.get(lake_num) + "");
                    Structs.Vertex newV2 = PropertyAdder.addProperty(v2, "altitude", minAlt.get(lake_num) + "");
                    try{
                        vList.set(vList.indexOf(v1), newV1);
                        vList.set(vList.indexOf(v2), newV2);
                    } catch(Exception e) {}
                }
            }
        }
        List<Object> updatedLists = new ArrayList<>();
        updatedLists.add(newList);
        updatedLists.add(vList);
        return updatedLists;
    }
}
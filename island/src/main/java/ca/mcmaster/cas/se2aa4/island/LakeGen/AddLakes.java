package ca.mcmaster.cas.se2aa4.island.LakeGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.LakeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddLakes{
    static Random rng = RandomNumber.getRandomInstance();
    private static final Extractor tileTagsEx = new TileTagExtractor();
    private static final Extractor lakeEx = new LakeExtractor();
    private static final Extractor altEx = new AltitudeExtractor();

    public static List<Structs.Polygon> addLakes(List<Structs.Polygon> landTiles, int numLakes, List<Structs.Polygon> newList){
        Structs.Polygon initialTile;
        for(int i = 0; i < numLakes; i++){
            do initialTile = landTiles.get(rng.nextInt(landTiles.size()));
            while(!checkEligible(initialTile, newList));
            List<Structs.Polygon> tilesInLake = new ArrayList<>();
            tilesInLake.add(initialTile);
            landTiles.remove(initialTile);
            int lakeSize = rng.nextInt(5,15);
            int checks = 0;
            while(tilesInLake.size() < lakeSize && checks < 100){
                Structs.Polygon nextTile = tilesInLake.get(rng.nextInt(tilesInLake.size()));
                Structs.Polygon tileToAdd = newList.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
                checks++;
                if(!checkEligible(tileToAdd, newList)) continue;
                tilesInLake.add(tileToAdd);
                landTiles.remove(tileToAdd);
            }
            for(Structs.Polygon p : tilesInLake){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("10,100,255").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("lake").build();
                Structs.Property lakeNum = Structs.Property.newBuilder().setKey("lake_num").setValue(Integer.toString(i+1)).build();
                Structs.Polygon pLake = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).addProperties(lakeNum).build();
                int idx = newList.indexOf(p);
                if(idx != -1) {
                    newList.set(idx, pLake);
                }
            }
        }
        return newList;
    }

    public static List<Object> fixLakeAltitudes(int numLakes, List<Structs.Polygon> newList, List<Structs.Segment> sList, List<Structs.Vertex> vList){
        List<Structs.Polygon>[] lakes = new List[numLakes];
        int[] minAlt = new int[numLakes];
        for(int i = 0; i < numLakes; i++){
            lakes[i] = new ArrayList<>();
            minAlt[i] = 100000;
        }
        for(Structs.Polygon p : newList) {
            String tag = tileTagsEx.extractValues(p.getPropertiesList());
            if(!tag.equals("lake") || !tag.equals("endor_lake")) continue;
            int lake_num = Integer.parseInt(lakeEx.extractValues(p.getPropertiesList())) - 1;
            lakes[lake_num].add(p);
            try {
                int alt = Integer.parseInt(altEx.extractValues(p.getPropertiesList()));
                minAlt[lake_num] = Math.min(minAlt[lake_num], alt);
            } catch (Exception e) {}
        }
        int idx = 0;
        for(List<Structs.Polygon> lake : lakes){
            for(Structs.Polygon p : lake){
                Structs.Property alt = Structs.Property.newBuilder().setKey("altitude").setValue(minAlt[idx] + "").build();
                Structs.Polygon newP = Structs.Polygon.newBuilder(p).addProperties(alt).build();
                newList.set(newList.indexOf(p), newP);
                for(Integer i : p.getSegmentIdxsList()){
                    Structs.Segment s = sList.get(i);
                    Structs.Vertex v1 = vList.get(s.getV1Idx());
                    Structs.Vertex v2 = vList.get(s.getV2Idx());
                    Structs.Vertex newV1 = Structs.Vertex.newBuilder(v1).addProperties(alt).build();
                    Structs.Vertex newV2 = Structs.Vertex.newBuilder(v2).addProperties(alt).build();
                    try{
                        vList.set(vList.indexOf(v1), newV1);
                        vList.set(vList.indexOf(v2), newV2);
                    } catch(Exception e) {}
                }
            }
            idx++;
        }
        List<Object> updatedLists = new ArrayList<>();
        updatedLists.add(newList);
        updatedLists.add(vList);
        return updatedLists;
    }

    private static boolean checkEligible(Structs.Polygon tile, List<Structs.Polygon> newList) {
        if(tile == null) return false;
        for(int idx : tile.getNeighborIdxsList()){
            String tag = tileTagsEx.extractValues(newList.get(idx).getPropertiesList());
            if(!tag.equals("land")) return false;
        }
        return true;
    }
}
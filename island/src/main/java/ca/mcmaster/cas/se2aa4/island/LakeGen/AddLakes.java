package ca.mcmaster.cas.se2aa4.island.LakeGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddLakes {
    static Random rng = new Random();
    private static final Extractor tileTagsEx = new TileTagExtractor();

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
//            System.out.println("Size = " + tilesInLake.size());
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

    private static boolean checkEligible(Structs.Polygon tile, List<Structs.Polygon> newList) {
        if(tile == null) return false;
        for(int idx : tile.getNeighborIdxsList()){
            String tag = tileTagsEx.extractValues(newList.get(idx).getPropertiesList());
            if(!tag.equals("land")) return false;
        }
        return true;
    }
}
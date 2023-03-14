package ca.mcmaster.cas.se2aa4.island.AquiferGen;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddAquifers {
    static Random rng = new Random();
    private static final Extractor tileTagsEx = new TileTagExtractor();

    public static List<Structs.Polygon> addAquifers(List<Structs.Polygon> landTiles, int numAqs, List<Structs.Polygon> newList){
        Structs.Polygon initialTile;
        for(int i = 0; i < numAqs; i++){
            do initialTile = landTiles.get(rng.nextInt(landTiles.size()));
            while(!checkEligible(initialTile, newList));
            List<Structs.Polygon> tilesInAq = new ArrayList<>();
            tilesInAq.add(initialTile);
            landTiles.remove(initialTile);
            int aqSize = rng.nextInt(5,15);
            int checks = 0;
            while(tilesInAq.size() < aqSize && checks < 100){
                Structs.Polygon nextTile = tilesInAq.get(rng.nextInt(tilesInAq.size()));
                Structs.Polygon tileToAdd = newList.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
                checks++;
                if(!checkEligible(tileToAdd, newList)) continue;
                tilesInAq.add(tileToAdd);
                landTiles.remove(tileToAdd);
            }

            for(Structs.Polygon p : tilesInAq){
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("aquifer").build();
                Structs.Property aqNum = Structs.Property.newBuilder().setKey("aq_num").setValue(Integer.toString(i+1)).build();
                Structs.Polygon pLake = Structs.Polygon.newBuilder(p).addProperties(tileTag).addProperties(aqNum).build();
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
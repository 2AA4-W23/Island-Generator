package ca.mcmaster.cas.se2aa4.island.LakeGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

public abstract class WaterBody {

    protected static TileTagExtractor tileTagsEx = new TileTagExtractor();

    private static boolean checkEligible(Structs.Polygon tile, List<Structs.Polygon> tiles){
        if(tile == null) return false;
        for(int idx : tile.getNeighborIdxsList()){
            String tag = tileTagsEx.extractValues(tiles.get(idx).getPropertiesList());
            if(!tag.equals("land")) return false;
        }
        return true;
    }

    protected static List<Structs.Polygon> addProperties(List<Structs.Polygon> tiles, List<Structs.Polygon> tilesInBody, int num, String tileTag, String numKey){
        for(Structs.Polygon p : tilesInBody){
            Structs.Polygon pLake = PropertyAdder.addProperty(p, "tile_tag", tileTag);
            pLake = PropertyAdder.addProperty(pLake, numKey, num + "");
            int idx = tiles.indexOf(p);
            if(idx != -1) {
                tiles.set(idx, pLake);
            }
        }
        return tiles;
    }

    protected static List<Structs.Polygon> addColor(List<Structs.Polygon> tiles, List<Structs.Polygon> tilesInBody){
        for(Structs.Polygon p : tilesInBody){
            if(!tileTagsEx.extractValues(p.getPropertiesList()).equals("lake")) continue;
            Structs.Polygon pLake = PropertyAdder.addProperty(p, "rgb_color", "10,100,255");
            int idx = tiles.indexOf(p);
            if(idx != -1) {
                tiles.set(idx, pLake);
            }
        }
        return tiles;
    }
    
    protected static List<Structs.Polygon> addBody(List<Structs.Polygon> newList, Random rng){
        Structs.Polygon initialTile;
        do initialTile = newList.get(rng.nextInt(newList.size()));
        while(!WaterBody.checkEligible(initialTile, newList));
        List<Structs.Polygon> tilesInLake = new ArrayList<>();
        tilesInLake.add(initialTile);
        int lakeSize = rng.nextInt(5,15);
        int checks = 0;
        while(tilesInLake.size() < lakeSize && checks < 100){
            Structs.Polygon nextTile = tilesInLake.get(rng.nextInt(tilesInLake.size()));
            Structs.Polygon tileToAdd = newList.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
            checks++;
            if(!checkEligible(tileToAdd, newList)) continue;
            tilesInLake.add(tileToAdd);
        }
        return tilesInLake;
    }
}

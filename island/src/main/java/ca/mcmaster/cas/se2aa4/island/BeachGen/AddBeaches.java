package ca.mcmaster.cas.se2aa4.island.BeachGen;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

public class AddBeaches {
    
    private static Extractor tileTagsEx = new TileTagExtractor();

    public static List<Structs.Polygon> addBeaches(List<Structs.Polygon> landTiles, List<Polygon> newList){
        List<Structs.Polygon> l2 = new ArrayList<>();
//        System.out.println("Entered");
//        System.out.println(landTiles.size());
        for(Structs.Polygon p: landTiles){
            for (int i = 0; i < p.getNeighborIdxsCount(); i++) {
                Structs.Polygon x = newList.get(p.getNeighborIdxs(i));
                if((tileTagsEx.extractValues(x.getPropertiesList()).equals("ocean")) && tileTagsEx.extractValues(p.getPropertiesList()).equals("land")){
                    l2.add(p);
                }
            }
        }
        landTiles.removeAll(l2);
//        System.out.println(landTiles.size());
        for(Structs.Polygon p : l2) {
//            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("194,178,128").build();
            Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("beach").build();
            Structs.Polygon beachTile = Structs.Polygon.newBuilder(p).addProperties(tileTag).build();
            int index = newList.indexOf(p);
            if(index != -1) newList.set(index, beachTile);
        }
        return newList;
    }
}

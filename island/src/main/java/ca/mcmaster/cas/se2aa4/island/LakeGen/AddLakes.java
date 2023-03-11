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

    public static List<Structs.Polygon> addLakes(List<Structs.Polygon> landTiles, int numLakes, List <Structs.Polygon> newList){
        List<Structs.Polygon> l2 = new ArrayList<>();

        int index = 0;
        System.out.println(landTiles.size());
        for(Structs.Polygon p: landTiles){
            for (int i = 0; i < p.getNeighborIdxsCount(); i++) {
                Structs.Polygon x = newList.get(p.getNeighborIdxs(i));
                if((tileTagsEx.extractValues(x.getPropertiesList()).equals("ocean")) && tileTagsEx.extractValues(p.getPropertiesList()).equals("land")){
                    l2.add(p);
                }
            }
            index ++;
        }
        landTiles.removeAll(l2);
        System.out.println(landTiles.size());

        for(int i = 0; i < numLakes; i++){
            List<Structs.Polygon> tilesInLake = new ArrayList<>();
            tilesInLake.add(landTiles.get(rng.nextInt(landTiles.size())));
            landTiles.remove(tilesInLake.get(0));
            int lakeSize = rng.nextInt(10,15);
            int checks = 0;
            while(tilesInLake.size() < lakeSize && checks < 50){
                checks++;
                Structs.Polygon nextTile = tilesInLake.get(rng.nextInt(tilesInLake.size()));
                boolean flag = false;
                for(int idx : nextTile.getNeighborIdxsList()){
                    String tag = tileTagsEx.extractValues(newList.get(idx).getPropertiesList());
                    if(!tag.equals("land")){
                        flag = true;
                        break;
                    }
                }
                if(flag) continue;
                Structs.Polygon tileToAdd = newList.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
                tilesInLake.add(tileToAdd);
                landTiles.remove(tileToAdd);
            }

            for(Structs.Polygon p : tilesInLake){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("10,100,255").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("lake").build();
                Structs.Polygon pLake = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                int idx = newList.indexOf(p);
                if(idx != -1) newList.set(idx, pLake);
            }
        }
        return newList;
    }
}

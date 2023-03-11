package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RGBExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IslandGenerator {
    private static final Extractor rgbEx = new RGBExtractor();
    private static final Extractor tileTagsEx = new TileTagExtractor();

    public static Structs.Mesh Generate(Configuration config){
        Structs.Mesh mesh = config.inputMesh;
        Shape islandShape = config.shapeObj;
        int numLakes = config.numLakes;
        islandShape.create();

        List<Structs.Polygon> pList = mesh.getPolygonsList();
        List<Structs.Vertex> vList = mesh.getVerticesList();
        List<Structs.Polygon> landTiles = new ArrayList<>();
        List<Structs.Polygon> newList = new ArrayList<>();

        Random rng = new Random();

        for (Structs.Polygon p: pList) {

            int indexc = p.getCentroidIdx();
            Structs.Vertex v = vList.get(indexc);
            if(!islandShape.contains(v.getX(), v.getY())){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("ocean").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
            }  else {
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("144,238,144").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("land").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
                landTiles.add(pColoredModify);
            }
        }

        int index= 0;
        for(Structs.Polygon p: newList){
            for (int i = 0; i < p.getNeighborIdxsCount(); i++) {
                Structs.Polygon x = newList.get(p.getNeighborIdxs(i));
                if(tileTagsEx.extractValues(x.getPropertiesList()).equals("ocean") && tileTagsEx.extractValues(p.getPropertiesList()).equals("land")){
                    Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("194,178,128").build();
                    Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("beach").build();
                    Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                    newList.set(index, pColoredModify);
                }
            }
            index++;
        }

        for(int i = 0; i < numLakes; i++){
            List<Structs.Polygon> tilesInLake = new ArrayList<>();
            tilesInLake.add(landTiles.get(rng.nextInt(landTiles.size())));
            landTiles.remove(tilesInLake.get(0));
            int lakeSize = rng.nextInt(1,10);
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

        System.out.println(pList.size() == newList.size());
        return Structs.Mesh.newBuilder().addAllPolygons(newList).addAllSegments(mesh.getSegmentsList()).addAllVertices(mesh.getVerticesList()).build();
    }
}

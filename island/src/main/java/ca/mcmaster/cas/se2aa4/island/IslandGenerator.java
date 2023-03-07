package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RGBExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Shape.Circle;
import ca.mcmaster.cas.se2aa4.island.Shape.Rectangle;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class IslandGenerator {
    private static final Extractor rgbEx = new RGBExtractor();
    private static final Extractor tileTagsEx = new TileTagExtractor();
    public static Structs.Mesh Generate(Structs.Mesh mesh, String shape){
        List<Structs.Polygon> pList = mesh.getPolygonsList();
        List<Structs.Vertex> vList = mesh.getVerticesList();

        List<Structs.Polygon> newList = new ArrayList<>();
        Shape islandShape;
        if(shape.equals("rectangle")){
            islandShape = new Rectangle();
            islandShape.create();
        } else if (shape.equals("circle")){
            islandShape = new Circle();
            islandShape.create();
        } else {
            islandShape = new Circle();
            islandShape.create();
        }


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
        System.out.println(pList.size()== newList.size());
        return Structs.Mesh.newBuilder().addAllPolygons(newList).addAllSegments(mesh.getSegmentsList()).addAllVertices(mesh.getVerticesList()).build();
    }

}

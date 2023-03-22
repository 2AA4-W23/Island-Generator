package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Lagoon {
    public Mesh LagoonTerrain(Mesh mesh){
        List<Polygon> pList = mesh.getPolygonsList();
        List<Vertex> vList = mesh.getVerticesList();

        List<Polygon> newList = new ArrayList<>();
        int index = 0;
        Ellipse2D outer = new Ellipse2D.Double(50,50,400,400);
        Ellipse2D lake = new Ellipse2D.Double(150,150,200,200);

//        for (Polygon p: pList){
//            System.out.println(extractColor(p.getPropertiesList()));
//        }
        for (Polygon p: pList) {
            int indexc = p.getCentroidIdx();
            Vertex v = vList.get(indexc);
            if(!outer.contains(v.getX(), v.getY())){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("ocean").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
            } else if(lake.contains(v.getX(), v.getY())) {
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("70,130,180").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("lagoon").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
            } else {
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("144,238,144").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("land").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
            }
        }

        for(Polygon p: newList){
            for (int i = 0; i < p.getNeighborIdxsCount(); i++) {
                Polygon x = newList.get(p.getNeighborIdxs(i));
                if((extractTag(x.getPropertiesList()).equals("ocean") || extractTag(x.getPropertiesList()).equals("lagoon")) && extractTag(p.getPropertiesList()).equals("land")){
                    Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("194,178,128").build();
                    Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("beach").build();
                    Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                    newList.set(index, pColoredModify);
                }
            }
            index++;
        }
//        System.out.println("After Modification");
//        for (Polygon p: newList){
//            System.out.println(extractColor(p.getPropertiesList()));
//        }
//        System.out.println(pList.size()== newList.size());
        return Structs.Mesh.newBuilder().addAllPolygons(newList).addAllSegments(mesh.getSegmentsList()).addAllVertices(mesh.getVerticesList()).build();
    }
    public String extractColor(List<Property> properties) {
        String color = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                color = p.getValue();
            }
        }
        if (color == null)
            return "null";
//        int red = Integer.parseInt(raw[0]);
//        int green = Integer.parseInt(raw[1]);
//        int blue = Integer.parseInt(raw[2]);

       return color;
    }
    public String extractTag(List<Property> properties) {
        String tag = null;
        for(Property p: properties) {
            if (p.getKey().equals("tile_tag")) {
                tag = p.getValue();
            }
        }
        if (tag == null)
            return "null";
//        String[] raw = color.split(",");
////        int red = Integer.parseInt(raw[0]);
////        int green = Integer.parseInt(raw[1]);
////        int blue = Integer.parseInt(raw[2]);

        return tag;
    }
}

package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Lagoon {
    public Mesh LagoonTerrain(Mesh mesh){
        List<Polygon> pList = mesh.getPolygonsList();
        List<Polygon> newList = new ArrayList<>();
        int index = 0;
        for (Polygon p: pList){
            System.out.println(extractColor(p.getPropertiesList()));
        }
        for (Polygon p: pList) {
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
            Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).build();
            newList.add(pColoredModify);
            index ++;
        }
        System.out.println("After Modification");
        for (Polygon p: newList){
            System.out.println(extractColor(p.getPropertiesList()));
        }
        return Structs.Mesh.newBuilder().addAllPolygons(newList).addAllSegments(mesh.getSegmentsList()).addAllVertices(mesh.getVerticesList()).build();
    }
    private String extractColor(List<Property> properties) {
        String color = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                color = p.getValue();
            }
        }
        if (color == null)
            return "null";

        String[] raw = color.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);

       return color;
    }
}

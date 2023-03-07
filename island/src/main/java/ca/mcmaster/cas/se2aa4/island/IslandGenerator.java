package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class IslandGenerator {
    public static Structs.Mesh Generate(Structs.Mesh mesh, String shape){
        List<Structs.Polygon> pList = mesh.getPolygonsList();
        List<Structs.Vertex> vList = mesh.getVerticesList();

        List<Structs.Polygon> newList = new ArrayList<>();
        if(shape.equals("circle")){

        } else {

        }
        return mesh;
    }

}

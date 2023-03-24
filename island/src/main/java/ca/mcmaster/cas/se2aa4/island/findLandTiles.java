package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class findLandTiles {
    public static List<List<Structs.Polygon>> seperateTiles(Shape shape, List<Structs.Polygon> pList, List<Structs.Vertex> vList){
        List<Structs.Polygon> landTiles = new ArrayList<>();
        List<Structs.Polygon> newList = new ArrayList<>();
        for (Structs.Polygon p: pList) {
            int indexc = p.getCentroidIdx();
            Structs.Vertex v = vList.get(indexc);
            if(!shape.contains(v.getX(), v.getY())){
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "tile_tag", "ocean");
                pColoredModify = PropertyAdder.addProperty(pColoredModify, "rgb_color", "43,101,236");
                newList.add(pColoredModify);
            }  else {
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "tile_tag", "land");
                pColoredModify = PropertyAdder.addProperty(pColoredModify, "rgb_color", "144,238,144");
                newList.add(pColoredModify);
                landTiles.add(pColoredModify);
            }
        }
        return Arrays.asList(newList, landTiles);
    }
}

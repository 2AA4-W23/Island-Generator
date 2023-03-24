package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomAltitude extends AltitudeTemplate{

    @Override
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist) {
        List<Structs.Polygon> pModList = new ArrayList<>();
        List <Structs.Vertex> vModList = new ArrayList<>(vlist);
        for(Structs.Polygon p:plist){
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue("0").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(altTag).build();
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = findVerticesIndex(p, slist);
                vModList = setVertexAltitude(vModList, vInts);
                int average = averageAltitude(vModList, vInts);
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p,"altitude",Integer.toString(average));
                pModList.add(pColoredModify);
            }
        }
        return createAnswerList(pModList, slist, vModList);
    }
}

package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MountainAltitude extends AltitudeTemplate {
    private double distance;
    @Override
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist){
        List<Structs.Polygon> pModList = new ArrayList<>();
        List <Structs.Vertex> vModList = new ArrayList<>(vlist);
        Mountain mountain = new Mountain();
        List<Line2D> lines = mountain.generateMountainRange();
        for(Structs.Polygon p:plist){
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "altitude","0");
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = findVerticesIndex(p, slist);
                this.distance = mountain.minDistanceMountainRange(p, vModList, lines);
                vModList = setVertexAltitude(vModList, vInts);

                int average = averageAltitude(vModList, vInts);
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "altitude",Integer.toString(average));
                pModList.add(pColoredModify);
            }
        }
        return createAnswerList(pModList, slist, vModList);
    }
    @Override
    protected List<Structs.Vertex> setVertexAltitude(List<Structs.Vertex> vModList, Set<Integer> vInts){
        int altVal;
        for(Integer i: vInts){
            Structs.Vertex v = vModList.get(i);
            if(altEx.extractValues(v.getPropertiesList()).equals("null")){
                altVal =  1000 - (int)distance;
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(altVal)).build();
                Structs.Vertex mV = Structs.Vertex.newBuilder(v).addProperties(altTag).build();
                vModList.set(i, mV);
            }
        }
        return vModList;
    }

}

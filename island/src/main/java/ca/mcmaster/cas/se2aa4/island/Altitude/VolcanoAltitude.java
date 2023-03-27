package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VolcanoAltitude extends AltitudeTemplate{
    private double distance;
    @Override
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> sList, List<Structs.Vertex> vlist) {
        List<Structs.Polygon> pModList = new ArrayList<>();
        List <Structs.Vertex> vModList = new ArrayList<>(vlist);
        double[] centerCoordinates = findIslandCenter(plist, vlist);
        for(Structs.Polygon p:plist){
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "altitude","0");
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = findVerticesIndex(p, sList);
                int centroidIdx = p.getCentroidIdx();
                Structs.Vertex centroid = vModList.get(centroidIdx);
                this.distance = distance(centroid.getX(), centroid.getY(), centerCoordinates[0], centerCoordinates[1]);
                vModList = setVertexAltitude(vModList, vInts);
                int average = averageAltitude(vModList, vInts);
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "altitude",Integer.toString(average));
                pModList.add(pColoredModify);
            }
        }
        return createAnswerList(pModList, sList, vModList);
    }
    private double[] findIslandCenter(List<Structs.Polygon> plist, List<Structs.Vertex> vlist){
        double minx = 500;
        double miny = 500;
        double maxx = 0;
        double maxy = 0;

        for(Structs.Polygon p :plist){
            if(!tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                int centroidIdx = p.getCentroidIdx();
                Structs.Vertex centroid = vlist.get(centroidIdx);
                minx = Math.min(centroid.getX(), minx);
                maxx = Math.max(centroid.getX(), maxx);
                maxy = Math.max(centroid.getY(), maxy);
                miny = Math.min(centroid.getY(), miny);
            }
        }

        double centerX = (maxx+minx)/2;
        double centerY = (maxy+miny)/2;
        return new double[]{centerX, centerY};
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

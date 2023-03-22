package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VolcanoAltitude implements AltitudeProfile{
    @Override
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> sList, List<Structs.Vertex> vlist) {
        List<Structs.Polygon> pModList = new ArrayList<>();
        List <Structs.Vertex> vList = new ArrayList<>(vlist);

        double minx = 500;
        double miny = 500;
        double maxx = 0;
        double maxy = 0;


        for(Structs.Polygon p :plist){
            if(!tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                int centroidIdx = p.getCentroidIdx();
                Structs.Vertex centroid = vList.get(centroidIdx);
                minx = Math.min(centroid.getX(), minx);
                maxx = Math.max(centroid.getX(), maxx);
                maxy = Math.max(centroid.getY(), maxy);
                miny = Math.min(centroid.getY(), miny);
            }
        }



        double centerX = (maxx+minx)/2;
        double centerY = (maxy+miny)/2;
        for(Structs.Polygon p:plist){
            int sum = 0;
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue("0").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(altTag).build();
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = new HashSet<>();
                for(int i = 0; i < p.getSegmentIdxsCount(); i++){
                    Structs.Segment seg = sList.get(p.getSegmentIdxs(i));
                    vInts.add(seg.getV1Idx());
                    vInts.add(seg.getV2Idx());
                }
                int centroidIdx = p.getCentroidIdx();
                Structs.Vertex centroid = vList.get(centroidIdx);
                double distance = distance(centroid.getX(), centroid.getY(), centerX, centerY);
                int altVal;
                for(Integer i: vInts){
                    Structs.Vertex v = vList.get(i);
                    if(altEx.extractValues(v.getPropertiesList()).equals("null")){
                        if(distance > 200){
                            altVal = rng.nextInt(0,1000);
                        } else if (distance > 150) {
                            altVal = rng.nextInt(1000,2000);
                        } else if (distance > 100) {
                            altVal = rng.nextInt(2000,3000);
                        } else if (distance > 50) {
                            altVal = rng.nextInt(3000,4000);
                        } else {
                            altVal = rng.nextInt(4000,5000);
                        }
                        Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(altVal)).build();
                        Structs.Vertex mV = Structs.Vertex.newBuilder(v).addProperties(altTag).build();
                        vList.set(i, mV);
                    }
                }
                for(Integer i: vInts){
                    sum += Integer.valueOf(altEx.extractValues(vList.get(i).getPropertiesList()));
                }
                int average = sum/vInts.size();
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(average)).build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(altTag).build();
                pModList.add(pColoredModify);
            }
        }
        List<Object> ansList = new ArrayList<>();
        VertexPolygonConnections vpc = new VertexPolygonConnections(pModList, sList, vList);
        ansList.add(pModList);
        ansList.add(sList);
        ansList.add(smoothenVertices(pModList, vList, vpc));
        return ansList;
    }
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}

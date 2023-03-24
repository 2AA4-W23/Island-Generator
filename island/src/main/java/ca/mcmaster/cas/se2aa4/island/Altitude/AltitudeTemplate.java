package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AltitudeTemplate implements AltitudeProfile{
    protected final int maxAlt = 10000;
    protected Set<Integer> findVerticesIndex(Structs.Polygon p, List<Structs.Segment> sList){
        Set<Integer> vInts = new HashSet<>();
        for(int i = 0; i < p.getSegmentIdxsCount(); i++){
            Structs.Segment seg = sList.get(p.getSegmentIdxs(i));
            vInts.add(seg.getV1Idx());
            vInts.add(seg.getV2Idx());
        }
        return vInts;
    }
    protected List<Structs.Vertex> setVertexAltitude(List<Structs.Vertex> vList, Set<Integer> vInts){
        for(Integer i: vInts){
            Structs.Vertex v = vList.get(i);
            if(altEx.extractValues(v.getPropertiesList()).equals("null")){
                int altVal = rng.nextInt(0,5000);
                Structs.Vertex mV = PropertyAdder.addProperty(v,"altitude",Integer.toString(altVal));;
                vList.set(i, mV);
            }
        }
        return vList;
    }
    protected double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
    protected int averageAltitude(List<Structs.Vertex> vList, Set<Integer> vInts){
        int sum = 0;
        for(Integer i: vInts){
            sum += Integer.parseInt(altEx.extractValues(vList.get(i).getPropertiesList()));
        }
        int average = sum/vInts.size();
        return average;
    }
    protected List<Object> createAnswerList(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist){
        List<Object> ansList = new ArrayList<>();
        VertexPolygonConnections vpc = new VertexPolygonConnections(plist, slist, vlist);
        ansList.add(plist);
        ansList.add(slist);
        ansList.add(smoothenVertices(plist, vlist, vpc));
        return ansList;
    }
    protected List<Structs.Vertex> smoothenVertices(List<Structs.Polygon> plist, List<Structs.Vertex> vlist, VertexPolygonConnections vpc){
        for(Structs.Vertex v : vlist){
            Set<Integer> connections = vpc.getConnections(v, vlist);
            if(connections == null) continue;
            int averageAlt = 0;
            for(Integer i : connections) {
                String alt = altEx.extractValues(plist.get(i).getPropertiesList());
                if(alt.equals("0")){
                    averageAlt = 0;
                    break;
                }
                try {
                    averageAlt += Integer.parseInt(alt) / connections.size();
                } catch(Exception e) {
                    averageAlt = 0;
                    break;
                }
            }
            Structs.Property altitude = Structs.Property.newBuilder().setKey("altitude").setValue(averageAlt + "").build();
            Structs.Vertex smoothV = Structs.Vertex.newBuilder(v).addProperties(altitude).build();
            vlist.set(vlist.indexOf(v), smoothV);
        }
        return vlist;
    }
}

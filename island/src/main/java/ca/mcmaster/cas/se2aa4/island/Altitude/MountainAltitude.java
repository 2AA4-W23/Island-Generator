package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MountainAltitude implements AltitudeProfile {
    @Override
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist) {
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


        CubicCurve2D c = new CubicCurve2D.Double();
        c.setCurve(100,300, 200,150,250,400, 300,100);
        List<Line2D> lines = new ArrayList<>();

        int numLines = rng.nextInt(1,5);

        for (int i = 0; i < numLines; i++) {
            int x1 = rng.nextInt(0,500);
            int y1 = rng.nextInt(0,500);
            int x2 = rng.nextInt(0,500);
            int y2 = rng.nextInt(0,500);

            Line2D d = new Line2D.Double();
            d.setLine(x1,y1,x2, y2);
            System.out.print(x1);
            System.out.print(y1);
            System.out.print(x2);
            System.out.print(y2);
            System.out.println("");
            lines.add(d);
        }


        System.out.println(maxx + " " + minx);
        System.out.println(maxy + " " + miny);

        double centerX = (maxx+minx)/2;
        double centerY = (maxy+miny)/2;

        System.out.println(centerX + " " + centerY);

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
                    Structs.Segment seg = slist.get(p.getSegmentIdxs(i));
                    vInts.add(seg.getV1Idx());
                    vInts.add(seg.getV2Idx());
                }
                int centroidIdx = p.getCentroidIdx();
                Structs.Vertex centroid = vList.get(centroidIdx);
                double distance = 1000000;
                for(Line2D d: lines){
                    distance = Math.min(distance, d.ptLineDist(centroid.getX(),centroid.getY()));
                }


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
        VertexPolygonConnections vpc = new VertexPolygonConnections(pModList, slist, vList);
        ansList.add(pModList);
        ansList.add(slist);
        ansList.add(smoothenVertices(pModList, vList, vpc));
        return ansList;

    }
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}

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
        List<Line2D> lines = generateMountainRange();
        for(Structs.Polygon p:plist){
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Polygon pColoredModify = PropertyAdder.addProperty(p, "altitude","0");
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = findVerticesIndex(p, slist);
                this.distance = minDistanceMountainRange(p, vModList, lines);
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
                if(this.distance > 200){
                    altVal = rng.nextInt(0,1000);
                } else if (this.distance > 150) {
                    altVal = rng.nextInt(1000,2000);
                } else if (this.distance > 100) {
                    altVal = rng.nextInt(2000,3000);
                } else if (this.distance > 50) {
                    altVal = rng.nextInt(3000,4000);
                } else {
                    altVal = rng.nextInt(4000,5000);
                }
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(altVal)).build();
                Structs.Vertex mV = Structs.Vertex.newBuilder(v).addProperties(altTag).build();
                vModList.set(i, mV);
            }
        }
        return vModList;
    }
    private List <Line2D> generateMountainRange(){
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
        return lines;
    }
    private double minDistanceMountainRange(Structs.Polygon p, List<Structs.Vertex> vModList, List<Line2D> lines){
        int centroidIdx = p.getCentroidIdx();
        Structs.Vertex centroid = vModList.get(centroidIdx);
        double distance = 1000000;
        for(Line2D d: lines){
            distance = Math.min(distance, d.ptLineDist(centroid.getX(),centroid.getY()));
        }
        return distance;
    }
}

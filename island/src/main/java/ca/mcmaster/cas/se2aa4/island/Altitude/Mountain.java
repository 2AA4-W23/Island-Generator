package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mountain {
    Random rng = RandomNumber.getRandomInstance();
    public List<Line2D> generateMountainRange(){
        List<Line2D> lines = new ArrayList<>();

        int numLines = rng.nextInt(1,5);

        for (int i = 0; i < numLines; i++) {
            int x1 = rng.nextInt(0,500);
            int y1 = rng.nextInt(0,500);
            int x2 = rng.nextInt(0,500);
            int y2 = rng.nextInt(0,500);
            Line2D d = new Line2D.Double();
            d.setLine(x1,y1,x2, y2);
            lines.add(d);
        }
        return lines;
    }
    public double minDistanceMountainRange(Structs.Polygon p, List<Structs.Vertex> vModList, List<Line2D> lines){
        int centroidIdx = p.getCentroidIdx();
        Structs.Vertex centroid = vModList.get(centroidIdx);
        double distance = Double.POSITIVE_INFINITY;
        for(Line2D d: lines){
            distance = Math.min(distance, d.ptLineDist(centroid.getX(),centroid.getY()));
        }
        return distance;
    }
}
